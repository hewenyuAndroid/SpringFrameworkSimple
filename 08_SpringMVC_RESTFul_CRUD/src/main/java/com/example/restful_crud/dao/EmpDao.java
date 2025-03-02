package com.example.restful_crud.dao;

import com.example.restful_crud.pojo.Emp;

import java.util.List;

public interface EmpDao {

    Emp queryById(int id);

    int insert(Emp emp);

    int update(Emp emp);

    int deleteById(int id);

    List<Emp> queryAll();

}
