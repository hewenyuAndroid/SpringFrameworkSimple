package com.example.restful_crud;

import com.example.restful_crud.pojo.Emp;
import com.example.restful_crud.service.EmpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class EmpServiceCaseTest {

    @Autowired
    private EmpService empService;

    @Test
    public void testQueryById() {
        Emp emp = empService.getEmpById(1);
        System.out.println("EmpServiceCaseTest: testQueryById(), emp=" + emp);
    }

    @Test
    public void testAddEmp() {
        Emp emp = new Emp();
        emp.setName("wangwu")
                .setAge(23)
                .setEmail("333@qq.com")
                .setGender("女")
                .setAddress("zhejiang")
                .setSalary(new BigDecimal("31000.00"));
        int update = empService.addEmp(emp);
        System.out.println("EmpServiceCaseTest: testAddEmp(), update=" + update);
    }


    @Test
    public void testUpdateEmp() {
        Emp emp = new Emp();
        emp.setSalary(new BigDecimal("32500.00"))
                .setId(3);
        int update = empService.updateEmp(emp);
        System.out.println("EmpServiceCaseTest: testUpdateEmp(), update=" + update);
    }

    @Test
    public void testDeleteEmp() {
        int update = empService.deleteEmp(2);
        System.out.println("EmpServiceCaseTest: testDeleteEmp(), update=" + update);
    }


}
