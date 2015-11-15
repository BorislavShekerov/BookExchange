package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by sheke on 10/30/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class UserDaoTest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    @Autowired
    protected UserDao userDao = null;

    @Test
    public void postBookOnExchange(){
        User user = new User();
       user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        userDao.addUser(user);
        User userForUsername = userDao.findUserByUsername(USERNAME);

        assertNotNull(userForUsername);
        assertEquals("Pasword should be the same as input",PASSWORD,userForUsername.getPassword());
    }
}
