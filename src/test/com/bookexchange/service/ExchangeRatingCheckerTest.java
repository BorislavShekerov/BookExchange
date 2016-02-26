package bookexchange.service;

import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dto.*;
import com.bookexchange.service.ExchangeRatingChecker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by sheke on 2/22/2016.
 */
public class ExchangeRatingCheckerTest {
    ExchangeRatingChecker testObj;
    NotificationsDao notificationsDaoMock;
    BookExchangeDao bookExchangeDaoMock;

    @Before
    public void setUp(){
        testObj = new ExchangeRatingChecker();
        notificationsDaoMock = Mockito.mock(NotificationsDao.class);
        bookExchangeDaoMock = Mockito.mock(BookExchangeDao.class);

        testObj.setBookExchangeDao(bookExchangeDaoMock);
        testObj.setNotificationsDao(notificationsDaoMock);
    }

    @Test
    public void checkForSuccessfulExchanges(){
        User user1 = new User.UserBuilder().setEmail("email1").setFirstName("DUmmyFirstname1").setLastName("dummyLastname1").buildUser();
        User user2 = new User.UserBuilder().setEmail("email2").setFirstName("DUmmyFirstname2").setLastName("dummyLastname2").buildUser();
        User user3 = new User.UserBuilder().setEmail("email3").setFirstName("DUmmyFirstname3").setLastName("dummyLastname3").buildUser();
        User user4 = new User.UserBuilder().setEmail("email4").setFirstName("DUmmyFirstname4").setLastName("dummyLastname4").buildUser();
        User user5 = new User.UserBuilder().setEmail("email5").setFirstName("DUmmyFirstname5").setLastName("dummyLastname5").buildUser();

        //Direct book exchange between user1 and user2
        int directExchangeId = 1;
        DirectBookExchange directBookExchange = Mockito.mock(DirectBookExchange.class);

        //Exchange chain between user3-.user4->user5
        ExchangeChainRequest requestInChain1 = new ExchangeChainRequest.ExchangeChainRequestBuilder().setRequestFor(user3).setUserChoosingFrom(user5).build();
        ExchangeChainRequest requestInChain2 = new ExchangeChainRequest.ExchangeChainRequestBuilder().setRequestFor(user4).setUserChoosingFrom(user3).build();
        ExchangeChainRequest requestInChain3 = new ExchangeChainRequest.ExchangeChainRequestBuilder().setRequestFor(user5).setUserChoosingFrom(user4).build();

        int chainExchangeId = 2;
        BookExchangeChain exchangeChain = new BookExchangeChain.BookExchangeChainBuilder().setId(chainExchangeId).setExchangeChainRequests(Arrays.asList(requestInChain1, requestInChain2, requestInChain3)).buildBookExchangeChain();

        Mockito.when(directBookExchange.getUserUnderOffer()).thenReturn(user2);
        Mockito.when(directBookExchange.getExchangeInitiator()).thenReturn(user1);
        Mockito.when(directBookExchange.getId()).thenReturn(directExchangeId);

        Mockito.when(bookExchangeDaoMock.getSuccessfulDirectExchangesCompletedYesterday()).thenReturn(Arrays.asList(directBookExchange));
        Mockito.when(bookExchangeDaoMock.getSuccessfulExchangeChainsCompletedYesterday()).thenReturn(Arrays.asList(exchangeChain));
        Mockito.when(notificationsDaoMock.getExchangeChainNotificationForExchange(directExchangeId)).thenReturn(Optional.ofNullable(null));
        Mockito.when(notificationsDaoMock.getExchangeChainNotificationForExchange(chainExchangeId)).thenReturn(Optional.ofNullable(null));

        testObj.checkForSuccessfulExchanges();

        ExchangeRatingNotification notification1 = new ExchangeRatingNotification.ExchangeRatingNotificationBuilder().setMessage("You recently completed an exchange with " + user1.getFullName() + ", rate him and get some VIP TOKENS!")
                .setNotificationType(NotificationType.RATE_EXCHANGE_RATING)
                .setDateCreated(LocalDateTime.now())
                .setExchangeId(directExchangeId)
                .setUserNotified(user2)
                .setUserRated(user1)
                .build();

        Mockito.verify(notificationsDaoMock).saveNotification(notification1);
    }
}
