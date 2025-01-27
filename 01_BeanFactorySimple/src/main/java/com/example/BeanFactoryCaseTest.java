package com.example;

import com.example.dao.UserDao;
import com.example.service.UserServiceImpl;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class BeanFactoryCaseTest {

    public static void main(String[] args) {
        getUserDaoBean();

        getUserService();
    }

    private static void getUserDaoBean() {
        // 1. 创建工厂对象
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        // 2. 创建bean对象解析器 (xml解析)
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        // 3. 绑定解析器
        reader.loadBeanDefinitions("beans.xml");

        // 4. 根据 id 获取bean对象
        UserDao userDao = (UserDao) factory.getBean("userDao");
        System.out.println("BeanFactoryCaseTest: getUserDaoBean(), userDao=" + userDao.hashCode());

        // 默认情况下，beanFactory 返回的 bean 对象是单例对象，这里再次获取到的对象和上面的一致
        UserDao userDao2 = (UserDao) factory.getBean("userDao");
        System.out.println("BeanFactoryCaseTest: getUserDaoBean(), userDao2=" + userDao2.hashCode());
    }

    private static void getUserService() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("beans.xml");

        UserServiceImpl userService = factory.getBean(UserServiceImpl.class);
        System.out.println("BeanFactoryCaseTest: getUserService(), userService=" + userService.hashCode());
        // 此时可以获取 UserService 中注入的 userDao 对象
        System.out.println("BeanFactoryCaseTest: getUserService(), userService.userDao=" + userService.getUserDao().hashCode());

    }

}