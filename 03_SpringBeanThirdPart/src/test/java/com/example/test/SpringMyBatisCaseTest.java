package com.example.test;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class SpringMyBatisCaseTest {

    @Test
    public void testMyBatisDefaultCase() throws IOException {
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
    }

    @Test
    public void testSpringMyBatisCase() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        EmpMapper mapper = context.getBean(EmpMapper.class);
        Emp emp = mapper.queryEmpById(1);
        System.out.println("SpringMyBatisCaseTest: testSpringMyBatisCase(), emp=" + emp);
    }

}
