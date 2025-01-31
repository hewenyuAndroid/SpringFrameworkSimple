package com.example.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 使用 @Component 注解，未指定 value 值时，bean的名称取值规则为
 * 1. 取类名 -> UserDao
 * 2. 将类名首字母小写 -> userDao
 */
//@Component("userDao")
@Component
@Scope("singleton")
@Lazy(false)
public class UserDao {

    /**
     * 使用 @Value 注解注入基础数据类型数据
     */
    @Value("zhangsan")
    private String name;

    private Integer age;

    public UserDao() {
        System.out.println("UserDao: constructor.");
    }

    // 当前注解在 javax.annotation-api 库下
    @PostConstruct
    public void init() {
        System.out.println("UserDao: init().");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("UserDao: destroy().");
    }

    /**
     * 使用 @Value 注解作用在函数上注入基础数据类型
     */
    @Value("20")
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDao{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
