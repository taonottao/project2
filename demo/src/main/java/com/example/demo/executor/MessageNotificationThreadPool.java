package com.example.demo.executor;

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class MessageNotificationThreadPool {

    private int corePoolSize = 20;

    private int maxPoolSize = 40;

    private int queueCapacity = 1000;


    @Bean(name = "messageNotificationThreadPoolExecutor")
    public ThreadPoolExecutor messageNotificationThreadPoolExecutor() {

        ThreadPoolExecutor executor = ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maxPoolSize)
                .setWorkQueue(new LinkedBlockingQueue<>(queueCapacity))
                .setHandler(new MessageNotificationThreadPoolRejectHandler())
                .build();
        return executor;
    }

}
