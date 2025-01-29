package com.example.dao;

public class UserDao {

    public UserDao() {
        System.out.println("UserDao: " + this.hashCode());
    }

    public void postInit() {
        System.out.println("UserDao: postInit(), " + this.hashCode());
    }

    public void preDestroy() {
        System.out.println("UserDao: preDestroy(), " + this.hashCode());
    }

}
