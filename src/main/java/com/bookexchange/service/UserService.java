package com.bookexchange.service;

import com.bookexchange.dao.CategoryDao;
import com.bookexchange.dao.NotificationsDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.Notification;
import com.bookexchange.dto.User;
import com.bookexchange.dto.UserRole;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by sheke on 11/9/2015.
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao bookCategoryDao;

    @Autowired
    private NotificationsDao notificationsDao;

    public User findUserByEmail(String username) throws BookExchangeInternalException {
        return userDao.findUserByEmail(username).orElseThrow(()->new BookExchangeInternalException("UNKNOWN USERNAME"));
    }

    public boolean userAlreadyExists(User userData) {
        return userDao.findUserByEmail(userData.getEmail()) != null;
    }

    public void registerUser(User userData) {
        userData.setEnabled(true);
        userData.setLoginCount(0);
        userDao.addUser(userData);

        addUserRole(userData);
        addNewUserNotificationToUserData(userData);
    }

    private void addUserRole(User userData) {
        UserRole userRole = new UserRole();
        userRole.setRole("ROLE_USER");
        userRole.setUsersForUserRole(userData);

        userDao.addUserRole(userRole);
    }

    private void addNewUserNotificationToUserData(User userData) {
        Notification newUserNotification = new Notification();
        newUserNotification.setDateCreated(LocalDateTime.now());
        newUserNotification.setMessage("Welcome to WeSwap," + userData.getFirstName() + "!Enjoy your journey!");
        newUserNotification.setUserNotified(userData);

        notificationsDao.addNotification(newUserNotification);
    }

    public void updateUserLoginCount(User userDetails) {
        userDetails.increaseLoginCount();

        userDao.updateUser(userDetails);
    }

    public void addCategoriesInterestedIn(List<BookCategory> categoriesInterestedIn,String userEmail) throws BookExchangeInternalException {
        Optional<User> userForEmail = userDao.findUserByEmail(userEmail);
        User userDetails = userForEmail.orElseThrow(() -> new BookExchangeInternalException("UNKNOWN USERNAME"));
     //   userDetails.addCategoriesInterestedIn(categoriesInterestedIn);

        userDao.updateUser(userDetails);
    }
}
