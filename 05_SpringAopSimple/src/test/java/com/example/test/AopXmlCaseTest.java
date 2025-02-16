package com.example.test;

import com.example.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopXmlCaseTest {

    @Test
    public void testXmlAopDefault() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 使用被代理对象的接口类型获取代理对象
        UserService userServiceProxy = context.getBean(UserService.class);
        userServiceProxy.login("zhangsan", "123");
    }

    @Test
    public void testXmlAopThrowing() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userServiceProxy = context.getBean(UserService.class);
        userServiceProxy.checkAccount(null);
    }

    @Test
    public void testXmlAopAround() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userServiceProxy = context.getBean(UserService.class);
        userServiceProxy.show();
    }

}
