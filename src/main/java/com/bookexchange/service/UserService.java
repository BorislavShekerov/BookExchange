package com.bookexchange.service;

import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sheke on 11/9/2015.
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserByUsername(String username){
        return userDao.findUserByEmail(username);
    }

    public boolean userAlreadyExists(User userData) {
        return userDao.findUserByEmail(userData.getEmail()) != null;
    }

    public void registerUser(User userData) {

        userDao.addUser(userData);
    }
}
