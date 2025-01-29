package com.example.dao;

public class UserDao {

    public UserDao() {
        System.out.println("UserDao: constructor");
    }

    public void initializer() {
        System.out.println("UserDao: initializer()");
    }

    public void setName(String name) {
        System.out.println("UserDao: setName() name=" + name);
    }

}
