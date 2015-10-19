package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by sheke on 10/19/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class BookDaoTest {

    public static final String DUMMY_BOOK_TITLE = "Book Title";
    public static final String DUMMY_FIRSTNAME = "Firstname";

    @Autowired
    protected BookDao bookDao = null;
    @Autowired
    protected UserDao userDao = null;

    @Test
    public void postBookOnExchange(){
        User user = new User();
        user.setFirstname(DUMMY_FIRSTNAME);

        Book b = new Book();
        b.setTitle(DUMMY_BOOK_TITLE);
        b.setPostedBy(user);

        userDao.addUser(user);
        bookDao.postBookOnExchange(b);

        List<Book> allBooksOnExchange = bookDao.getAllBooksOnExchange();

        assertEquals("Books on exchange should be 1 after the insertion", 1, allBooksOnExchange.size());
        assertEquals("Title of the book on the exchange should be"+DUMMY_BOOK_TITLE,DUMMY_BOOK_TITLE, allBooksOnExchange.get(0).getTitle());
    }
}
