package com.example.other;

import com.example.dao.UserDao;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// 首选要将工厂类交给 Spring 容器管理，才可以执行到下面的工厂方法
@Component
public class ThirdBean {

    // 如果设置 @Bean 的value值，则默认取函数的名称作为 Bean 的 beanName
    //    @Bean("thirdUserService")
    @Bean
    public UserService thirdUserService() {
        UserService userService = new UserService();
        userService.setUserDao(new UserDao());
        return userService;
    }

    /**
     * 工厂方法需要参数注入的话，有如下几种方式注入
     * 1. 使用 @Autowired 根据类型自动匹配，@Autowired 可以省略
     * 2. 使用 @Qualifier 根据名称匹配
     * 3. 使用 @Value 匹配普通数据
     */
    @Bean
    public UserService thirdUserService2(
            @Value("${jdbc.username}") String userName,
//            @Autowired @Qualifier("userDao2") UserDao userDao
//            @Qualifier("userDao2") UserDao userDao
//            UserDao userDao
            @Autowired UserDao userDao
    ) {
        System.out.println("ThirdBean: thirdUserService2(): userName=" + userName);
        System.out.println("ThirdBean: thirdUserService2(): userDao=" + userDao);
        UserService userService = new UserService();
        userService.setUserDao(userDao);
        return userService;
    }

}
