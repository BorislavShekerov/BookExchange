package com.bookexchange.dao;

import com.bookexchange.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by sheke on 10/30/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class UserDaoTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    @Autowired
    protected UserDao userDao = null;

    @Test
    public void postBookOnExchange(){
        User user = new User();
        user.setFirstName(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);

        userDao.saveUser(user);
        User userForUsername = userDao.findUserByEmail(EMAIL).get();

        assertNotNull(userForUsername);
        assertEquals("Pasword should be the same as input",PASSWORD,userForUsername.getPassword());
    }
}
