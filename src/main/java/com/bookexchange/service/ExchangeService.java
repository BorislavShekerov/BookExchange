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
import java.util.stream.Collectors;

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
    @Value("${notification.direct_exchange_rejected_initiator}")
    private String exchangeRejectedInitiatorMessage;
    @Value("${notification.direct_exchange_rejected_userUnderOffer")
    private String excahngeRejecteUserUnderOfferdMessage;
    @Value("${notification.direct_exchange_accepted")
    private String exchangeAccepted;

    public void recordExchange(String initiatorEmail,ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        Book bookUnderOffer = bookDao.getBookPostedByUser(exchangeOrder.getBookUnderOffer(), exchangeOrder.getBookUnderOfferOwner()).orElseThrow(() -> new BookExchangeInternalException("Book under offer not found"));
        User exchangeInitiator = userDao.findUserByEmail(initiatorEmail).orElseThrow(() -> new BookExchangeInternalException("No user found for email"));
        DirectBookExchange exchangeToRecord = buildBookExchange(bookUnderOffer,exchangeInitiator);

        bookExchangeDao.saveBookExchange(exchangeToRecord);
        updateUserDataWithNewExchange(bookUnderOffer.getPostedBy(), exchangeToRecord);
        addNewExchangeRequestNotification(bookUnderOffer.getPostedBy(),exchangeOrder.getBookUnderOfferOwner());
    }

    private void addNewExchangeRequestNotification(User postedBy,String exchangeInitiator) {
        Notification newExchangeRequestNotification = new Notification.NotificationBuilder().setMessage(newExchangeRequestMessage+" "+exchangeInitiator).setUserNotified(postedBy).setDateCreated(LocalDateTime.now()).build();

        notificationsDao.saveNotification(newExchangeRequestNotification);
    }

    private void updateUserDataWithNewExchange(User exchangeInitiator, DirectBookExchange exchangeToRecord) {
        exchangeInitiator.addBookExchange(exchangeToRecord);

        userDao.saveUser(exchangeInitiator);
    }

    private DirectBookExchange buildBookExchange(Book bookPostedOnExchange, User exchangeInitiator) {
        DirectBookExchange directBookExchange = new DirectBookExchange.BookExchangeBuilder().setBookRequested(bookPostedOnExchange)
                .setExchangeInitiator(exchangeInitiator)
                .setDateCreated(LocalDateTime.now())
                .buildBookExchange();

        return directBookExchange;
    }

    public ExchangeAccumulator getExchangesInitiatedByUser(String userEmail){
        List<DirectBookExchange> directExchangesForUser = bookExchangeDao.getDirectExchangesInitiatedByUser(userEmail);
        List<BookExchangeChain> exchangeChainsInitiatedByUser = bookExchangeDao.getExchangeChainsInitiatedByUser(userEmail);

        return new ExchangeAccumulator(directExchangesForUser, exchangeChainsInitiatedByUser).sortByCreationDate();
    }

    public void rejecDirecttExchange(String userEmail, int id) throws BookExchangeInternalException {
        DirectBookExchange directExchangeToReject = bookExchangeDao.getDirectBookExchange(id).orElseThrow(() -> new BookExchangeInternalException("No exchange found"));
        directExchangeToReject.setOver(true);
        directExchangeToReject.setSuccessful(false);

        bookExchangeDao.saveBookExchange(directExchangeToReject);
        notifyOtherExchangeParticipant(userEmail, directExchangeToReject);
    }

    private void notifyOtherExchangeParticipant(String userEmail, DirectBookExchange directExchangeToReject) {
        if(directExchangeToReject.getExchangeInitiatorEmail().equals(userEmail)){
            raiseDirectExchangeRejectedNotification(excahngeRejecteUserUnderOfferdMessage,directExchangeToReject.getUserUnderOffer());
        }else{
            raiseDirectExchangeRejectedNotification(exchangeRejectedInitiatorMessage,directExchangeToReject.getExchangeInitiator());
        }
    }

    private void raiseDirectExchangeRejectedNotification(String notificationMessage, User userToNotify){
        notificationsDao.saveNotification(new Notification.NotificationBuilder()
                .setMessage(notificationMessage)
                .setNotificationType(NotificationType.DIREXT_EXHANGE_REJECTED)
                .setDateCreated(LocalDateTime.now())
                .setUserNotified(userToNotify)
                .build());
    }

    public ExchangeAccumulator getExchangeRequestsReceivedByUser(String userEmail) {
        List<DirectBookExchange> directExchangesForUser = bookExchangeDao.getDirectExchangesOfferedToUser(userEmail);
        List<BookExchangeChain> exchangeChainsInitiatedByUser = bookExchangeDao.getExchangeChainsUserInvitedIn(userEmail).stream().map(exchangeChainRequest -> exchangeChainRequest.getParentExchangeChain()).collect(Collectors.toList());

        return new ExchangeAccumulator(directExchangesForUser, exchangeChainsInitiatedByUser).sortByCreationDate();
    }

    public void acceptDirectExchange(String userEmail,int bookRequestedId, int exchangeId) throws BookExchangeInternalException {
        DirectBookExchange directBookExchange = bookExchangeDao.getDirectBookExchange(exchangeId).orElseThrow(() -> new BookExchangeInternalException("No exchanges found"));
        Book bookRequestedInExchange = bookDao.getBookForId(bookRequestedId).orElseThrow(() -> new BookExchangeInternalException("No book found"));

        updateExchangeDetails(directBookExchange, bookRequestedInExchange);
        bookExchangeDao.saveBookExchange(directBookExchange);
        notificationsDao.saveNotification(new Notification.NotificationBuilder().setMessage(exchangeAccepted).setUserNotified(bookRequestedInExchange.getPostedBy()).setNotificationType(NotificationType.DIRECT_EXCHANGE_ACCEPTED).setDateCreated(LocalDateTime.now()).build());
    }

    private void updateExchangeDetails(DirectBookExchange directBookExchange, Book bookRequestedInExchange) {
        directBookExchange.setBookOfferedInExchange(bookRequestedInExchange);
        directBookExchange.setOver(true);
        directBookExchange.setSuccessful(true);
    }
}
