package com.example.service.impl;

import com.example.dao.EmpDao;

public class EmpService {

    private EmpDao empDao;

    // 需要有 set 函数
    public void setEmpDao(EmpDao empDao) {
        this.empDao = empDao;
    }

    public void save() {
        empDao.save();
    }

}
