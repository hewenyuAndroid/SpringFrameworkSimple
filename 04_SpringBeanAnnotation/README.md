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