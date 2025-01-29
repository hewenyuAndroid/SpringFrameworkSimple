package com.example.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class DynamicRegisterBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("DynamicRegisterBeanFactoryPostProcessor: postProcessBeanDefinitionRegistry()");
        // 动态注册 bookDao Bean
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName("com.example.dao.BookDao");
        beanDefinitionRegistry.registerBeanDefinition("bookDao", beanDefinition);
        System.out.println("DynamicRegisterBeanFactoryPostProcessor: postProcessBeanDefinitionRegistry() register bookDao done.");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("DynamicRegisterBeanFactoryPostProcessor: postProcessBeanFactory()");
    }
}
