package com.example;

import com.example.bean.Book;
import com.example.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

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

    @Test
    public void testInsertBook() {
        System.out.println("BookDaoCaseTest: testInsertBook(), begin.");
        Book book = new Book();
        book.setBookName("Thinking in java");
        book.setPrice(new BigDecimal("99.00"));
        book.setStock(20);
        int result = bookDao.insert(book);
        System.out.println("update row = " + result);
        System.out.println("BookDaoCaseTest: testInsertBook(), end.");
    }

    @Test
    public void testUpdateBook() {
        System.out.println("BookDaoCaseTest: testUpdateBook(), begin.");
        Book book = new Book();
        book.setId(4);
        book.setBookName("Kotlin实战");
        book.setPrice(new BigDecimal("88.88"));
        book.setStock(21);
        int result = bookDao.updateBookById(book);
        System.out.println("update row = " + result);
        System.out.println("BookDaoCaseTest: testUpdateBook(), end.");
    }

    @Test
    public void testUpdateDelete() {
        System.out.println("BookDaoCaseTest: testUpdateDelete(), begin.");
        int result = bookDao.deleteBookById(4);
        System.out.println("update row = " + result);
        System.out.println("BookDaoCaseTest: testUpdateDelete(), end.");
    }

}
