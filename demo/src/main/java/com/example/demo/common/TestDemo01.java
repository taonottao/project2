package com.example.demo.common;

import cn.hutool.cache.impl.TimedCache;

public class TestDemo01 {

    private static volatile TimedCache instance;

    private TestDemo01(){}

    public static TimedCache getInstance() {
        if (instance == null) {
            synchronized (TestDemo01.class) {
                if (instance == null) {
                    // 设置过期时间为60s
                    instance = new TimedCache(1000 * 60);
                    // 设置没30min执行一次定时任务，清理缓存中的值
                    instance.schedulePrune(1000 * 60 * 30);
                }
            }
        }
        return instance;
    }

}
