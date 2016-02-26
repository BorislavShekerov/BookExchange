package com.bookexchange.service;

import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dto.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sheke on 2/20/2016.
 */
@Component("exchangeRatingChecker")
public class ExchangeRatingChecker {
    @Autowired
    private BookExchangeDao bookExchangeDao;
    @Autowired
    private NotificationsDao notificationsDao;

    private static final Logger LOGGER = Logger.getLogger(ExchangeRatingChecker.class);

    public void checkForSuccessfulExchanges(){
        List<DirectBookExchange> successfulDirectExchanges = bookExchangeDao.getSuccessfulDirectExchangesCompletedYesterday();
        List<BookExchangeChain> successfulExchangeChains = bookExchangeDao.getSuccessfulExchangeChainsCompletedYesterday();

        successfulDirectExchanges.stream().filter(directBookExchange -> rateExchangeNotificationNotRaised(directBookExchange.getId())).forEach(directBookExchange1 -> raiseRateExchangePartnerNotification(directBookExchange1));
        successfulExchangeChains.stream().filter(exchangeChain -> rateExchangeNotificationNotRaised(exchangeChain.getId())).forEach(exchangeChain -> raiseRateExchangePartnerNotification(exchangeChain));
    }

    private void raiseRateExchangePartnerNotification(BookExchangeChain exchangeChain) {
        List<ExchangeChainRequest> exchangeChainRequests = exchangeChain.getExchangeChainRequests();

        exchangeChainRequests.stream().map(exchangeChainRequest -> buildExchangeRatingNotification(exchangeChainRequest.getRequestFor(), exchangeChainRequest.getUserChoosingFrom(), exchangeChainRequest.getId()))
                .forEach(exchangeRatingNotification -> notificationsDao.saveNotification(exchangeRatingNotification));
    }

    private ExchangeRatingNotification buildExchangeRatingNotification(User userRating,User userRated,int exchangeId) {
        return new ExchangeRatingNotification.ExchangeRatingNotificationBuilder()
                .setMessage("You recently completed an exchange with " + userRated.getFullName() + ", rate him and get some VIP TOKENS!")
                .setNotificationType(NotificationType.RATE_EXCHANGE_RATING)
                .setDateCreated(LocalDateTime.now())
                .setExchangeId(exchangeId)
                .setUserNotified(userRating)
                .setUserRated(userRated)
                .build();
    }

    private boolean rateExchangeNotificationNotRaised(int exchangeChain) {
        return !notificationsDao.getExchangeChainNotificationForExchange(exchangeChain).isPresent();
    }

    private void raiseRateExchangePartnerNotification(DirectBookExchange directBookExchange) {
        User exchangeInitiator = directBookExchange.getExchangeInitiator();
        User userUnderOffer = directBookExchange.getUserUnderOffer();
        int exchangeId = directBookExchange.getId();

        notificationsDao.saveNotification(buildExchangeRatingNotification(exchangeInitiator, userUnderOffer, exchangeId));
        notificationsDao.saveNotification(buildExchangeRatingNotification(userUnderOffer, exchangeInitiator, exchangeId));
    }

    public void setBookExchangeDao(BookExchangeDao bookExchangeDao) {
        this.bookExchangeDao = bookExchangeDao;
    }

    public void setNotificationsDao(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }
}
