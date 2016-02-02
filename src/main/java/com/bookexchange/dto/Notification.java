package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sheke on 11/16/2015.
 */
@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "MESSAGE")
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NOTIFIED")
    @JsonIgnore
    private User userNotified;
    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;
    @Column(name="NOTIFICATION_TYPE")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @Column(name = "IS_SEEN")
    private boolean isSeen;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public User getUserNotified() {
        return userNotified;
    }

    public void setUserNotified(User userNotified) {
        this.userNotified = userNotified;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return userNotified != null ? userNotified.equals(that.userNotified) : that.userNotified == null;

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (userNotified != null ? userNotified.hashCode() : 0);
        return result;
    }

    public static class NotificationBuilder {
        private Notification notification;

        public NotificationBuilder(){
            notification = new Notification();
        }

        public NotificationBuilder setMessage(String message) {
            notification.setMessage(message); return this;
        }

        public NotificationBuilder setUserNotified(User userNotified) {
            notification.setUserNotified(userNotified); return this;
        }

        public NotificationBuilder isSeen(boolean isSeen) {
            notification.setSeen(isSeen); return this;
        }

        public NotificationBuilder setNotificationType(NotificationType notificationType) {
            notification.setNotificationType(notificationType); return this;
        }

        public NotificationBuilder setDateCreated(LocalDateTime localDateTime) {
            notification.setDateCreated(localDateTime); return this;
        }

        public Notification build(){
            return notification;
        }

    }
}
