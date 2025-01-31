package com.example;

import com.example.dao.JdbcProp;
import com.example.dao.UserDao;
import com.example.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextCaseTest {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        System.out.println("ApplicationContextCaseTest: userDao = " + userDao);

        JdbcProp jdbcProp = context.getBean(JdbcProp.class);
        System.out.println("ApplicationContextCaseTest: jdbcProp = " + jdbcProp);

        UserService userService = context.getBean("userService", UserService.class);
        System.out.println("ApplicationContextCaseTest: userService = " + userService);

        UserService thirdUserService = (UserService) context.getBean("thirdUserService");
        System.out.println("ApplicationContextCaseTest: thirdUserService = " + thirdUserService);

    }

}