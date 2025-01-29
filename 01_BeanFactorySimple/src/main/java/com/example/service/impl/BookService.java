package com.example.service.impl;

import com.example.dao.BookDao;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BookService {

    private List<String> bookNameList;

    private List<BookDao> bookDaoList;

    private Map<String, BookDao> bookDaoMap;

    private Properties bookDaoProperties;

    public void setBookNameList(List<String> bookNameList) {
        this.bookNameList = bookNameList;
    }

    public void setBookDaoList(List<BookDao> bookDaoList) {
        this.bookDaoList = bookDaoList;
    }

    public void setBookDaoMap(Map<String, BookDao> bookDaoMap) {
        this.bookDaoMap = bookDaoMap;
    }

    public void setBookDaoProperties(Properties bookDaoProperties) {
        this.bookDaoProperties = bookDaoProperties;
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookNameList=" + bookNameList +
                ", bookDaoList=" + bookDaoList +
                ", bookDaoMap=" + bookDaoMap +
                ", bookDaoProperties=" + bookDaoProperties +
                '}';
    }

}
