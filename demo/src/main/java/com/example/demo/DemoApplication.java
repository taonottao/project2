package com.example.demo;

import cn.hutool.extra.ftp.SimpleFtpServer;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }

}
