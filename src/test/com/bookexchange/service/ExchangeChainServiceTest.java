package bookexchange.service;

import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.ExchangeChainRequest;
import com.bookexchange.dto.Notification;
import com.bookexchange.dto.User;
import com.bookexchange.service.ExchangeChainService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;

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
    ExchangeChainService testObj = new ExchangeChainService();
    BookExchangeDao exchangeDao;
    NotificationsDao notificationsDao;

    @Before
    public void setUp(){
        exchangeDao = Mockito.mock(BookExchangeDao.class);
        notificationsDao = Mockito.mock(NotificationsDao.class);

        testObj.setBookExchangeDao(exchangeDao);
        testObj.setNotificationsDao(notificationsDao);
        testObj.newExchangeChainRequestMessage = DUMMY_NOTIFICATION_MESSAGE;
    }
    @Test
    public void registerExchangeChain(){
        User user1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).setFirstName(U_1_FIRSTNAME).setLastName(U_2_FIRSTNAME).buildUser();
        User user2 = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();
        User user3 = new User.UserBuilder().setEmail(DUMMY_EMAIL_3).buildUser();

        testObj.registerExchangeChain(Arrays.asList(user1,user2,user3));

        ExchangeChainRequest exchangeChainRequest1 = new ExchangeChainRequest();
        exchangeChainRequest1.setUserOffering(user1);
        exchangeChainRequest1.setUserChoosing(user2);

        ExchangeChainRequest exchangeChainRequest2 = new ExchangeChainRequest();
        exchangeChainRequest2.setUserOffering(user2);
        exchangeChainRequest2.setUserChoosing(user3);

        BookExchangeChain bookExchangeChain = new BookExchangeChain.BookExchangeChainBuilder().setExchangeInitiator(user1).setExchangeChainRequests(Arrays.asList(exchangeChainRequest1,exchangeChainRequest2)).buildBookExchangeChain();

        Mockito.verify(exchangeDao).addBookExchangeChain(bookExchangeChain);

        Notification notificationUser2 = new Notification.NotificationBuilder().setDateCreated(LocalDateTime.now()).setMessage(user1.getFullName()+ "(" +DUMMY_EMAIL_1 + ") "  +DUMMY_NOTIFICATION_MESSAGE).setUserNotified(user2).build();
        Notification notificationUser3 = new Notification.NotificationBuilder().setDateCreated(LocalDateTime.now()).setMessage(user1.getFullName()+ "(" +DUMMY_EMAIL_1 + ") "  +DUMMY_NOTIFICATION_MESSAGE).setUserNotified(user3).build();

        Mockito.verify(notificationsDao).saveNotification(notificationUser2);
        Mockito.verify(notificationsDao).saveNotification(notificationUser3);
    }
}
