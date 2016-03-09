package bookexchange.service;

import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.*;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.ExchangeChainService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 1/30/2016.
 */
public class ExchangeChainServiceTest {

    public static final String DUMMY_EMAIL_1 = "email1";
    public static final String DUMMY_EMAIL_2 = "email2";
    public static final String DUMMY_EMAIL_3 = "email3";
    public static final String U_1_FIRSTNAME = "u1firstname";
    public static final String U_2_FIRSTNAME = "u2firstname";
    public static final String DUMMY_NOTIFICATION_MESSAGE = "dummyMessage";
    public static final String DUMMY_EXCHANGE_CHAIN_REJECT_PARTICIPANTS_MESSAGE = "dummyMessageParticipants";
    public static final String DUMMY_EXCHANGE_CHAIN_REJECT_INITIATOR_MESSAGE = "dummyMessageInitiator";
    public static final String BOOK_REQUESTED = "B_REQUESTED";

    ExchangeChainService testObj = new ExchangeChainService();
    BookExchangeDao exchangeDao;
    NotificationsDao notificationsDao;
    UserDao userDao;

    @Before
    public void setUp() {
        exchangeDao = Mockito.mock(BookExchangeDao.class);
        notificationsDao = Mockito.mock(NotificationsDao.class);
        userDao = Mockito.mock(UserDao.class);

        testObj.setBookExchangeDao(exchangeDao);
        testObj.setNotificationsDao(notificationsDao);
        testObj.setUserDao(userDao);

        testObj.newExchangeChainRequestMessage = DUMMY_NOTIFICATION_MESSAGE;
        testObj.exchangeChainRejectMessageForParticipants = DUMMY_EXCHANGE_CHAIN_REJECT_PARTICIPANTS_MESSAGE;
        testObj.exchangeChainRejectMessageForInitiator = DUMMY_EXCHANGE_CHAIN_REJECT_INITIATOR_MESSAGE;
    }

    @Test
    public void registerExchangeChain() throws BookExchangeInternalException {
        User user1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).setFirstName(U_1_FIRSTNAME).setLastName(U_2_FIRSTNAME).buildUser();
        User user2 = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();
        User user3 = new User.UserBuilder().setEmail(DUMMY_EMAIL_3).buildUser();

        //testObj.registerExchangeChain(Arrays.asList(user1, user2, user3),BOOK_REQUESTED, exchangeChainOrder.getClosedComponent());

        ExchangeChainRequest exchangeChainRequest1 = new ExchangeChainRequest();
        exchangeChainRequest1.setUserOfferingTo(user1);
        exchangeChainRequest1.setUserChoosingFrom(user2);

        ExchangeChainRequest exchangeChainRequest2 = new ExchangeChainRequest();
        exchangeChainRequest2.setUserOfferingTo(user2);
        exchangeChainRequest2.setUserChoosingFrom(user3);

        BookExchangeChain bookExchangeChain = new BookExchangeChain.BookExchangeChainBuilder().setExchangeInitiator(user1).setExchangeChainRequests(Arrays.asList(exchangeChainRequest1, exchangeChainRequest2)).buildBookExchangeChain();

        Mockito.verify(exchangeDao).saveBookExchangeChain(bookExchangeChain);

        Notification notificationUser2 = new Notification.NotificationBuilder().setDateCreated(LocalDateTime.now()).setMessage(user1.getFullName() + "(" + DUMMY_EMAIL_1 + ") " + DUMMY_NOTIFICATION_MESSAGE).setUserNotified(user2).build();
        Notification notificationUser3 = new Notification.NotificationBuilder().setDateCreated(LocalDateTime.now()).setMessage(user1.getFullName() + "(" + DUMMY_EMAIL_1 + ") " + DUMMY_NOTIFICATION_MESSAGE).setUserNotified(user3).build();

        Mockito.verify(notificationsDao).saveNotification(notificationUser2);
        Mockito.verify(notificationsDao).saveNotification(notificationUser3);
    }

    @Test
    public void getChainDetailsForUser_userNotLastInChain() {
        String userToCheckFor = "userD";
        int exchangeChainToCheckForId = 1;
        User user1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).setFirstName(U_1_FIRSTNAME).setLastName(U_2_FIRSTNAME).buildUser();
        User user2 = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();

        BookExchangeChain bookExchangeChain = Mockito.mock(BookExchangeChain.class);
        ExchangeChainRequest exchangeChainRequestForUser = Mockito.mock(ExchangeChainRequest.class);

        Mockito.when(exchangeDao.getExchangeChainForId(exchangeChainToCheckForId)).thenReturn(Optional.of(bookExchangeChain));
        Mockito.when(bookExchangeChain.getExchangeChainRequestForUser(userToCheckFor)).thenReturn(Optional.of(exchangeChainRequestForUser));
        Mockito.when(exchangeChainRequestForUser.getUserChoosingFrom()).thenReturn(user1);
        Mockito.when(exchangeChainRequestForUser.getUserOfferingTo()).thenReturn(user2);

        try {
            List<User> result = testObj.getChainDetailsForUser(exchangeChainToCheckForId, userToCheckFor);

            assertEquals(2, result.size());
            assertEquals(user1, result.get(0));
            assertEquals(user2, result.get(1));
        } catch (BookExchangeInternalException e) {
            fail();
        }

    }

    @Ignore
    public void getChainDetailsForUser_userLastInChain() {
        String userToCheckFor = "userD";
        int exchangeChainToCheckForId = 1;
        User user1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).setFirstName(U_1_FIRSTNAME).setLastName(U_2_FIRSTNAME).buildUser();
        User user3 = new User.UserBuilder().setEmail(DUMMY_EMAIL_3).buildUser();

        BookExchangeChain bookExchangeChain = Mockito.mock(BookExchangeChain.class);

        ExchangeChainRequest exchangeChainRequestForUser = Mockito.mock(ExchangeChainRequest.class);

        Mockito.when(exchangeDao.getExchangeChainForId(exchangeChainToCheckForId)).thenReturn(Optional.of(bookExchangeChain));
        Mockito.when(bookExchangeChain.getExchangeChainRequestForUser(userToCheckFor)).thenReturn(Optional.of(exchangeChainRequestForUser));

//        Mockito.when(bookExchangeChain.getUserToChooseFrom(userToCheckFor)).thenReturn(Optional.of(user1));
//        Mockito.when(bookExchangeChain.getUserToOfferTo(userToCheckFor)).thenReturn(Optional.ofNullable(null));
//        Mockito.when(exchangeChainRequestForUser.getUserChoosingFrom()).thenReturn(user1);
//        Mockito.when(exchangeChainRequestForUser.getUserOfferingTo()).thenReturn(user2);
        Mockito.when(bookExchangeChain.getExchangeInitiator()).thenReturn(user3);

        try {
            List<User> result = testObj.getChainDetailsForUser(exchangeChainToCheckForId, userToCheckFor);

            assertEquals(2, result.size());
            assertEquals(user1, result.get(0));
            assertEquals(user3, result.get(1));
        } catch (BookExchangeInternalException e) {
            fail();
        }
    }

    @Ignore
    public void rejectExchangeChain(){
        String userRejectedEmail = "userD";
        String dummyUserRejectedFullname = "dummyFulname1";


        int exchangeChainToCheckForId = 1;

        BookExchangeChain bookExchangeChain = Mockito.mock(BookExchangeChain.class);
        User userRejected = Mockito.mock(User.class);
        User userOffering1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).buildUser();
        User userOffering2 = new User.UserBuilder().setEmail(userRejectedEmail).buildUser();
        User exchangeChainInitiator = Mockito.mock(User.class);

        ExchangeChainRequest request1 = new ExchangeChainRequest();
        request1.setUserOfferingTo(userOffering1);

        ExchangeChainRequest request2 = new ExchangeChainRequest();
        request2.setUserOfferingTo(userOffering2);

        Mockito.when(exchangeDao.getExchangeChainForId(exchangeChainToCheckForId)).thenReturn(Optional.of(bookExchangeChain));
        Mockito.when(bookExchangeChain.getId()).thenReturn(exchangeChainToCheckForId);
        Mockito.when(userDao.findUserByEmail(userRejectedEmail)).thenReturn(Optional.of(userRejected));
        Mockito.when(bookExchangeChain.getExchangeChainRequests()).thenReturn(Arrays.asList(request1,request2));
        Mockito.when(userRejected.getFullName()).thenReturn(dummyUserRejectedFullname);
        Mockito.when(userRejected.getEmail()).thenReturn(userRejectedEmail);


        try {
            testObj.rejectExchangeChain(userRejectedEmail,exchangeChainToCheckForId);
        } catch (BookExchangeInternalException e) {
            fail();
        }
        Notification notificaitonForUserOffering1 = new Notification.NotificationBuilder().setUserNotified(userOffering1).setMessage(dummyUserRejectedFullname+" "+DUMMY_EXCHANGE_CHAIN_REJECT_PARTICIPANTS_MESSAGE+" ("+exchangeChainToCheckForId+")").setUserNotified(userOffering1).isSeen(false).setNotificationType(NotificationType.EXCHANGE_CHAIN_REJECTION).build();
        Notification notificaitonForInitiator = new Notification.NotificationBuilder().setUserNotified(userOffering1).setMessage(dummyUserRejectedFullname+" "+DUMMY_EXCHANGE_CHAIN_REJECT_INITIATOR_MESSAGE+" ("+exchangeChainToCheckForId+")").setUserNotified(exchangeChainInitiator).isSeen(false).setNotificationType(NotificationType.EXCHANGE_CHAIN_REJECTION).build();

        Mockito.verify(notificationsDao).saveNotification(notificaitonForUserOffering1);
        Mockito.verify(notificationsDao).saveNotification(notificaitonForInitiator);

    }
}
