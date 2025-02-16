package com.example.service.impl;

import com.example.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void login(String username, String password) {
        System.out.println("UserServiceImpl: login(), username=" + username + ", password=" + password);
    }

    @Override
    public void checkAccount(String account) {
        System.out.println("UserServiceImpl: checkAccount(), account=" + account);
        account.toString();
    }

    @Override
    public void show() {
        System.out.println("UserServiceImpl: show()");
    }

}
