package com.example.demo.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class FileUtil extends cn.hutool.core.io.FileUtil {

    public static boolean checkAllowDownLoad(String fileName) {

        // 禁止目录上跳级别
        if (StrUtil.contains(fileName, "..")) {
            return false;
        }
        if (ArrayUtil.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, getFileType(fileName))) {
            return true;
        }
        return false;
    }

    private static String getFileType(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return "";
        }

        String type = fileName.substring(index + 1).toLowerCase();

        return type;
    }

    public static void writeBytes(String filePath, ServletOutputStream outputStream) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = fis.read(buffer);
                if (read > 0) {
                    outputStream.write(buffer, 0, read);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(fis);
        }

    }

    public static String uploadFile(String filePath, MultipartFile multipartFile) throws IOException {
        String absPath = getAbsoluteFile(filePath, multipartFile).getAbsolutePath();
        multipartFile.transferTo(Paths.get(absPath));
        return absPath;
    }

    private static File getAbsoluteFile(String filePath, MultipartFile multipartFile) {
        String type = getFileType(multipartFile.getOriginalFilename());
        File file = new File(filePath + "/" + multipartFile.getName()+ "." + type);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
            }
        }
        return file;
    }
}
