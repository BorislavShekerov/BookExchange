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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sheke on 11/9/2015.
 */
@Service("userService")
public class UserService {
    private static final int INITIAL_VIP_TOKENS = 5;
    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao bookCategoryDao;

    @Value("${notification.new_user}")
    private String welcomeMessage;
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
        userData.setVipTokens(INITIAL_VIP_TOKENS);
        userDao.saveUser(userData);

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
        newUserNotification.setMessage(welcomeMessage);
        newUserNotification.setUserNotified(userData);

        notificationsDao.saveNotification(newUserNotification);
    }

    public void updateUserLoginCount(User userDetails) {
        userDetails.increaseLoginCount();

        userDao.saveUser(userDetails);
    }

    public List<User> fetchUsersForEmail(List<String> userEmails){
        return userEmails.stream().map(userEmail -> userDao.findUserByEmail(userEmail).get()).collect(Collectors.toList());
    }

    public Set<BookCategory> addCategoriesInterestedIn(List<BookCategory> categoriesInterestedIn, String userEmail) throws BookExchangeInternalException {
        Optional<User> userForEmail = userDao.findUserByEmail(userEmail);
        User userDetails = userForEmail.orElseThrow(() -> new BookExchangeInternalException("UNKNOWN USERNAME"));

        userDetails.addCategoriesInterestedIn(categoriesInterestedIn);
        userDao.saveUser(userDetails);
        return userDao.findUserByEmail(userEmail).get().getCategoriesInterestedIn();
    }

    public Set<BookCategory> removeCategoryInterestedIn(String categoryToRemoveName, String userEmail) throws BookExchangeInternalException {
        User userDetails = userDao.findUserByEmail(userEmail).orElseThrow(() -> new BookExchangeInternalException("UNKNOWN USERNAME"));
        BookCategory categoryToRemove = userDetails.getCategoryInterestedIn(categoryToRemoveName).orElseThrow(() -> new BookExchangeInternalException("User not interested in category"));
        userDetails.removeCateoryInterestedIn(categoryToRemove);

        bookCategoryDao.removeCategory(categoryToRemove);
        userDao.saveUser(userDetails);
        return userDao.findUserByEmail(userEmail).get().getCategoriesInterestedIn();
    }
}
