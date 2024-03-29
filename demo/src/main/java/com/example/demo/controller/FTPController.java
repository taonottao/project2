package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ftp")
public class FTPController {

    @PostMapping
    public String fileDl() {

        Ftp ftp = new Ftp("127.0.0.1");
        ftp.cd("/D:/from");
        //上传本地文件
//        ftp.upload("D:/from", FileUtil.file("D:/from/hello111.txt"));
////下载远程文件
        ftp.download("/D:/from", "hello111.txt", FileUtil.file("D:/to/hello222.txt"));
        return null;
    }

}
