package com.example;

import com.example.dao.BookDao;
import com.example.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextCaseTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        System.out.println("ApplicationContextCaseTest: userDao = " + userDao);

        BookDao bookDao = (BookDao) applicationContext.getBean("bookDao");
        System.out.println("ApplicationContextCaseTest: bookDao = " + bookDao);
    }
}