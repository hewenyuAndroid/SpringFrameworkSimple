package com.example;

import com.example.config.SpringConfig;
import com.example.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigCaseTest {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 使用注解配置 Spring 时，需要使用注解相关的配置类启动容器, 而不是原先的 ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserDao userDao = (UserDao) context.getBean("userDao");
        System.out.println("AnnotationConfigCaseTest: userDao = " + userDao);
    }

}
