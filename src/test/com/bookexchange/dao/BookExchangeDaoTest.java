package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.BookExchange;
import com.bookexchange.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 11/13/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class BookExchangeDaoTest {

    public static final String DUMMY_EMAIL_1 = "FirstEmail";
    public static final String DUMMY_EMAIL_2 = "SecondEmail";
    public static final String DUMMY_BOOK_TITLE_1 = "Book Title 1";
    public static final String DUMMY_BOOK_TITLE_2 = "Book Title 2";
    public static final String DUMMY_CATEGORY_1 = "Category 1";
    public static final String DUMMY_CATEGORY_2 = "Category 2";

    @Autowired
    protected BookDao bookDao = null;
    @Autowired
    protected UserDao userDao = null;
    @Autowired
    protected CategoryDao categoryDao = null;
    @Autowired
    protected BookExchangeDao bookExchangeDao = null;

    @Test
    public void addBookExchange(){
        User user1 = new User();
        user1.setEmail(DUMMY_EMAIL_1);

        User user2 = new User();
        user2.setEmail(DUMMY_EMAIL_2);

        Book user1Book = new Book();
        user1Book.setTitle(DUMMY_BOOK_TITLE_1);
        user1Book.setPostedBy(user1);

        Book user2Book = new Book();
        user2Book.setTitle(DUMMY_BOOK_TITLE_2);
        user2Book.setPostedBy(user2);

        BookCategory category1 = new BookCategory();
        category1.setCategoryName(DUMMY_CATEGORY_1);

        BookCategory category2 = new BookCategory();
        category2.setCategoryName(DUMMY_CATEGORY_2);

        user1Book.setCategory(category1);
        user2Book.setCategory(category2);

        userDao.addUser(user1);
        userDao.addUser(user2);
        categoryDao.addCategory(category1);
        categoryDao.addCategory(category2);
        bookDao.postBookOnExchange(user1Book);
        bookDao.postBookOnExchange(user2Book);

        BookExchange bookExchange = new BookExchange();
        bookExchange.setBookPostedOnExchange(user1Book);
        bookExchange.setBookPostedOnExchange(user2Book);

       user1.addBookExchange(bookExchange);
        user2.addBookExchange(bookExchange);

        userDao.updateUser(user1);
        userDao.updateUser(user2);

        user1 = userDao.findUserByEmail(DUMMY_EMAIL_1);
        user2 = userDao.findUserByEmail(DUMMY_EMAIL_2);

        assertEquals("Exchange Added To User 1", bookExchange, user1.getCurrentExchanges().iterator().next());
        assertEquals("Exchange Added To User 2", bookExchange, user2.getCurrentExchanges().iterator().next());
    }

}
