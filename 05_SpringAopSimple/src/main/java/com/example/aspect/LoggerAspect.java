package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import java.util.Arrays;

public class LoggerAspect {

    public void beforeAdvice(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        // 获取方法名称
        String methodName = signature.getName();
        // 获取参数
        Object[] args = joinPoint.getArgs();
        System.out.println("LoggerAspect: beforeAdvice(), methodName=" + methodName + ", args=" + Arrays.toString(args));
    }

    public void afterReturningAdvice() {
        System.out.println("LoggerAspect: afterReturningAdvice()");
    }

    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable throwable) {
        System.out.println("LoggerAspect: afterThrowingAdvice(), throwable=" + throwable.getMessage());
    }

    public void afterAdvice() {
        System.out.println("LoggerAspect: afterAdvice()");
    }

    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            System.out.println("LoggerAspect: aroundAdvice() beforeAdvice");
            result = joinPoint.proceed();
            System.out.println("LoggerAspect: aroundAdvice() afterReturningAdvice.");
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("LoggerAspect: aroundAdvice() afterThrowingAdvice.");
        } finally {
            System.out.println("LoggerAspect: aroundAdvice() afterAdvice()");
        }
        return result;
    }

}
