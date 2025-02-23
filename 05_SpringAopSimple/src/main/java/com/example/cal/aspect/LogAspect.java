package com.example.cal.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Component 首先这个类要受 spring 管理
 * @Aspect 注解告诉 spring 这个组件是个切面，注意：需要在 applicationContext.xml 配置文件中开启aop支持
 */
@Aspect
@Component
public class LogAspect {

    /*
        需要告诉 spring 以下通知何时何地运行？

        何时?
            @Before: 目标方法执行之前运行
            @AfterReturning: 目标方法正常执行结束返回结果时运行 (可以理解为try代码块的最后一行)
            @AfterThrowing: 目标方法抛出异常时运行 (可以理解为 catch 代码块里面执行)
            @After: 目标方法最终返回时运行 (可以理解为 finally 代码块)

        何地?
            切入点表达式: execution( 方法的全签名 )
                - 全签名完整写法:
                    public int com.example.cal.Calculator.add(int, int) throws Exception
                - 全签名省略写法: [] 中的内容可以省略
                    [public] int [com.example.cal.Calculator].add(int, int) [throws Exception]
                    int add(int, int)
                - 通配符
                    - *: 表示任意字符
                    - (..) 动态参数列表

        何时何地?
            @Before("execution(int add(int, int))")

     */

    /**
     * 抽取切入点表达式
     */
    @Pointcut("execution(int *(int, int))")
    public static void pointCut() {
        // do nothing
    }

    @Before("pointCut()")   // 使用切入点表达式
    public void excBefore(JoinPoint joinPoint) {
        // 获取方法签名
        Signature signature = joinPoint.getSignature();
        // 获取目标方法名称
        String methodName = signature.getName();
        // 获取目标方法参数
        Object[] args = joinPoint.getArgs();
        System.out.println("LogAspect: excBefore(), methodName=" + methodName + ", args=" + Arrays.toString(args));
    }

    /**
     * 返回通知
     *
     * @param joinPoint 连接点对象
     * @param result    配置返回值的参数名称
     */
    @AfterReturning(value = "execution(int *(int, int))", returning = "result")
    public void excReturn(JoinPoint joinPoint, Object result) {
        // 获取目标方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = signature.getName();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        System.out.println("LogAspect: excReturn(), methodName=" + methodName + ", args=" + Arrays.toString(args) + ", result=" + result);
    }

    /**
     * 异常通知
     *
     * @param joinPoint 连接点对象
     * @param tx        配置异常通知获取异常信息
     */
    @AfterThrowing(value = "execution(int *(int, int))", throwing = "tx")
    public void excException(JoinPoint joinPoint, Throwable tx) {
        // 获取目标方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = signature.getName();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        System.out.println("LogAspect: excException(), methodName=" + methodName + ", args=" + Arrays.toString(args) + ", exception=" + tx);
    }

    @After("execution(int *(int, int))")
    public void excEnd() {
        System.out.println("LogAspect: excEnd()");
    }

}
