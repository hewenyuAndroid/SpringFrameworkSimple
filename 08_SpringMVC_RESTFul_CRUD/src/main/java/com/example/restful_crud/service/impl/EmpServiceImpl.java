package com.example.restful_crud.service.impl;

import com.example.restful_crud.dao.EmpDao;
import com.example.restful_crud.pojo.Emp;
import com.example.restful_crud.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpDao empDao;

    @Override
    public Emp getEmpById(int id) {
        return empDao.queryById(id);
    }

    @Override
    public int addEmp(Emp emp) {
        return empDao.insert(emp);
    }

    @Override
    public int updateEmp(Emp emp) {

        Integer id = emp.getId();
        if (id == null) {
            return 0;
        }

        Emp empById = empDao.queryById(id);

        if (StringUtils.hasText(emp.getName())) {
            empById.setName(emp.getName());
        }
        if (StringUtils.hasText(emp.getEmail())) {
            empById.setEmail(emp.getEmail());
        }
        if (StringUtils.hasText(emp.getGender())) {
            empById.setGender(emp.getGender());
        }
        if (StringUtils.hasText(emp.getAddress())) {
            empById.setAddress(emp.getAddress());
        }
        if (emp.getAge() != null) {
            empById.setAge(emp.getAge());
        }
        if (emp.getSalary() != null) {
            empById.setSalary(emp.getSalary());
        }

        return empDao.update(empById);
    }

    @Override
    public int deleteEmp(int id) {
        return empDao.deleteById(id);
    }

    @Override
    public List<Emp> getAllEmp() {
        return empDao.queryAll();
    }
}
