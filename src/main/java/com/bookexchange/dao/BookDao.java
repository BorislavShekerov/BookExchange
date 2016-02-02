package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Book> booksOnExchange = criteria.list();
        return booksOnExchange;
    }

    public Optional<Book> getBookForEmail(String bookTitle, String email) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.createAlias("postedBy", "userPosted")
                .add(Restrictions.eq("title", bookTitle))
                .add(Restrictions.eq("userPosted.email", email));

        Book bookForUsername = (Book) criteria.uniqueResult();
        return Optional.of(bookForUsername);
    }

    public void postBookOnExchange(Book bookToPost) {
        sessionFactory.getCurrentSession().save(bookToPost);
    }

    public List<Book> getAllBooksReqestedByUser(String currentUserEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("postedBy", "userPosted")
                .add(Restrictions.ne("userPosted.email", currentUserEmail));

        return (List<Book>) criteria.list();
    }
}
