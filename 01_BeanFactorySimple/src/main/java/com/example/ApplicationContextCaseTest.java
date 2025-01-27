package com.example;

import com.example.service.IUserService;
import com.example.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextCaseTest {

    public static void main(String[] args) {
        // 通常将 applicationContext 容器对应的配置文件名称设置为 applicationContext.xml
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 通过 spring 容器获取bean对象
        IUserService userService = applicationContext.getBean(UserServiceImpl.class);
        System.out.println("ApplicationContextCaseTest: userService=" + userService);
    }

}
