package com.example.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class UserService {

    public UserService() {
        System.out.println("UserService: constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("UserService: init()");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("UserService: destroy()");
    }

}
