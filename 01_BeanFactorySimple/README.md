
# BeanFactory

## BeanFactory 项目配置流程

> step1 创建 maven 项目导入 spring 坐标，并同步项目

```xml
<dependencies>
    <!--
        导入 spring-context 包
        spring-context 会自动依赖导入 spring-aop, spring-beans, spring-core
    -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.24</version>
    </dependency>
</dependencies>
```

> step2 定义对应的 bean 类

```java
// userDao
public class UserDao { }

// IUserService
public interface IUserService { }

// UserServiceImpl
public class UserServiceImpl implements IUserService { 
    private UserDao userDao;
    
    // beans.xml 中配置的 userDao 属性映射到该 get 函数，通过该函数注入 userDao 对象
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

> step3 创建 `beans.xml` 配置文件，配置 bean 信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 配置 spring bean 对象和依赖关系 -->
    <bean name="userService" class="com.example.service.impl.UserServiceImpl">
        <!--
            注入 userDao 对象，
            name="userDao" 表示名为 userDao 的 spring bean
            ref="userDao" 表示向 userService 对象中注入名为 userDao 的属性，即调用 serUserDao() 函数注入对象
        -->
        <property name="userDao" ref="userDao"/>
    </bean>

    <bean name="userDao" class="com.example.dao.UserDao"/>

</beans>
```

> step4 测试 BeanFactory 获取 bean 对象

```java
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
```

得到输出日志如下
```
UserDao: 1219161283
BeanFactoryCaseTest: getUserDaoBean(), userDao=1219161283
BeanFactoryCaseTest: getUserDaoBean(), userDao2=1219161283

UserServiceImpl: 1781071780
UserDao: 1667148529
UserServiceImpl: setUserDao(), userDao=1667148529
BeanFactoryCaseTest: getUserService(), userService=1781071780
BeanFactoryCaseTest: getUserService(), userService.userDao=1667148529
```

## ApplicationContext 获取 bean 对象

`ApplicationContext` 称之为 Spring 容器，内部封装了 `BeanFactory`， 比 `BeanFactory` 功能更加丰富，使用 `ApplicationContext` 进行开发时，`xml` 配置文件的名称习惯写成 `applicationContext.xml`。

> step1 将配置文件 `beans.xml 拷贝一份为 `applicationContext.xml`，xml 内容无需改动 (非必须)

> step2 使用 `ApplicationContext` 容器对象获取 bean

```java
// 通常将 applicationContext 容器对应的配置文件名称设置为 applicationContext.xml
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

// 通过 spring 容器获取bean对象
IUserService userService = applicationContext.getBean(UserServiceImpl.class);
System.out.println("ApplicationContextCaseTest: userService=" + userService);
```

> `ApplicationContext` 与 `BeanFactory` 的关系

1. `BeanFactory` 是 Spring 的早期接口，称之为 `Spring` 的 `Bean工厂`， `ApplicationContext` 是后期更高级的接口，称之为 `Spring容器`;
2. `ApplicationContext` 在 `BeanFactory` 的基础上对功能进行了扩展，例如: 监听功能、国际化功能等。`BeanFactory` 的 api 更偏向底层，`ApplicationContext` 的 api 大多数是对这些底层 api 的封装;
3. `Bean` 创建的主要逻辑和功能都被封装在 `BeanFactory` 中，`ApplicationContext` 不仅继承了 `BeanFactory`，而且内部还维护着 `BeanFactory` 的引用，所以 `ApplicationContext` 和 `BeanFactory` 既有继承关系，又有融合关系;
4. `Bean` 的初始化时机不同
   - 原始的 `BeanFactory` 是在首次调用 `getBean()` 时才进行 `Bean` 的创建;
   - `ApplicationContext` 是配置文件加载，容器一创建就将 `Bean` 都实例化并初始化完成;

目录结构如下
```text
│  pom.xml
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─example
│  │  │          │  ApplicationContextCaseTest.java
│  │  │          │  BeanFactoryCaseTest.java
│  │  │          │
│  │  │          ├─dao
│  │  │          │      UserDao.java
│  │  │          │
│  │  │          └─service
│  │  │              │  IUserService.java
│  │  │              │
│  │  │              └─impl
│  │  │                      UserServiceImpl.java
│  │  │
│  │  └─resources
│  │          applicationContext.xml
│  │          beans.xml
│  │
│  └─test
│      └─java
└─target
```

## 常用的 `ApplicationContext`

常用的 `ApplicationContext` 对象如下

| 实现类                                  | 描述                                    |
|--------------------------------------|---------------------------------------|
| `ClassPathXmlApplicationContext`     | 加载类路径下的 xml 配置的 `ApplicationContext`  |
| `FileSystemXmlApplicationContext`    | 加载磁盘路径下的 xml 配置的 `ApplicationContext` |
| `AnnotationConfigApplicationContext` | 加载注解配置类的 `ApplicationContext`           |

在 `Spring Web` 环境下，常用的两个 `ApplicationContext` 如下

| 实现类                                     | 描述                                    |
|-----------------------------------------|---------------------------------------|
| `XmlWebApplicationContext`              | web 环境下，加载类路径下的 xml 配置的 `Application` |
| `AnnotationConfigWebApplicationContext` | web 环境下，加载注解配置类的 `ApplicationContext` |
