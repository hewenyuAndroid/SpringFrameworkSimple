package com.example.dao;

public class BookDao {

    public BookDao() {
        System.out.println("BookDao: 无参构造...");
    }

    public BookDao(String name) {
        System.out.println("BookDao: 有参构造，name=" + name);
    }

}
