package com.example.demo.common;

import cn.hutool.cache.impl.TimedCache;

public class CacheCode {

    private volatile static TimedCache instance;

    private CacheCode(){};

    public static TimedCache getInstance() {
        if (instance == null) {
            synchronized (CacheCode.class) {
                if (instance == null) {
                    // 设置过期时间 5 min
                    instance = new TimedCache(5 * 60 * 1000);
                    instance.schedulePrune(30*60*1000);
                }
            }
        }
        return instance;
    }

}
