package com.bookexchange.functional;

import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import com.bookexchange.dto.UserRole;
import com.bookexchange.web.UserController;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 1/17/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@Transactional
public class UserControllerTest {

    @Autowired
    UserController testObj;

    @Autowired
    UserDao userDao;

    private static final String EMAIL1 = "dummyEmail1";
    private static final String EMAIL2 = "dummyEmail2";
    private static final String user2FirstName = "dummyFirstName";


    @Test
    public void registerUser_userExists(){
        User user1 = new User.UserBuilder().setEmail(EMAIL1).buildUser();
        User user2 = new User.UserBuilder().setEmail(EMAIL1).buildUser();

        userDao.addUser(user1);

        String result = testObj.registerUser(user2, null,null);

        assertEquals("User already exists for that email","Failure", result);
    }

    @Test
    public void registerUser_userDoesNotExist_userRegistered(){
        User user1 = new User.UserBuilder().setEmail(EMAIL1).buildUser();

        BookCategory scienceFiction = new BookCategory();
        scienceFiction.setCategoryName("Science Fiction");

        User user2 = new User.UserBuilder().setEmail(EMAIL2).setFirstName(user2FirstName).setCategoriesInterestedIn(new HashSet<>(Arrays.asList(scienceFiction))).buildUser();

        userDao.addUser(user1);

        String result = testObj.registerUser(user2, null,null);

        user2 = userDao.findUserByEmail(EMAIL2).get();

        assertEquals("User should have the firstname given to him",user2FirstName, user2.getFirstName());
        assertEquals("New user notification must be set","Welcome to WeSwap," + user2.getFirstName() + "!Enjoy your journey!",user2.getUserNotifications().iterator().next().getMessage());
        assertEquals("User Role must be set to ROLE_USER", UserRole.ROLE_USER,user2.getUserRole().iterator().next().getRole());
        assertEquals("User muste be enabled",true,user2.isEnabled());
    }
}
