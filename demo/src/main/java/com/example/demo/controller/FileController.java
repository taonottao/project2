package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.example.demo.utils.FileUtil;
import org.apache.ftpserver.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    public static void main(String[] args) {
        String str = "特此申请开具100分文书";
        String ret = StrUtil.subBetween(str, "申请", "文书");
        System.out.println(ret);
    }

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtil.checkAllowDownLoad(fileName)) {
                throw new Exception(StrUtil.format("文件名称<{}>非法，不允许下载！！！", fileName));
            }
            String filePath = "d:/from/" + fileName;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtil.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Map uploadFile(MultipartFile multipartFile) {
        Map result = new HashMap();
        String filePath = "D:/from/uploadPath";
        String fileName;
        try {
            fileName = FileUtil.uploadFile(filePath, multipartFile);
        } catch (IOException e) {
            result.put("code", 400);
            result.put("success", "failure");
            result.put("msg", "文件上传失败");
            return result;
        }
        result.put("code", 200);
        result.put("success", "ok");
        result.put("msg", "文件上传成功");
        result.put("fileName", fileName);
        return result;
    }

    /**
     * 模版导入并转换成html格式返回
     */
    @PostMapping("/importReqFormTemplate")
    public String importReqFormTemplate(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));

        try {
            String tempFileNamePrefix = IdUtil.fastSimpleUUID();
            File tempFile = File.createTempFile(tempFileNamePrefix, fileType);
            file.transferTo(tempFile);
            Setting setting = new Setting();
            String datePath = DateUtil.format(LocalDateTime.now(), "yyyy/MM/dd");
            String targetFilePath = StrUtil.format("{}/{}", "D:/to", datePath);

            // 构建命令行参数
            String[] command = {
                    targetFilePath,
                    "--headless",
                    "--convert-to", "html",
                    "--outdir", targetFilePath,  // 目标文件输出路径
                    FileUtil.getAbsolutePath(tempFile) // 源文件
            };

            // 执行命令
            Process process = Runtime.getRuntime().exec(command);
            int code = process.waitFor();
            if (code != 0) {
                System.out.println("文件提取失败！");
            }
            String content = cn.hutool.core.io.FileUtil.readString(StrUtil.format("{}/{}.{}",
                    targetFilePath, FileUtil.getPrefix(tempFile), "html"), CharsetUtil.GBK);
            if (FileUtil.exist(tempFile)) {
                FileUtil.del(targetFilePath);
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "failure";
        }
    }



}
