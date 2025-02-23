package com.example.dao;

import com.example.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 根据id查询book
     *
     * @param id 书本id
     */
    public Book queryBookById(int id) {

        // 1. 创建sql
        String sql = "select * from book where id = ?";

        // 执行查询
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);

        return book;
    }

}
