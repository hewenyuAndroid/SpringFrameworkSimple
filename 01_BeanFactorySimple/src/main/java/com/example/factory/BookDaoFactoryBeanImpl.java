package com.example.factory;

import com.example.dao.BookDao;
import org.springframework.beans.factory.FactoryBean;

public class BookDaoFactoryBeanImpl implements FactoryBean<BookDao> {
    public BookDaoFactoryBeanImpl() {
        System.out.println("BookDaoFactoryBeanImpl(): constructor.");
    }

    @Override
    public BookDao getObject() throws Exception {
        System.out.println("BookDaoFactoryBeanImpl(): getObject().");
        return new BookDao();
    }

    @Override
    public Class<?> getObjectType() {
        return BookDao.class;
    }
}
