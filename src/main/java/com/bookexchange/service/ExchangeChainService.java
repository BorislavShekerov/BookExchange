package com.bookexchange.service;

import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.ExchangeChainRequest;
import com.bookexchange.dto.Notification;
import com.bookexchange.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private NotificationsDao notificationsDao;
    @Value("${notification.new_exchange_chain_request}")
    public String newExchangeChainRequestMessage;

    public void registerExchangeChain(List<User> usersOnChain){
        List<ExchangeChainRequest> exchangeChainRequests = constructExchangeChainRequests(usersOnChain);
        BookExchangeChain exchangeChain = new BookExchangeChain.BookExchangeChainBuilder().setExchangeInitiator(usersOnChain.get(0)).setExchangeChainRequests(exchangeChainRequests).buildBookExchangeChain();

        updateExchangeChainRequests(exchangeChainRequests,exchangeChain);
        bookExchangeDao.addBookExchangeChain(exchangeChain);

        addExchangeChainInvitationNotification(usersOnChain);
    }

    public List<User> getChainDetailsForUser(String requestingUserEmail, String exchangeInitiatorEmail) {
        List<ExchangeChainRequest> exchangeRequestsWithUser = bookExchangeDao.getExchangeChainRequestsForUser(requestingUserEmail, exchangeInitiatorEmail);
        List<User> usersToExchangeWith = new ArrayList<>();

        if(exchangeRequestsWithUser.size() == 2){
            usersToExchangeWith.add(exchangeRequestsWithUser.stream().filter(exchangeChainRequest -> !exchangeChainRequest.getUserOffering().getEmail().equals(requestingUserEmail))
                    .map(exchangeChainRequest1 -> exchangeChainRequest1.getUserOffering())
                    .findFirst().get());
            usersToExchangeWith.add(exchangeRequestsWithUser.stream().filter(exchangeChainRequest -> !exchangeChainRequest.getUserChoosing().getEmail().equals(requestingUserEmail))
                    .map(exchangeChainRequest1 -> exchangeChainRequest1.getUserChoosing())
                    .findFirst().get());
        }else{
            usersToExchangeWith.add(exchangeRequestsWithUser.get(0).getUserOffering());
            usersToExchangeWith.add(userDao.findUserByEmail(exchangeInitiatorEmail).get());
        }

        return usersToExchangeWith;
    }

    private void updateExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests, BookExchangeChain exchangeChain) {
        exchangeChainRequests.stream().forEach(exchangeChainRequest -> exchangeChainRequest.setRequestFor(exchangeChain));
    }

    private void addExchangeChainInvitationNotification(List<User> usersOnChain) {
        User initiatorUser = usersOnChain.get(0);
        for(int i = 1;i < usersOnChain.size();i++){
            Notification exchangeChainRequestNotification = new Notification.NotificationBuilder().setMessage(initiatorUser.getFullName() + "(" + initiatorUser.getEmail() + ") "  + newExchangeChainRequestMessage).setUserNotified(usersOnChain.get(i)).setDateCreated(LocalDateTime.now()).build();
            notificationsDao.saveNotification(exchangeChainRequestNotification);
        }
    }

    private void persistExchangeChainRequests(List<ExchangeChainRequest> exchangeChainRequests) {
        exchangeChainRequests.stream().forEach(exchangeChainRequest -> bookExchangeDao.addBookExchangeChainRequest(exchangeChainRequest));
    }

    private List<ExchangeChainRequest> constructExchangeChainRequests(List<User> usersOnChain) {
        ExchangeChainRequest requestForInitiator = new ExchangeChainRequest(usersOnChain.get(0));

        List<ExchangeChainRequest> chainRequestsForUsers = new ArrayList<>(Arrays.asList(requestForInitiator));
        for(int i = 1;i < usersOnChain.size(); i++){
            User currentUser = usersOnChain.get(i);
            chainRequestsForUsers.get(i-1).setUserChoosing(currentUser);

            if(i < usersOnChain.size() - 1){
                ExchangeChainRequest requestForCurrentUser = new ExchangeChainRequest(currentUser);
                chainRequestsForUsers.add(requestForCurrentUser);
            }
        }

        return chainRequestsForUsers;
    }

    public void setBookExchangeDao(BookExchangeDao bookExchangeDao) {
        this.bookExchangeDao = bookExchangeDao;
    }

    public void setNotificationsDao(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }
}
