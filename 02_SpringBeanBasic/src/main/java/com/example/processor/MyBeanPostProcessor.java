package com.example.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor: postProcessBeforeInitialization() bean=" + bean);
        // 在 bean 实例化，且属性注入完毕后， init-method 函数执行之前 回调
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor: postProcessAfterInitialization() bean=" + bean);
        // 在 init-method 函数执行完毕之后，bean 实例添加到 singletonObjects 单例池之前 回调
        return bean;
    }
}
