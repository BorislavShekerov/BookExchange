package com.bookexchange.service;

import com.bookexchange.dao.*;
import com.bookexchange.dto.*;
import com.bookexchange.dto.frontend.ExchangeAcceptance;
import com.bookexchange.dto.frontend.ExchangeChainProposal;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.graph.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by sheke on 1/30/2016.
 */
@Service("exchangeChainService")
@Transactional
public class ExchangeChainService {

    @Autowired
    private BookExchangeDao bookExchangeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private GraphDao graphDao;
    @Autowired
    private NotificationsDao notificationsDao;
    @Value("${notification.new_exchange_chain_request}")
    public String newExchangeChainRequestMessage;
    @Value("${notification.exchange_chain_rejection_participants}")
    public String exchangeChainRejectMessageForParticipants;
    @Value("${notification.exchange_chain_rejection_initiator}")
    public String exchangeChainRejectMessageForInitiator;
    @Value("${notification.exchange_chain_acceptance_initiator}")
    public String exchangeChainAcceptanceMessageForInitiator;
    @Value("${notification.exchange_chain_success_participants}")
    public String exchangeChainSuccessParticipants;
    @Value("${notification.exchange_chain_success_initiator}")
    public String exchangeChainSuccessInitiator;


    public void rejectExchangeChain(String userRejectingEmail, int exchangeChainID) throws BookExchangeInternalException {
        BookExchangeChain exchangeChainRequest = bookExchangeDao.getExchangeChainForId(exchangeChainID).orElseThrow(() -> new BookExchangeInternalException("No exchange chain for id"));
        User userRejected = userDao.findUserByEmail(userRejectingEmail).get();

        exchangeChainRequest.setSuccessful(false);
        exchangeChainRequest.setOver(true);
        exchangeChainRequest.setChainBreaker(userRejected);
        exchangeChainRequest.setDateCompleted(LocalDateTime.now());

        exchangeChainRequest.markExchangeChainRequestRejectedFromUser(userRejectingEmail);

        bookExchangeDao.saveBookExchangeChain(exchangeChainRequest);
        notifyUsersForChainFailure(exchangeChainRequest, userRejected);
    }

    public void registerExchangeChain(List<User> usersOnChain, String bookRequestedTitle, Graph closedComponent) throws BookExchangeInternalException {
        List<ExchangeChainRequest> exchangeChainRequests = constructExchangeChainRequests(usersOnChain);
        Book bookRequested = usersOnChain.get(usersOnChain.size() - 1).getBookPostedByUser(bookRequestedTitle).orElseThrow(() -> new BookExchangeInternalException("No such book posted by user"));

        closedComponent.increaseExploredPathsCount();
        closedComponent.removeDuplicates();
        closedComponent.fixMappings();

        BookExchangeChain exchangeChain = new BookExchangeChain.BookExchangeChainBuilder().setExchangeInitiator(usersOnChain.get(0)).setExchangeChainRequests(exchangeChainRequests).setDateCreated(LocalDateTime.now()).setClosedComponent(closedComponent).buildBookExchangeChain();

        exchangeChain.addRequestedBook(new BookRequestedInChain.BookRequestedInChainBuilder().setChainRequestedIn(exchangeChain).setRequestedBook(bookRequested).setRequestedBy(usersOnChain.get(0)).buildBookExchangeChain());
        updateExchangeChainRequests(exchangeChainRequests, exchangeChain);

        bookExchangeDao.saveBookExchangeChain(exchangeChain);

        addExchangeChainInvitationNotification(usersOnChain, exchangeChain);
    }

    public List<User> getChainDetailsForUser(int newExchangeChainRequestId, String userEmail) throws BookExchangeInternalException {
        BookExchangeChain bookExchangeChain = bookExchangeDao.getExchangeChainForId(newExchangeChainRequestId).orElseThrow(() -> new BookExchangeInternalException("No exchange chain for id"));
        ExchangeChainRequest exchangeRequestForUser = bookExchangeChain.getExchangeChainRequestForUser(userEmail).orElseThrow(() -> new BookExchangeInternalException("User not in exchange chain"));
        List<User> usersToExchangeWith = new ArrayList<>();

        usersToExchangeWith.add(exchangeRequestForUser.getUserChoosingFrom());
        usersToExchangeWith.add(exchangeRequestForUser.getUserOfferingTo());

        return usersToExchangeWith;
    }

    public void acceptExchangeChain(String userEmail, ExchangeAcceptance exchangeChainAcceptance) throws BookExchangeInternalException {
        BookExchangeChain bookExchangeChain = bookExchangeDao.getExchangeChainForId(exchangeChainAcceptance.getExchangeId()).orElseThrow(() -> new BookExchangeInternalException("No exchange chain for id"));
        User acceptingUser = userDao.findUserByEmail(userEmail).get();

        bookExchangeChain.markExchangeChainRequestAcceptedFromUser(userEmail);
        addRequestedBookToExchangeChain(userEmail, exchangeChainAcceptance, bookExchangeChain);

        raiseAcceptanceNotifications(acceptingUser,bookExchangeChain);
        bookExchangeDao.saveBookExchangeChain(bookExchangeChain);
    }

    private void raiseAcceptanceNotifications(User acceptingUser, BookExchangeChain bookExchangeChain) {
        if(bookExchangeChain.areAllChainRequestsAccepted()){
            bookExchangeChain.setSuccessful(true);
            bookExchangeChain.setOver(true);
            bookExchangeChain.getUsersToBeNotified().forEach(user ->  sendChainSuccessNotification(user,exchangeChainSuccessParticipants));
            sendChainSuccessNotification(bookExchangeChain.getExchangeInitiator(),exchangeChainSuccessInitiator);
        }else {
            notifyExchangeInitiatorForAcceptance(acceptingUser.getFullName(),bookExchangeChain.getExchangeInitiator());
        }
    }

    private void sendChainSuccessNotification(User exchangeInitiator, String notificationMessage) {
        Notification acceptanceNotification = new Notification.NotificationBuilder().setUserNotified(exchangeInitiator).setMessage(notificationMessage).setNotificationType(NotificationType.EXCHANGE_CHAIN_SUCCESS).isSeen(false).setDateCreated(LocalDateTime.now()).build();
        notificationsDao.saveNotification(acceptanceNotification);
    }

    private void notifyExchangeInitiatorForAcceptance(String userEmail, User exchangeInitiator) {
        Notification acceptanceNotification = new Notification.NotificationBuilder().setUserNotified(exchangeInitiator).setMessage(userEmail + " " + exchangeChainAcceptanceMessageForInitiator).setNotificationType(NotificationType.EXCHANGE_CHAIN_ACCEPTANCE).isSeen(false).setDateCreated(LocalDateTime.now()).build();
        notificationsDao.saveNotification(acceptanceNotification);
    }

    private void addRequestedBookToExchangeChain(String userEmail, ExchangeAcceptance exchangeChainAcceptance, BookExchangeChain bookExchangeChain) throws BookExchangeInternalException {
        User userRequestingBook = userDao.findUserByEmail(userEmail).get();

        BookRequestedInChain bookRequested = new BookRequestedInChain(bookDao.getBookForId(exchangeChainAcceptance.getBookId()).orElseThrow(() -> new BookExchangeInternalException("No book founnd for id")));
        bookRequested.setRequestedBy(userRequestingBook);
        bookRequested.setChainRequestedIn(bookExchangeChain);

        bookExchangeChain.addRequestedBook(bookRequested);
    }

    private void notifyUsersForChainFailure(BookExchangeChain exchangeChainRequest, User userRejected) {
        List<User> usersToBeNotified = exchangeChainRequest.getUsersToBeNotified();
        List<Notification> notificationsToSend = usersToBeNotified.stream().filter(user -> !user.getEmail().equals(userRejected.getEmail()))
                .map(user -> new Notification.NotificationBuilder().setUserNotified(user).setMessage(userRejected.getFullName() +" "+ exchangeChainRejectMessageForParticipants + " (" + exchangeChainRequest.getId() + ")").setNotificationType(NotificationType.EXCHANGE_CHAIN_REJECTION).setDateCreated(LocalDateTime.now()).isSeen(false).build()).collect(Collectors.toList());
        notificationsToSend.add(new Notification.NotificationBuilder().setUserNotified(exchangeChainRequest.getExchangeInitiator()).setMessage(userRejected.getFullName()+" " + exchangeChainRejectMessageForInitiator + " (" + exchangeChainRequest.getId() + ")").setNotificationType(NotificationType.EXCHANGE_CHAIN_REJECTION).setDateCreated(LocalDateTime.now()).isSeen(false).build());

        notificationsToSend.stream().forEach(notification -> notificationsDao.saveNotification(notification));
    }



    private void updateExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests, BookExchangeChain exchangeChain) {
        exchangeChainRequests.stream().forEach(exchangeChainRequest -> exchangeChainRequest.setParentExchangeChain(exchangeChain));
    }

    private void addExchangeChainInvitationNotification(List<User> usersOnChain, BookExchangeChain exchangeChain) {
        User initiatorUser = usersOnChain.get(0);
        for (int i = 1; i < usersOnChain.size(); i++) {
            Notification exchangeChainRequestNotification = new Notification.NotificationBuilder().setMessage(initiatorUser.getFullName() + "(" + exchangeChain.getId() + ") " + newExchangeChainRequestMessage).setUserNotified(usersOnChain.get(i)).setDateCreated(LocalDateTime.now()).setNotificationType(NotificationType.EXCHANGE_CHAIN_INVITATION).build();
            notificationsDao.saveNotification(exchangeChainRequestNotification);
        }
    }

    private void persistExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests) {
        exchangeChainRequests.stream().forEach(exchangeChainRequest -> bookExchangeDao.addBookExchangeChainRequest(exchangeChainRequest));
    }

    private List<ExchangeChainRequest> constructExchangeChainRequests(List<User> usersOnChain) {
               List<ExchangeChainRequest> chainRequestsForUsers = new ArrayList<>();
        for (int i = 1; i < usersOnChain.size(); i++) {
            ExchangeChainRequest.ExchangeChainRequestBuilder chainRequestBuilder = new ExchangeChainRequest.ExchangeChainRequestBuilder().setRequestFor(usersOnChain.get(i)).setUserChoosingFrom(usersOnChain.get(i - 1));

            if(i == usersOnChain.size() -1) {
                chainRequestBuilder.setUserOfferingTo(usersOnChain.get(0));
            }else{
                chainRequestBuilder.setUserOfferingTo(usersOnChain.get(i + 1));
            }

            chainRequestsForUsers.add(chainRequestBuilder.build());
        }

        return chainRequestsForUsers;
    }

    public void setBookExchangeDao(BookExchangeDao bookExchangeDao) {
        this.bookExchangeDao = bookExchangeDao;
    }

    public void setNotificationsDao(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public BookExchangeChain getExchangeChainForId(int exchangeChainId) throws BookExchangeInternalException {
        BookExchangeChain exchangeChain = bookExchangeDao.getExchangeChainForId(exchangeChainId).orElseThrow(() -> new BookExchangeInternalException("No chain founnd for id"));

        return exchangeChain;
    }
}
