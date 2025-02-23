package com.example.cal.proxy;

import com.example.cal.log.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static <T> T createProxy(T target) {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Object result = null;
                        String methodName = method.getName();
                        try {
                            // 执行前回调
                            LogUtil.excBefore(methodName, args);
                            result = method.invoke(target, args);
                            // 正常执行结束回调
                            LogUtil.excReturn(methodName, result);
                        } catch (Exception e) {
                            // 一场执行结束回调
                            e.printStackTrace();
                            LogUtil.excException(methodName, e);
                        } finally {
                            // 最终执行结束回调
                            LogUtil.excEnd(methodName);
                        }

                        return result;
                    }
                }
        );

        return (T) proxy;
    }

}
