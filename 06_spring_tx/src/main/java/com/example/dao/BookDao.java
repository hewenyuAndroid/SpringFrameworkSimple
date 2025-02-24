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

    /**
     * 插入图书
     *
     * @param book 新增的图书
     * @return 更新的行数
     */
    public int insert(Book book) {

        // 1. 创建 sql
        String sql = "insert into book(bookName, price, stock) values(?,?,?)";

        // 执行插入
        int result = jdbcTemplate.update(sql, book.getBookName(), book.getPrice(), book.getStock());

        return result;
    }


}
