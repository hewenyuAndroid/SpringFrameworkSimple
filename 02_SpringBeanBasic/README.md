[toc]

# Spring Bean 实例化

## Spring Bean 从 <bean> 到 bean 实例的过程

Spring 容器在进行初始化时，会将 xml 配置的 `<bean>` 的信息封装成一个 `BeanDefinition` 对象，所有的 `BeanDefinition` 对象会被存储到一个 `BeanFactory` 下的 `beanDefinitionMap` 的映射表中，Spring 框架再对 `beanDefinitionMap` 遍历，使用反射创建 `Bean` 实例对象，创建的 `Bean` 实例对象会被存储到 `singletonObjects` 缓存池中，后续通过 Spring 容器 `getBean()` 获取 bean 实例时，就会从 `singletonObjects` 缓存池中查询并返回 bean。

> step1 定义 spring bean 信息

```xml
<bean id="userDao" class="com.example.dao.UserDao"/>
```

> step2 封装 bean 信息到 `BeanDefinition` 对象中

```java
public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {
    void setScope(@Nullable String var1);
    void setScope(@Nullable String var1);
    ...
}
```

> step3 存储 `BeanDefinition` 对象到 `beanDefinitionMap` 中

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
    ...
    // 存储 <bean> 标签对应的 BeanDefinition 对象
    // key: <bean> 标签对应 bean 的 beanName
    // value: <bean> 标签对应的 BeanDefinition 对象
    private final Map<String, BeanDefinition> beanDefinitionMap;
    ...
}
```

> step4 Spring 框架解析 `beanDefinitionMap` 中的数据，通过反射创建 bean 存储到 `singletonObjects` 单例池中

```java
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
    ...
    // 存储 bean 实例的单例对象
    // key: bean 对应的 beanName
    // value: bean 实例对象
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);
    ...
}
```

![Spring实例化Bean过程](./imgs/SpringFrameworkCreateBean.png)

## Spring 的后处理器

Spring 的后处理器允许我们介入到 `Bean` 的实例化过程中，达到 动态添加 `BeanDefinition`、动态修改 `BeanDefinition`、以及动态修改 `Bean` 的作用。Spring 的后处理器主要有以下两种:

1. `BeanFactoryPostProcessor`: Bean 工厂后处理器，在 `beanDefinitionMap` 且在 `Bean` 实例化之前执行;
2. `BeanPostProcessor`: Bean 后处理器，一般在 Bean 实例化后，在填充到 `singletonObjects` 之前执行;

Spring 后处理器执行时机如图所示

![Spring后处理器](./imgs/SpringFrameworkCreateBeanPostProcessor.png)

### `BeanFactoryPostProcessor` Bean 工厂后处理器

`BeanFactoryPostProcessor` 是一个接口规范，实现了该接口的类只要注册到 Spring 容器中，Spring 就会在对应的时机回调该接口的方法。

```java
// BeanFactoryPostProcessor 接口定义
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory var1) throws BeansException;
}
```

> step1 创建自定义的 Bean 工厂后处理器

```java
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor: postProcessBeanFactory()");
    }
}
```

> step2 注册 Bean 工厂后处理器

```xml
<!-- 注册 Bean工厂后处理器 -->
<bean class="com.example.processor.MyBeanFactoryPostProcessor"/>
```

> step3 启动 spring 容器

```java
new ClassPathXmlApplicationContext("applicationContext.xml");

// 输出
/*
MyBeanFactoryPostProcessor: postProcessBeanFactory()
*/
```

可以看到，spring 容器启动过程中，执行了自定义 bean工厂后处理器的回调函数。

#### 修改 BeanDefinition 对象

> step1 在 `MyBeanFactoryPostProcessor` 中，将 `userDao` 的 Bean 对象修改为 `UserDaoV2`

```java
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor: postProcessBeanFactory()");
        // 获取 id 为 userDao 的 BeanDefinition 对象
        BeanDefinition userDaoBeanDefinition = configurableListableBeanFactory.getBeanDefinition("userDao");
        // 修改 userDao 的 class 为 V2 版本
        userDaoBeanDefinition.setBeanClassName("com.example.dao.UserDaoV2");
    }
}
```

> step2 spring 容器中获取名为 `userDao` 的实例

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
UserDao userDao = applicationContext.getBean(UserDao.class);
System.out.println("ApplicationContextCaseTest: userDao = " + userDao);

// 输出:
/*
MyBeanFactoryPostProcessor: postProcessBeanFactory()
// 可以看到，userDao 返回的实例对象变成了 UserDaoV2 类型
ApplicationContextCaseTest: userDao = com.example.dao.UserDaoV2@ae13544
*/
```

#### 动态注册 `BeanDefinition` 对象

> step1 创建目标 bean 类

注意: 无需将 BookDao 类注册到 `applicationContext.xml` 文件中

```java
package com.example.dao;

public class BookDao { }
```

> step2 新增自定义 `BeanFactoryPostProcessor` Bean工厂后处理器类

自定义 Bean 工厂后处理器类实现 `BeanFactoryRegistryPostProcessor` 接口，在 `postProcessBeanDefinitionRegistry` 回调函数中动态注册 `BookDao` 的 `BeanDefinition` 类。

```java
public class DynamicRegisterBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("DynamicRegisterBeanFactoryPostProcessor: postProcessBeanDefinitionRegistry()");
        // 动态注册 bookDao Bean
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName("com.example.dao.BookDao");
        beanDefinitionRegistry.registerBeanDefinition("bookDao", beanDefinition);
        System.out.println("DynamicRegisterBeanFactoryPostProcessor: postProcessBeanDefinitionRegistry() register bookDao done.");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("DynamicRegisterBeanFactoryPostProcessor: postProcessBeanFactory()");
    }
}
```

> step3 向 spring 中注册自定义后处理器

```xml
<!-- 注册 Bean工厂后处理器 -->
<bean class="com.example.processor.DynamicRegisterBeanFactoryPostProcessor"/>
```

> step4 通过 spring 容器获取 `BookDao` bean 实例

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
BookDao bookDao = (BookDao) applicationContext.getBean("bookDao");
System.out.println("ApplicationContextCaseTest: bookDao = " + bookDao);

// 输出:
/*
// spring 容器初始化
DynamicRegisterBeanFactoryPostProcessor: postProcessBeanDefinitionRegistry()
DynamicRegisterBeanFactoryPostProcessor: postProcessBeanDefinitionRegistry() register bookDao done.
DynamicRegisterBeanFactoryPostProcessor: postProcessBeanFactory()
// 通过 spring 容器获取 bookDao 实例
ApplicationContextCaseTest: bookDao = com.example.dao.BookDao@587e5365
*/
```

可以看到 `BookDao` 类没有在 `applicationContext.xml` 文件中注册，通过 bean 工厂后处理器动态注册后，spring 容器可以成功的获取到 `bookDao` 实例。


### `BeanPostProcessor` Bean 后处理器

Spring 的 Bean 被实例化后，到最终缓存到 `singletonObjects` 单例池之前，中间会经过 bean 的初始化过程 ( 例如: 属性的填充，`init-method` 方法执行 )，spring 提供了一个对外进行扩展的点 `BeanPostProcessor` 称之为 Bean 后处理器。

自定义 spring bean 后处理器需要实现 `BeanPostProcessor` 接口，然后将实现类在 spring 中进行注册，spring 框架会自动识别并在对应的时机回调接口函数。

```java
public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 在 bean 的属性注入完毕，init-method 函数执行之前回调
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 在 init-method 函数执行完毕，bean 添加到 singletonObjects 单例池之前执行
        return bean;
    }
}
```


> step1 创建自定义 bean 类

```java
public class UserDao {
    public UserDao() {
        System.out.println("UserDao: constructor");
    }

    public void initializer() {
        System.out.println("UserDao: initializer()");
    }

    public void setName(String name) {
        System.out.println("UserDao: setName() name=" + name);
    }
}
```

> step2 注册自定义 bean

```xml
<!-- 增加 init-method 函数配置 -->
<bean id="userDao" class="com.example.dao.UserDao" init-method="initializer">
    <property name="name" value="aaa"/>
</bean>
```

> step3 创建 bean 后处理器类

```java
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor: postProcessBeforeInitialization() bean=" + bean);
        // 在 bean 实例化，且属性注入完毕后， init-method 函数执行之前 回调
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor: postProcessAfterInitialization() bean=" + bean);
        // 在 init-method 函数执行完毕之后，bean 实例添加到 singletonObjects 单例池之前 回调
        return bean;
    }
}
```

> step4 注册 bean 后处理器类

```xml
<!-- 注册 Bean后处理器 -->
<bean class="com.example.processor.MyBeanPostProcessor"/>
```

> step5 启动 spring 容器

```java
new ClassPathXmlApplicationContext("applicationContext.xml");

// 输出
/*
UserDao: constructor
UserDao: setName() name=aaa
// 1. 在 userDao 对象创建 && 属性注入完毕后执行 bean 后处理器 postProcessBeforeInitialization() 函数
// 注意: 这里是在每个 bean 实例化并注入属性后执行 postProcessBeforeInitialization()，而不是所有 bean 创建完毕后同意执行回调
MyBeanPostProcessor: postProcessBeforeInitialization() bean=com.example.dao.UserDaoV2@68e965f5
// 2. 在 bean 后处理器 postProcessBeforeInitialization() 执行完毕后执行 init-method 函数
UserDao: initializer()
// 3. 在 init-method 函数执行完毕后回调 postProcessAfterInitialization() 函数 
MyBeanPostProcessor: postProcessAfterInitialization() bean=com.example.dao.UserDaoV2@68e965f5
*/
```

