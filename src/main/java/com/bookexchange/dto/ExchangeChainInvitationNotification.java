package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sheke on 3/18/2016.
 */
@Entity
@Table(name = "EXCHANGE_CHAIN_INVITATION_NOTIFICATIONS")
@PrimaryKeyJoinColumn(name="NOTIFICATION_ID")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class ExchangeChainInvitationNotification extends Notification{
    @OneToOne
    @JoinColumn(name="EXCHANGE_CHAIN_ID")
    private BookExchangeChain bookExchangeChain;

    public BookExchangeChain getBookExchangeChain() {
        return bookExchangeChain;
    }

    public void setBookExchangeChain(BookExchangeChain bookExchangeChain) {
        this.bookExchangeChain = bookExchangeChain;
    }

    public static class ExchangeChainNotificationBuilder {
        private ExchangeChainInvitationNotification notification;

        public ExchangeChainNotificationBuilder(){
            notification = new ExchangeChainInvitationNotification();
        }

        public ExchangeChainNotificationBuilder setMessage(String message) {
            notification.setMessage(message); return this;
        }

        public ExchangeChainNotificationBuilder setUserNotified(User userNotified) {
            notification.setUserNotified(userNotified); return this;
        }

        public ExchangeChainNotificationBuilder isSeen(boolean isSeen) {
            notification.setSeen(isSeen); return this;
        }

        public ExchangeChainNotificationBuilder setNotificationType(NotificationType notificationType) {
            notification.setNotificationType(notificationType); return this;
        }

        public ExchangeChainNotificationBuilder setDateCreated(LocalDateTime localDateTime) {
            notification.setDateCreated(localDateTime); return this;
        }

        public ExchangeChainNotificationBuilder setBookExchangeChain(BookExchangeChain bookExchangeChain) {
            notification.setBookExchangeChain(bookExchangeChain); return this;
        }

        public ExchangeChainInvitationNotification build(){
            return notification;
        }

    }
}
