package bookexchange.dao;

import com.bookexchange.dao.RatingDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.RatingComment;
import com.bookexchange.dto.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by sheke on 2/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class RatingDaoTest {
    public static final String DUMMY_EMAIL_1 = "dummyEmail1";
    private static final String DUMMY_EMAIL_2 = "dummyEmail2";
    @Autowired
    protected RatingDao ratingDao = null;
    @Autowired
    protected UserDao userDao = null;

    @Test
    public void getAllRatingsForUser(){
        String commentMessage = "Comment message";
        int rating1 = 5;

        User userCommentFor = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).buildUser();
        User userCommented = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();

        userDao.saveUser(userCommented);
        userDao.saveUser(userCommentFor);


        RatingComment rating = new RatingComment();
        rating.setRating(rating1);
        rating.setCommentFor(userCommentFor);
        rating.setCommentFrom(userCommented);
        rating.setCommentMessage(commentMessage);


        ratingDao.addRating(rating);
        List<RatingComment> allRatingsForUser = ratingDao.getAllRatingsForUser(userCommentFor.getEmail());

        assertEquals(1,allRatingsForUser.size());
        assertEquals(commentMessage,allRatingsForUser.get(0).getCommentMessage());
        assertEquals(rating1,allRatingsForUser.get(0).getRating());
    }
}