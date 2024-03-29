package com.example.demo.aspect;

import com.example.demo.annotation.TaskMessage;
import com.example.demo.common.constant.TaskMessageNotificationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@Aspect
public class MessageNotifyAspect {

    private static final String TASK_COMPLETE_APPROVE_CODE = "approve";

    private static final String TASK_COMPLETE_REJECT_CODE = "reject";
    
    @Autowired
    private ThreadPoolExecutor messageNotificationThreadPoolExecutor;

    @Pointcut("@annotation(com.example.demo.annotation.TaskMessage)")
    public void taskMessagePointcut() {
    }

    @AfterReturning(pointcut = "@annotation(taskMessage)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, TaskMessage taskMessage, Object jsonResult){
        TaskMessageNotificationType type = taskMessage.notificationType();
        // todo 流程开始，通过，拒绝，根据 type 匹配，发送不同短信通知，通过 线程池异步发送短信
        if (type.equals(TaskMessageNotificationType.approve)) {
            messageNotificationThreadPoolExecutor.execute(() -> {
                System.out.println("执行发送短信");
            });
        }
    }

}
