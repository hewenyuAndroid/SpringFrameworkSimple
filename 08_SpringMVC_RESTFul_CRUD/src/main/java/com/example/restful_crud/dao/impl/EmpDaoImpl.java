package com.example.restful_crud.dao.impl;

import com.example.restful_crud.dao.EmpDao;
import com.example.restful_crud.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpDaoImpl implements EmpDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Emp queryById(int id) {
        String sql = "select * from t_emp where id = ?";
        try {
            Emp emp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Emp.class), id);
            return emp;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insert(Emp emp) {
        String sql = "insert into t_emp(name, age, email, gender, address, salary) values(?,?,?,?,?,?)";
        int update = jdbcTemplate.update(
                sql,
                emp.getName(),
                emp.getAge(),
                emp.getEmail(),
                emp.getGender(),
                emp.getAddress(),
                emp.getSalary()
        );
        return update;
    }

    @Override
    public int update(Emp emp) {
        String sql = "update t_emp set name=?, age=?, email=?, gender=?, address=?, salary=? where id=?";
        int update = jdbcTemplate.update(
                sql,
                emp.getName(),
                emp.getAge(),
                emp.getEmail(),
                emp.getGender(),
                emp.getAddress(),
                emp.getSalary(),
                emp.getId()
        );
        return update;
    }

    @Override
    public int deleteById(int id) {
        String sql = "delete from t_emp where id = ?";
        int update = jdbcTemplate.update(sql, id);
        return update;
    }

    @Override
    public List<Emp> queryAll() {
        String sql = "select * from t_emp";
        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class));
        return list;
    }
}
