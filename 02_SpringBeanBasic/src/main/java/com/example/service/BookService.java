package com.example.service;

import com.example.dao.BookDao;

public class BookService {

    private BookDao bookDao;

    public BookService() {
        System.out.println("BookService: constructor");
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
        System.out.println("BookService: setBookDao() bookDao=" + bookDao);
    }

}
