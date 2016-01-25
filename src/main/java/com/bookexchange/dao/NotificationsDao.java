package com.bookexchange.dao;

import com.bookexchange.dto.Notification;
import com.bookexchange.dto.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by sheke on 1/18/2016.
 */
@Repository
public class NotificationsDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addNotification(Notification notification){
        sessionFactory.getCurrentSession().save(notification);
    }

}
