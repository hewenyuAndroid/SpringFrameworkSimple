package com.example.test;

import com.example.cal.Calculator;
import com.example.cal.impl.CalculatorImpl;
import com.example.cal.proxy.ProxyFactory;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class CalculatorCaseTest {

    @Test
    public void testDynamicProxy() {
        Calculator calculator = new CalculatorImpl();
        calculator.add(1, 2);
        calculator.subtract(3, 1);

        System.out.println("===========================");

        // 使用动态代理
        // JDK动态代理，强制要求目标对象必须要有接口，代理的也只是接口定义的方法
        Calculator calculatorProxy = (Calculator) Proxy.newProxyInstance(
                // 取目标对象类加载器
                calculator.getClass().getClassLoader(),
                // 获取目标对象所实现的所有接口
                calculator.getClass().getInterfaces(),
                // InvocationHandler 类似于拦截器
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("===>proxy before, method=" + method.getName() + ", args=" + Arrays.toString(args));
                        Object result = method.invoke(calculator, args);
                        System.out.println("===>proxy after, method=" + method.getName() + ", result=" + result);
                        return result;
                    }
                });
        calculatorProxy.add(1, 2);
        calculatorProxy.subtract(3, 1);

    }

    @Test
    public void testProxyLogEnclose() {
        // 创建目标对象
        Calculator calculator = new CalculatorImpl();
        // 创建代理对象
        Calculator calculatorProxy = ProxyFactory.createProxy(calculator);
        // 正常执行加法
        calculatorProxy.add(3, 4);

        try {
            // 异常执行除法
            calculatorProxy.divide(2, 0);
        } catch (Exception e) {

        }
    }

}
