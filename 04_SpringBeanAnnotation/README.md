[toc]

# 基本注解开发

Spring 除了使用 xml 配置文件进行配置之外，还可以使用注解方式进行配置，注解方式慢慢成为 xml 配置的替代方案，spring 提供的注解有三个版本:

1. 2.0 时代，Spring 开始出现注解;
2. 2.5 时代，Spring 的 Bean 配置可以通过注解完成;
3. 3.0 时代，Spring 的其它配置也可以使用注解完成，spring 进入全注解时代;

## Spring Bean基本注解开发

基本 Bean 注解，主要是使用注解的方式替代原 xml 的 `<bean>` 标签及其子标签属性的配置

```xml
<bean 
        id="" name="" class="" scope="" lazy-init="" 
        init-method="" destroy-method="" abstract="" 
        autowrie="" factory-bean="" factory-method="" />
```

使用 Spring 注解替代上述标签

| xml配置                        | 注解               | 描述                                                                              |
|------------------------------|------------------|---------------------------------------------------------------------------------|
| `<bean id="" name="" />`     | `@Component`     | 被该注解标识的类，会在指定扫描范围内被 Spring 加载并实例化                                               |
| `<bean scope="" />`          | `@Scope`         | 在类上或使用了 @Bean 标注的方法上，标注 Bean 的作用范围，取值为 `singleton` 或 `prototype`                |
| `<bean lazy-init="" />`      | `@Lazy`          | 在类上或使用了 @Bean 标注的方法上，标注 Bean 是否延迟加载，取值为 true 或 false                            |
| `<bean init-method="" />`    | `@PostConstruct` | 在方法上使用，标注 Bean 的实例化后执行的方法<br/>注意: 当前注解为 javax 的注解，需要导入 `javax.annotation-api` 包 |
| `<bean destroy-method="" />` | `@PreDestroy`    | 在方法上使用，标注 Bean 销毁前执行的方法<br/>注意: 当前注解为 javax 的注解，需要导入 `javax.annotation-api` 包   |


> 使用注解完成 spring bean 的配置

```java
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
// 注意 PostConstruct 和 PreDestroy 注解是 javax 包下的注解
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
}
```


注意: 使用注解对需要被 Spring 实例化的 Bean 进行标注时，需要告诉 Spring 去哪里找这些 Bean, 要配置 扫描路径

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--
        配置 spring 框架要扫描的包
        当前配置下，spring 框架会扫描 com.example 包及其子包下的 spring 注解
    -->
    <context:component-scan base-package="com.example"/>

</beans>
```

由于 JavaEE 开发是分层的，为了每层 Bean 标识的注解语义化更加明确，`@Component` 注解衍生出了如下三个注解

| `@Componennt` 注解的衍生注解 | 描述              |
|-----------------------|-----------------|
| `@Repository`         | 在 Dao 层类上使用     |
| `@Service`            | 在 Service 层类上使用 |
| `@Controller`         | 在 web 层类上使用     |

```java
@Repository("userDao")
public class UserDao { }

@Service("userService")
public class UserService { }

@Controller("userController")
public class UserController { }
```

## Spring Bean 依赖注入开发

Spring Bean 依赖注入的注解，主要是使用注解的方式替代 xml 的 `<property>` 标签完成属性的注入操作:

```xml
<bean id="" class="">
    <property name="" value="" />
    <property name="" ref="" />
</bean>
```

Spring 主要提供如下注解，用于在 Bean 内部进行注入

| 属性注入注解       | 描述                                 |
|--------------|------------------------------------|
| `@Value`     | 使用在字段或方法上，用于注入普通数据                 |
| `@Autowired` | 使用在字段或方法上，用于根据类型 (`byType`) 注入引用数据 |
| `@Qualifier` | 使用在字段或方法上，结合 `@Autowired` 根据名称注入   |
| `@Resource`  | 使用在字段或方法上，根据类型或名称注入                |

### 使用 `@Value` 注解

#### 使用 `@Value` 注解注入基础数据类型

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    /**
     * 使用 @Value 注解注入基础数据类型数据
     */
    @Value("zhangsan")
    private String name;

    private Integer age;

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
```

启动 spring 容器后获取 userDao bean 实例

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
UserDao userDao = (UserDao) context.getBean("userDao");
System.out.println("ApplicationContextCaseTest: userDao = " + userDao);

// 输出
/*
ApplicationContextCaseTest: userDao = UserDao{name='zhangsan', age=20}       
*/
```

#### 使用 `@Value` 注入 `.properties` 属性文件中的属性

> step1: 创建 `jdbc.properties` 属性文件

```properties
jdbc.username=root
jdbc.password=root
```
> step2: 在 `applicationContext.xml` 文件中，注册属性文件

```xml
<!-- 注入 jdbc.properties 配置 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
```

> step3: 创建 bean 类，注入属性

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JdbcProp {
    /**
     * 使用 "${}" 方式获 properties 文件中的值
     */
    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Override
    public String toString() {
        return "JdbcProp{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

> step4: 启动 spring 容器，获取 bean

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
JdbcProp jdbcProp = context.getBean(JdbcProp.class);
System.out.println("ApplicationContextCaseTest: jdbcProp = " + jdbcProp);

// 输出
/*
ApplicationContextCaseTest: jdbcProp = JdbcProp{username='root', password='root'}
*/
```

### 使用 `@Autowired` 注解，根据类型注入

> step1: 创建 `UserService` 类，持有 `UserDao` 实例

```java
import com.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 使用在属性上直接注入
    @Autowired
    private UserDao userDao;
    
    // 使用在方法上
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                '}';
    }
}
```

> step2: 启动 spring 容器，获取实例

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
UserService userService = context.getBean(UserService.class);
System.out.println("ApplicationContextCaseTest: userService = " + userService);

// 输出
/*
ApplicationContextCaseTest: userService = UserService{userDao=UserDao{name='zhangsan', age=20}}
*/
```

当容器中同一类型的 Bean 实例有多个时，会尝试自动根据名字进行匹配，如果匹配也不成功则会报错

```java
public interface IUserDao {}

@Component
public class UserDaoImpl implements IUserDao {}

@Component
public class UserDaoImpl2 implements IUserDao {}

@Component
public class UserService {
    // 此时存在 多个 IUserDao 的实例，尝试使用字段名称 userDao 匹配
    // 如果 spring 容器中，userDao 的实例也匹配不上，则会报错
    @Autowired
    private IUserDao userDao;
    
    // 配合 @Qualifier 注解, 指定当前注入的 bean 名称
    @Autowired
    @Qualifier("userDao2")
    private IUserDao userDao2;
}
```

> 扩展

1. 当 `@Autowired` 注解使用在方法上时，可以根据参数类型匹配 bean 实例;
2. 当 `@Autowired` 注解使用在方法上时，方法参数是一个集合，则 spring 会返回当前容器中所有的该类型的实例;

```java
@Service
public class UserService {
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
}
```

启动容器，得到如下输出:

```text
UserService: yyy(), userDaoList=[UserDao{name='zhangsan', age=20}]
UserService: xxx(), userDao=UserDao{name='zhangsan', age=20}
```

### 使用 `@Resource` 注解

`@Resource` 注解是 `javax.annotation` 包下的注解，它既可以根据类型注入，也可以根据名称注入，无参就是根据类型注入，有参数就是根据名称注入

> step1: 使用 @Resource 注解注入属性

```java
package com.example.service;

import com.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

//    @Resource
//    private UserDao userDao2;
    
    @Resource(name="userDao")
    private UserDao userDao2;

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                "userDao2=" + userDao2 +
                '}';
    }
}
```

> step2: 启动 spring 容器，获取bean

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
UserService userService = context.getBean(UserService.class);
System.out.println("ApplicationContextCaseTest: userService = " + userService);

// 输出
/*
ApplicationContextCaseTest: userService = UserService{userDao=UserDao{name='zhangsan', age=20}userDao2=UserDao{name='zhangsan', age=20}}
*/
```



