package com.example.demo.controller;

import com.example.demo.annotation.TaskMessage;
import com.example.demo.common.constant.TaskMessageNotificationType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    
    @GetMapping
    @TaskMessage(notificationType = TaskMessageNotificationType.approve)
    public String testAop() {
        return "succ";
    }
    
}
