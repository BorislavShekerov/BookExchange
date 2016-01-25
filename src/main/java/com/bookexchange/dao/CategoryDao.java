package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sheke on 10/27/2015.
 */
@Repository
public class CategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addCategory(BookCategory category) {
        sessionFactory.getCurrentSession().saveOrUpdate(category);
    }

    public List<BookCategory> getAllCategories() {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookCategory.class);
        return criteria.list();
    }
}
