package com.example;

import com.example.dao.BookDao;
import com.example.service.IUserService;
import com.example.service.impl.BookService;
import com.example.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextCaseTest {

    public static void main(String[] args) {
        // 通常将 applicationContext 容器对应的配置文件名称设置为 applicationContext.xml
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 通过 spring 容器获取bean对象
        IUserService userService = applicationContext.getBean(UserServiceImpl.class);
        System.out.println("ApplicationContextCaseTest: userService=" + userService);

        System.out.println("-------------------------------");

        BookDao bookDao1 = (BookDao) applicationContext.getBean("bookDao");
        BookDao bookDao2 = (BookDao) applicationContext.getBean("bookDaoWithName");
        System.out.println("ApplicationContextCaseTest: bookDao1=" + bookDao1 + ", bookDao2=" + bookDao2);

        System.out.println("-------------------------------");

        BookDao bookDao3 = (BookDao) applicationContext.getBean("bookDaoWithStatic");
        BookDao bookDao4 = (BookDao) applicationContext.getBean("bookDaoWithStaticName");
        System.out.println("ApplicationContextCaseTest: bookDao3=" + bookDao3 + ", bookDao4=" + bookDao4);

        System.out.println("-------------------------------");

        BookDao bookDao5 = (BookDao) applicationContext.getBean("bookDaoFactory");
        BookDao bookDao6 = (BookDao) applicationContext.getBean("bookDaoFactoryWithName");
        System.out.println("ApplicationContextCaseTest: bookDao5=" + bookDao5 + ", bookDao6=" + bookDao6);

        System.out.println("-------------------------------");

        BookDao bookDao7 = (BookDao) applicationContext.getBean("bookDaoFactoryBeanImpl");
        System.out.println("ApplicationContextCaseTest: bookDao7=" + bookDao7);

        BookService bookService = (BookService) applicationContext.getBean("bookService");
        System.out.println("ApplicationContextCaseTest: bookService=" + bookService);


    }

}
