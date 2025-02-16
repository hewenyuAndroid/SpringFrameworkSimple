

spring整合第三方框架

spring 整合第三方框架有两种方案

1. 不需要自定义命名空间，不需要使用 spring 的配置文件配置第三方框架本身内容，例如: `mybatis`;
2. 需要引入第三方命名空间，需要使用 spring 的配置文件配置第三方框架本身内容，例如: `Dubbo`;




# mybatis 使用

## mybatis 的默认使用方式

> step1: 创建核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <properties resource="jdbc.properties"/>
    
    <settings>
        <!-- 开启驼峰命名映射 -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

    <typeAliases>
        <package name="com.example.pojo"/>
    </typeAliases>

    <environments default="development">

        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--
                    使用 ${key} 的方式读取 jdbc.properties 配置文件中的属性值
                -->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>

    </environments>

    <mappers>
        <package name="com.example.mapper"/>
    </mappers>

</configuration>
```

> step2: 创建数据对象

```java
public class Emp {
    ...
}
```

> step3: 创建 Mapper 接口

```java
public interface EmpMapper {
    /** 根据 empId 查询用户 */
    Emp queryEmpById(@Param("empId") Integer empId);
}
```

> step4: 创建 Mapper 映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.mapper.EmpMapper">

    <!--  Emp queryEmpById(@Param("empId") Integer empId); -->
    <select id="queryEmpById" resultType="Emp">
        select * from t_emp where emp_id = #{empId}
    </select>

</mapper>
```

> step5: 测试用例

```java
// 1. 读取核心配置文件
InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
// 2. 创建 SqlSessionFactory
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory sqlSessionFactory = builder.build(is);
// 3. 获取 SqlSession
SqlSession sqlSession = sqlSessionFactory.openSession(true);
// 4. 获取 Mapper 代理对象
EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
// 5. 调用 mapper 查询接口
Emp emp = mapper.queryEmpById(1);
System.out.println("SpringMyBatisCaseTest: testMyBatisDefaultCase(), emp=" + emp);
sqlSession.close();
```

## spring 整合 mybatis

Spring 整合 mybatis 的步骤如下:

1. 导入 `mybatis` 整合 `spring` 的相关坐标;
2. 编写 `mapper` 和 `mapper.xml`;
3. 配置 `SqlSessionFactoryBean` 和 `MapperScannerConfigurer`;
4. 编写自测用例;

> step1: 导入 mybatis 整合 spring 的相关坐标

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.9</version>
</dependency>

<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.7</version>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.31</version>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.6</version>
</dependency>
```

> step2: 创建数据映射对象

```java
public class Emp {
    ...
}
```

> step3: 创建 mapper 接口

```java
public interface EmpMapper {
    /** 根据 empId 查询用户 */
    Emp queryEmpById(@Param("empId") Integer empId);
}
```

> step4: 创建 mapper 映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.mapper.EmpMapper">

    <!--  Emp queryEmpById(@Param("empId") Integer empId); -->
    <select id="queryEmpById" resultType="Emp">
        select * from t_emp where emp_id = #{empId}
    </select>

</mapper>
```

> step5: 在 spring 配置文件中配置 `SqlSessionFactoryBean` 和 `MapperScannerConfigurer`

在当前配置文件中处理原先 mybatis 核心配置文件的功能。

// applicationContext.xml
```xml
<!-- spring 整合 mybatis -->

<!-- step1: 配置数据源 -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="url" value="jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>

<!-- step2: 配置 SqlSessionFactoryBean -->
<bean class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <!-- 配置mapper映射文件目录 -->
    <property name="mapperLocations" value="classpath:com/example/mapper/*.xml"/>
    <!-- 配置别名 -->
    <!-- <property name="typeAliases" value="com.example.pojo.Emp"/> -->
    <!-- 批量配置别名 -->
    <property name="typeAliasesPackage" value="com.example.pojo"/>
    <!-- 配置支持驼峰命名映射 -->
    <property name="configuration">
        <bean class="org.apache.ibatis.session.Configuration">
            <property name="mapUnderscoreToCamelCase" value="true"/>
        </bean>
    </property>
</bean>

<!-- step3: 配置 Mapper 扫描包 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.example.mapper"/>
</bean>
```

> step6: 编写自测用例

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
EmpMapper mapper = context.getBean(EmpMapper.class);
Emp emp = mapper.queryEmpById(1);
System.out.println("SpringMyBatisCaseTest: testSpringMyBatisCase(), emp=" + emp);
```

### spring 整合 mybatis 原理分析

整合包 `mybatis-spring` 内部提供了一个 `SqlSessionFactoryBean` 和 `MapperScannerConfigurer` 对象，`SqlSessionFactoryBean` 一旦被实例化，就开始扫描 mapper，然后通过动态代理产生 Mapper 的实现类存储到 spring 容器中，相关类如下:

- `SqlSessionFactoryBean`: 需要进行配置，用于提供 `SqlSessionFactory`;
- `MapperScannerConfigurer`: 需要进行配置，用于扫描指定 mapper 注册 `BeanDefinition`;
- `MapperFactoryBean`: `Mapper` 的 `FactoryBean`，获取指定 mapper 时调用 `getObject()` 方法;
- `ClassPathMapperScanner`: `definition.setAutowireMode(2)` 修改了自动注入状态，因此 `MapperFactoryBean` 中的 `setSqlSessionFactory` 会自动注入进去;


# 自定义命名空间

spring 自定义命名空间流程:

1. 确定命名空间名称、`schema`虚拟路径、标签名称;
2. 编写 `schema` 约束文件 `*.xsd`  `XML schema Definition` ( 例如 `custom-namespace.xsd` );
3. 在类加载路径下创建 `META` 目录，创建约束映射文件 `spring.schemas` 和处理器映射文件 `spring.handlers`;
4. 创建命名空间处理器 ( 例如 `CustomNamespaceHandler` )，实现 `NamespaceHanlder` 接口 或 集成其子类，在 `init()` 函数中注册解析器;
4. 创建标签解析器 ( 例如 `CustomElementBeanDefinitionParser` )，实现 `BeanDefinitionParser` 接口 或 继承其子类，重写 `doParser` 函数解析标签属性;


