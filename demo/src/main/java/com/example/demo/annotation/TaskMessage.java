package com.example.demo.annotation;


import com.example.demo.common.constant.TaskMessageNotificationType;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskMessage {
    TaskMessageNotificationType notificationType();
}
