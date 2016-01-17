package com.bookexchange.functional;

import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.User;
import com.bookexchange.web.UserController;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 1/17/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class UserControllerTest {

    @Autowired
    UserController testObj;

    @Autowired
    UserDao userDao;

    private static final String EMAIL1 = "dummyEmail1";
    private static final String EMAIL2 = "dummyEmail2";


    @Test
    public void registerUser_userExists(){
        User user1 = new User.UserBuilder().setEmail(EMAIL1).buildUser();
        User user2 = new User.UserBuilder().setEmail(EMAIL1).buildUser();

        userDao.addUser(user1);

        String result = testObj.registerUser(user2, null);

        assertEquals("Output must be Success", "Success", result);
    }
}
