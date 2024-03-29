package com.example.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;


public class DemoTest {
    public static void main(String[] args) {
        DateTime date = DateUtil.date();
        System.out.println(date);
    }
}
