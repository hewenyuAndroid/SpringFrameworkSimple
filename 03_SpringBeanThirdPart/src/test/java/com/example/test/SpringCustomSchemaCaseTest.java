package com.example.test;

import com.example.pojo.CustomBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCustomSchemaCaseTest {

    @Test
    public void testCustomSchema() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomBean customBean1 = applicationContext.getBean("001", CustomBean.class);
        CustomBean customBean2 = applicationContext.getBean("002", CustomBean.class);
        System.out.println("SpringCustomSchemaCaseTest: testCustomSchema(), customBean1=" + customBean1 + ", customBean2=" + customBean2);
        /*
        SpringCustomSchemaCaseTest: testCustomSchema(),
        customBean1=CustomBean{id='001', name='zhangsan', tag='zhangsan'},
        customBean2=CustomBean{id='002', name='lisi', tag='lisi'}
         */
    }

}
