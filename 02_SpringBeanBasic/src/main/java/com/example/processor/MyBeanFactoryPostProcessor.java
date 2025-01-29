package com.example.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor: postProcessBeanFactory()");
        // 获取 id 为 userDao 的 BeanDefinition 对象
        BeanDefinition userDaoBeanDefinition = configurableListableBeanFactory.getBeanDefinition("userDao");
        // 修改 userDao 的 class 为 V2 版本
        userDaoBeanDefinition.setBeanClassName("com.example.dao.UserDaoV2");
    }

}
