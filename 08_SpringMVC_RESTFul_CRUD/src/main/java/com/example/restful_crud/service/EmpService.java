package com.example.restful_crud.service;

import com.example.restful_crud.pojo.Emp;

import java.util.List;

public interface EmpService {

    Emp getEmpById(int id);

    int addEmp(Emp emp);

    int updateEmp(Emp emp);

    int deleteEmp(int id);

    List<Emp> getAllEmp();

}
