package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sheke on 10/19/2015.
 */
@Repository
public class BookDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Book> getAllBooksOnExchange() {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        List<Book> booksOnExchange = criteria.list();
        return booksOnExchange;
    }

    public void postBookOnExchange(Book bookToPost) {
        sessionFactory.getCurrentSession().save(bookToPost);
    }
}
