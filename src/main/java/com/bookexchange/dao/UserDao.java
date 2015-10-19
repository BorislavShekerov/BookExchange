package com.bookexchange.dao;

import com.bookexchange.dto.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by sheke on 10/19/2015.
 */
@Repository
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

        public void addUser(User user) {
            sessionFactory.getCurrentSession().save(user);
        }

}
