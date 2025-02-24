package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据用户id更新余额
     *
     * @param id    用户id
     * @param delta 扣除的余额
     * @return 更新的行数
     */
    public int subtractBalanceById(Integer id, BigDecimal delta) {

        // 1. 创建 sql
        String sql = "update account set balance = balance - ? where id = ?";

        // 2. 执行sql
        int result = jdbcTemplate.update(sql, delta, id);

        return result;
    }

}
