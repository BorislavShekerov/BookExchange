package com.bookexchange.dao;

import com.bookexchange.dto.BookExchange;
import com.bookexchange.dto.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    public List<BookExchange> getBookExchangesForUser(String userEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookExchange.class)
                .createAlias("bookPostedOnExchange", "bookPosted")
                .createAlias("bookPosted.postedBy", "bookPostedUserPosted")
                .createAlias("bookOfferedInExchange", "bookOffered")
                .createAlias("bookOffered.postedBy", "bookOfferedUserPosted")
                .add(Restrictions.or(Restrictions.eq("bookPostedUserPosted.email", userEmail),Restrictions.eq("bookOfferedUserPosted.email", userEmail)));
        List<BookExchange> booksOnExchange = criteria.list();

        return booksOnExchange;
    }
}
