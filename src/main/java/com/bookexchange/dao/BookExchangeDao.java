package com.bookexchange.dao;

import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.BookExchange;
import com.bookexchange.dto.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by sheke on 11/13/2015.
 */
@Repository
public class BookExchangeDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addBookExchange(BookExchange bookExchange) {
        sessionFactory.getCurrentSession().save(bookExchange);
    }


}
