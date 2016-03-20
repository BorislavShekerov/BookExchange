package com.bookexchange.dto;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sheke on 3/18/2016.
 */
@Entity
@Table(name = "DIRECT_EXCHANGE_INVITATION_NOTIFICATIONS")
@PrimaryKeyJoinColumn(name="NOTIFICATION_ID")
public class DirectExchangeInvitationNotification extends Notification {
    @OneToOne
    @JoinColumn(name="DIRECT_EXCHANGE_ID")
    private DirectBookExchange directBookExchange;

    public DirectBookExchange getDirectBookExchange() {
        return directBookExchange;
    }

    public void setDirectBookExchange(DirectBookExchange directBookExchange) {
        this.directBookExchange = directBookExchange;
    }

    public static class DirectExchangeNotificationBuilder {
        private DirectExchangeInvitationNotification notification;

        public DirectExchangeNotificationBuilder(){
            notification = new DirectExchangeInvitationNotification();
        }

        public DirectExchangeNotificationBuilder setMessage(String message) {
            notification.setMessage(message); return this;
        }

        public DirectExchangeNotificationBuilder setUserNotified(User userNotified) {
            notification.setUserNotified(userNotified); return this;
        }

        public DirectExchangeNotificationBuilder isSeen(boolean isSeen) {
            notification.setSeen(isSeen); return this;
        }

        public DirectExchangeNotificationBuilder setNotificationType(NotificationType notificationType) {
            notification.setNotificationType(notificationType); return this;
        }

        public DirectExchangeNotificationBuilder setDateCreated(LocalDateTime localDateTime) {
            notification.setDateCreated(localDateTime); return this;
        }

        public DirectExchangeNotificationBuilder setDirectBookExchange(DirectBookExchange directBookExchange) {
            notification.setDirectBookExchange(directBookExchange); return this;
        }

        public DirectExchangeInvitationNotification build(){
            return notification;
        }

    }
}
