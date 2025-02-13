package com.example.test;

import com.example.config.SpringConfig;
import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationAnnotationMyBatisCaseTest {

    @Test
    public void testMyBatisCase() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        EmpMapper mapper = context.getBean(EmpMapper.class);
        Emp emp = mapper.queryEmpById(1);
        System.out.println("ApplicationAnnotationMyBatisCaseTest: testMyBatisCase(), emp=" + emp);
    }

}
