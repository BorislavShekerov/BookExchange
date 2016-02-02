package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.Notification;
import com.bookexchange.dto.NotificationType;
import com.bookexchange.dto.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by sheke on 1/18/2016.
 */
@Repository
public class NotificationsDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void saveNotification(Notification notification){
        sessionFactory.getCurrentSession().saveOrUpdate(notification);
    }

    public List<Notification> getAllNewNotificaitonsForUser(String userEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Notification.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("userNotified", "user")
                .add(Restrictions.eq("user.email", userEmail))
                .add(Restrictions.eq("isSeen", false));

        List<Notification> newNotifications = (List<Notification>) criteria.list();
        return newNotifications;
    }

    public Optional<Notification> getNotification(long notificationId){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Notification.class);
                criteria.add(Restrictions.eq("id", notificationId));

        return Optional.ofNullable((Notification) criteria.uniqueResult());
    }
}
