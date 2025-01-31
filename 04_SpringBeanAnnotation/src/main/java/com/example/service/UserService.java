package com.example.service;

import com.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    // 使用在属性上直接注入
    @Autowired
    private UserDao userDao;

    @Resource
    private UserDao userDao2;

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

    // 使用在方法上
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    // @Autowired 注解使用在方法上时，可以根据参数类型匹配
    @Autowired
    public void xxx(UserDao userDao) {
        System.out.println("UserService: xxx(), userDao=" + userDao);
    }

    // @Autowired 注解使用在方法上时，如果参数类型是集合，则会把所有该类型的bean返回
    @Autowired
    public void yyy(List<UserDao> userDaoList) {
        System.out.println("UserService: yyy(), userDaoList=" + userDaoList);
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                "userDao2=" + userDao2 +
                '}';
    }
}
