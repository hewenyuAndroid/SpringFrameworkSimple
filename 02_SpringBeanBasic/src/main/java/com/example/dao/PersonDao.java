package com.example.dao;

import org.springframework.beans.factory.InitializingBean;

public class PersonDao implements InitializingBean {

    public PersonDao() {
        System.out.println("PersonDao: constructor");
    }

    public void init() {
        System.out.println("PersonDao: init()");
    }

    public void setName(String name) {
        System.out.println("PersonDao: setName(), name=" + name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("PersonDao: afterPropertiesSet()");

    }
}
