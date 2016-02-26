package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sheke on 2/22/2016.
 */
@Entity
@Table(name = "EXCHANGE_RATING_NOTIFICATIONS")
@PrimaryKeyJoinColumn(name="NOTIFICATION_ID")
public class ExchangeRatingNotification extends Notification {
    @Column(name = "EXCHANGE_ID")
    private int exchangeId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_RATED_ID", nullable = false)
    private User userRated;

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public User getUserRated() {
        return userRated;
    }

    public void setUserRated(User userRated) {
        this.userRated = userRated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRatingNotification)) return false;
        if (!super.equals(o)) return false;

        ExchangeRatingNotification that = (ExchangeRatingNotification) o;

        if (exchangeId != that.exchangeId) return false;
        return userRated.equals(that.userRated);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + exchangeId;

        if(userRated != null){
            result = 31 * result + userRated.hashCode();
        }
        return result;
    }

    public static class ExchangeRatingNotificationBuilder{
        ExchangeRatingNotification exchangeRatingNotification;

        public ExchangeRatingNotificationBuilder(){
            exchangeRatingNotification = new ExchangeRatingNotification();
        }

        public ExchangeRatingNotificationBuilder setMessage(String message) {
            exchangeRatingNotification.setMessage(message); return this;
        }

        public ExchangeRatingNotificationBuilder setUserNotified(User userNotified) {
            exchangeRatingNotification.setUserNotified(userNotified); return this;
        }

        public ExchangeRatingNotificationBuilder isSeen(boolean isSeen) {
            exchangeRatingNotification.setSeen(isSeen); return this;
        }

        public ExchangeRatingNotificationBuilder setNotificationType(NotificationType notificationType) {
            exchangeRatingNotification.setNotificationType(notificationType); return this;
        }

        public ExchangeRatingNotificationBuilder setDateCreated(LocalDateTime localDateTime) {
            exchangeRatingNotification.setDateCreated(localDateTime); return this;
        }

        public ExchangeRatingNotificationBuilder setExchangeId(int exchangeId) {
            exchangeRatingNotification.setExchangeId(exchangeId); return this;
        }

        public ExchangeRatingNotificationBuilder setUserRated(User userToRate) {
            exchangeRatingNotification.setUserRated(userToRate); return this;
        }

        public ExchangeRatingNotification build(){
            return exchangeRatingNotification;
        }
    }
}
