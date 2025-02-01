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

## 非自定义 Bean 注解开发

非自定义 Bean 不能像自定义 Bean 一样使用 `@Component` 注解进行管理，非自定义 Bean 需要通过工厂的方式进行实例化，使用 `@Bean` 标注方法即可， `@Bean` 的属性为 `beanName`，如果不指定value值，则 `beanName` 默认取当前工厂方法的方法名称;

```java
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
```

启动 spring 容器获取 bean

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
UserService thirdUserService = (UserService) context.getBean("thirdUserService");
System.out.println("ApplicationContextCaseTest: thirdUserService = " + thirdUserService);
```

如果使用 `@Bean` 工厂方法需要注入参数的话，有如下几种方式

1. 使用 @Autowired 根据类型自动匹配，@Autowired 可以省略
2. 使用 @Qualifier 根据名称匹配
3. 使用 @Value 匹配普通数据


### 其它注解扩展 `@Primary` `@Profile`

#### `@Primary` 注解

`@Primary` 注解用于标注相同类型 `Bean` 的优先使用权，`@Primary` 是 Spring 3.0 引入的，与 `@Component` 和 `@Bean` 一起使用，标注该 `Bean` 的优先级更高，在通过类型获取 Bean 或通过 `@Autowired` 根据类型自动注入时，会选择优先级更高的 Bean;

```java
@Component
public class UserDaoImpl implements IUserDao {}

// 使用 @Primary 注解标注的 bean 通过类型匹配时，优先返回
@Component
@Primary
public class UserDaoImpl2 implements IUserDao {}

public class UserService {
    // 使用 @Autowired 注入时，由于 UserDaoImpl2 使用了 @Primary 注解，
    // 因此当前的 userDao 实例为 UserDaoImpl2 类型
    @Autowired
    private IUserDao userDao;
}

// 使用类型获取 Bean 实例时，由于 UserDaoImpl2 使用了 @Primary 注解
// 因此当前获取到的是 UserDaoImpl2 实例
applicationContext.getBean(IUserDao.class);
```

配合 `@Bean` 注解使用

```java
@Component
public class ThirdBean {
    // 通过类型获取或者 @Autowired 注入时，优先返回 userDao1 实例
    @Bean
    @Primary
    public IUserDao userDao1() {
        return new UserDao();
    }
    
    public IUserDao userDao2() {
        return new UserDao();
    }
}
```

#### `@Profile` 注解

`@Profile` 注解的作用同 xml 配置的 profile 属性，用于环境切换使用

```xml
<beans profile="test" />
```

`@Profile` 注解标注在类或方法上，标注当前产生的 Bean 从属哪个环境，只有激活了当前环境，被标注的 Bean 才能被注册到 Spring 容器中，未标注环境的 Bean 能够在任何环境中都被加载到 Spring 容器中。

```java
// 生产环境启动时加载
@Component
@Profile("prod")
public class UserDaoImpl implements IUserDao {}

// 测试环境启动时加载
@Component
@Profile("test")
public class UserDaoImpl2 implements IUserDao {}
```

> 配置 spring 容器的环境

- 使用命令行动态参数，虚拟机参数位置加载 `-Dspring.profiles.active=test` ;
- 使用代码的方式设置环境变量 `System.setProperty("spring.profiles.active=test")` ;


## 非 Bean 标签使用

`@Component` 注解替代了 `<bean>` 标签，但是像 `<import>`、`<context:componentScan>` 等 非 `<bean>` 标签目前还是在 `applicationContext.xml` 配置文件中，例如:

```xml
<!-- 组件扫描 -->
<context:component-scan base-package="com.example"/>
<!-- 加载 jdbc.properties文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
<!-- 引入其它 xml 文件 -->
<import resource="classpath:beans.xml"/>
```

### `@Configuration` 注解

`@Configuration` 注解标识的类为配置类，替代原有 xml 的配置文件，该注解第一个作用是标识该类是一个配置类，第二个作用是标识该类具有 `@Component` 作用

```java
@Configuration
public class ApplicationContextConfig {}
```

> step1: 创建注解配置类

```java
@Configuration
@ComponentScan("com.example")
@PropertySource("classpath:jdbc.properties")
public class SpringConfig { }
```

> step2: 使用注解相关的类启动 spring 容器

```java
//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
// 使用注解配置 Spring 时，需要使用注解相关的配置类启动容器, 而不是原先的 ClassPathXmlApplicationContext
ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
UserDao userDao = (UserDao) context.getBean("userDao");
System.out.println("AnnotationConfigCaseTest: userDao = " + userDao);
```


### `@ComponentScan` 注解

`@ComponentScan` 组件扫描配置注解，替换原有 xml 文件中的 `<context:component-scan base-package="" />`

`base-package` 的配置方式:

- 指定一个或多个包名: 扫描指定包及其子包下使用注解的类;
- 不配置包名: 扫描当前 `@ComonentScan` 注解配置类所在包及其子包下的类;

```java
// 扫描 ApplicationContextConfig 类所在包及其子包下的注解配置类
@Configuration
@ComponentScan
public class ApplicationContextConfig {}

// 扫描 com.example 包及其子包下的注解配置类
@Configuration
@ComponentScan("com.example")
public class ApplicationContextConfig {}

// 扫描 com.example.dao 和 com.example.service 包及其子包下的注解配置类
@Configuration
@ComponentScan({"com.example.dao", "com.example.service"})
public class ApplicationContextConfig {}
```

### `@PropertySource` 注解

`@PropertySource` 注解用于加载外部 `properties` 资源配置，替代原有的 `<context:property-placeholder location="" />` 配置

```java
// 配置 properties 文件加载
@Configuration
@ComponentScan
//@PropertySource("classpath:jdbc.properties")
@PropertySource({"classpath:jdbc.properties", "classpath:source.properties"})
public class ApplicationContextConfig {}
```

### `@Import` 注解

`@Imoprt` 注解用于加载其它配置类，替代原有 xml 的 `<import resource="classpath:beans.xml" />` 配置

```java
// 未将 OtherBean 交给 Spring 管理，此时spring框架不能扫描到 userBean 函数
//@Component
public class OtherBean {
    @Bean
    public UserBean userBean(){
        return new UserBean();
    }
}
```

```java
// 将 OtherBean 类通过 @Import 注解导入到配置中，容器启动后，可以正常获取到 UserBean 实例;
@Configuration
@ComponentScan
@PropertySource("classpath:jdbc.properties")
@Import(OtherBean.class)
public class ApplicationContextConfig { }
```


