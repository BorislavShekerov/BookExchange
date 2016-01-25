package com.bookexchange.dao;

import com.bookexchange.dto.User;
import com.bookexchange.dto.UserRole;
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
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    public Optional<User> findUserByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        return Optional.ofNullable((User) criteria.uniqueResult());
    }

    public List<User> getAllUsers(){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(User.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

    public void addUserRole(UserRole userRole){
        sessionFactory.getCurrentSession().save(userRole);
    }

}
