package com.bookexchange.service;

import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dto.Notification;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sheke on 1/31/2016.
 */
@Service("notificationService")
public class NotificationService {
    @Autowired
    NotificationsDao notificationsDao;

    public List<Notification> getAllNewNotificationsForUser(String userEmail) {
        return notificationsDao.getAllNewNotificaitonsForUser(userEmail);
    }

    public void markNotificationAsSeen(Notification notification) throws BookExchangeInternalException {
        Notification notificationToUpdate = notificationsDao.getNotification(notification.getId()).orElseThrow(() -> new BookExchangeInternalException("No notification for id"));
        notificationToUpdate.setSeen(true);

        notificationsDao.saveNotification(notificationToUpdate);
    }
}
