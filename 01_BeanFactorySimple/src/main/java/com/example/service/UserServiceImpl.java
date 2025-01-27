package com.example.service;

import com.example.dao.UserDao;

public class UserServiceImpl implements IUserService {

    public UserServiceImpl() {
        System.out.println("UserServiceImpl: " + this.hashCode());
    }

    private UserDao userDao;

    // 增加 setUserDao 函数，spring 框架通过set函数自动注入 userDao 对象
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        System.out.println("UserServiceImpl: setUserDao(), userDao=" + userDao.hashCode());
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
