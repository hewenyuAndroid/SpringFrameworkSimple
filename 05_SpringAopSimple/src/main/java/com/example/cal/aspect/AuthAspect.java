package com.example.cal.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 模拟认证模块切面
 * @Order(1) 存在多个切面时，可以使用 @Order 注解指定切面执行的顺序，数字越小优先级越高
 */
@Order(1)
@Component
@Aspect
public class AuthAspect {

    /**
     * 环绕通知
     *
     * @param joinPoint 连接点对象
     * @return result
     */
    @Around(value = "com.example.cal.aspect.LogAspect.pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = signature.getName();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 定义方法返回值
        Object result = null;
        try {
            System.out.println("AuthAspect: aroundAdvice() before, methodName=" + methodName + ", args=" + Arrays.toString(args));
            // 执行目标方法
            result = joinPoint.proceed();
            System.out.println("AuthAspect: aroundAdvice() afterReturning, methodName=" + methodName + ", result=" + result);
        } catch (Throwable e) {
            System.out.println("AuthAspect: aroundAdvice() afterThrowing, methodName=" + methodName + ", exception=" + e);
            // 环绕通知捕获异常后需要继续向外抛异常，防止多切面时，外部的切面感知不到异常信息
            throw e;
        } finally {
            System.out.println("AuthAspect: aroundAdvice() after, methodName=" + methodName);
        }
        return result;
    }

}
