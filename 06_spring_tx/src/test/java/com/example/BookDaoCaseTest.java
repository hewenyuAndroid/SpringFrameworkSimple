package com.example;

import com.example.bean.Book;
import com.example.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookDaoCaseTest {

    @Autowired
    private BookDao bookDao;

    @Test
    public void testBookDaoQueryById() {
        System.out.println("BookDaoCaseTest: testBookDaoQueryById(), begin.");
        Book book = bookDao.queryBookById(1);
        System.out.println("book = " + book);
        System.out.println("BookDaoCaseTest: testBookDaoQueryById(), end.");
    }
}
