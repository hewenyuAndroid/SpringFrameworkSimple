package com.example.factory;

import com.example.dao.BookDao;

// 创建一个工厂类
public class BookDaoFactoryBean {

    // 提供一个静态函数获取 Bean，无参构造方式
    public static BookDao getBookDaoWithStatic() {
        System.out.println("BookDaoFactoryBean: getUserDaoWithStatic, 无参构造方式");
        // 这里可以实现自定义逻辑
        return new BookDao();
    }

    // 提供一个静态函数获取 Bean，有参构造方式
    public static BookDao getBookDaoWithStatic(String name) {
        System.out.println("BookDaoFactoryBean: getUserDaoWithStatic(), name=" + name);
        return new BookDao(name);
    }

    // 提供一个非静态函数获取Bean，通过调用无参构造方式创建 Bean
    public BookDao getBookDao() {
        System.out.println("BookDaoFactoryBean: getBookDao(), 无参构造");
        return new BookDao();
    }

    // 提供一个非静态函数,通过调用有参构造方式创建 Bean
    public BookDao getBookDao(String name) {
        System.out.println("BookDaoFactoryBean: getBookDao(), name=" + name);
        return new BookDao(name);
    }

}
