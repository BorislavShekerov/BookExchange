package bookexchange.dao;

import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.Notification;
import com.bookexchange.dto.NotificationType;
import com.bookexchange.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by sheke on 2/1/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class NotificationsDaoTest {
    public static final String EMAIL_1 = "email1";
    public static final String DUMMY_NOTIFICAITON_MESSAGE = "blabla";
    @Autowired
    protected NotificationsDao notificationsDao;

    @Autowired
    protected UserDao userDao;

    @Test
    public void getExchangeChainRequestNotificationsForUser(){
        User user1 = new User.UserBuilder().setEmail(EMAIL_1).buildUser();

        userDao.addUser(user1);
        Notification notification = new Notification.NotificationBuilder().setUserNotified(user1).setNotificationType(NotificationType.EXCHANGE_CHAIN_INVITATION).isSeen(false).setMessage(DUMMY_NOTIFICAITON_MESSAGE).build();

        notificationsDao.saveNotification(notification);

        List<Notification> exchangeChainInvitationNotifications = notificationsDao.getAllNewNotificaitonsForUser(EMAIL_1);

        assertEquals(exchangeChainInvitationNotifications.size(),1);
        assertEquals(exchangeChainInvitationNotifications.get(0).getUserNotified(),user1);
        assertEquals(exchangeChainInvitationNotifications.get(0).getMessage(),DUMMY_NOTIFICAITON_MESSAGE);
    }
}
