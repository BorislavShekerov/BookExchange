package com.bookexchange.service;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.*;
import com.bookexchange.dto.frontend.ExchangeAccumulator;
import com.bookexchange.dto.frontend.ExchangeOrder;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by sheke on 11/17/2015.
 */
@Service("exchangeService")
@Transactional
public class ExchangeService {

    @Autowired
    private BookExchangeDao bookExchangeDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationsDao notificationsDao;
    @Value("${notification.new_exchange_request}")
    private String newExchangeRequestMessage;

    public void recordExchange(ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        Book bookUnderOffer = bookDao.getBookForEmail(exchangeOrder.getBookUnderOffer(), exchangeOrder.getBookUnderOfferOwner()).orElseThrow(() -> new BookExchangeInternalException("Book under offer not found"));
        Book bookOfferedInExchange = bookDao.getBookForEmail(exchangeOrder.getBookOfferedInExchange(), exchangeOrder.getBookOfferedInExchangeOwner()).orElseThrow(() -> new BookExchangeInternalException("Book offered in exhange not found"));

        DirectBookExchange exchangeToRecord = buildBookExchange(bookUnderOffer, bookOfferedInExchange);

        bookExchangeDao.addBookExchange(exchangeToRecord);
        updateUserDataWithNewExchange(bookUnderOffer.getPostedBy(), bookOfferedInExchange.getPostedBy(), exchangeToRecord);
        addNewExchangeRequestNotification(bookUnderOffer.getPostedBy(),bookOfferedInExchange.getOwnerName());
    }

    private void addNewExchangeRequestNotification(User postedBy,String exchangeInitiator) {
        Notification newExchangeRequestNotification = new Notification.NotificationBuilder().setMessage(newExchangeRequestMessage+" "+exchangeInitiator).setUserNotified(postedBy).setDateCreated(LocalDateTime.now()).build();

        notificationsDao.saveNotification(newExchangeRequestNotification);
    }

    private void updateUserDataWithNewExchange(User bookUnderOfferUser, User exchangeInitiator, DirectBookExchange exchangeToRecord) {
        bookUnderOfferUser.addBookExchange(exchangeToRecord);
        exchangeInitiator.addBookExchange(exchangeToRecord);

        userDao.updateUser(bookUnderOfferUser);
        userDao.updateUser(exchangeInitiator);
    }

    private DirectBookExchange buildBookExchange(Book bookPostedOnExchange, Book bookOfferedInExchange) {
        DirectBookExchange directBookExchange = new DirectBookExchange.BookExchangeBuilder().setBookPostedOnExchange(bookPostedOnExchange)
                .setBookOfferedInExchange(bookOfferedInExchange)
                .setDateCreated(LocalDateTime.now())
                .buildBookExchange();

        return directBookExchange;
    }

    public ExchangeAccumulator getUserCurrentExchanges(String userEmail){
        List<DirectBookExchange> directExchangesForUser = bookExchangeDao.getBookExchangesForUser(userEmail);
        List<BookExchangeChain> exchangeChainsInitiatedByUser = bookExchangeDao.getExchangeChainsInitiatedByUser(userEmail);

        return new ExchangeAccumulator(directExchangesForUser, exchangeChainsInitiatedByUser).sortByCreationDate();
    }
}
