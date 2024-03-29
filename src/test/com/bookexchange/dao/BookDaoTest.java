package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by sheke on 10/19/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class BookDaoTest {

    public static final String DUMMY_BOOK_TITLE = "Book Title";
    public static final String DUMMY_EMAIL= "email";
    public static final String SCIENCE_CATEGORY = "Science";

    @Autowired
    protected BookDao bookDao = null;
    @Autowired
    protected UserDao userDao = null;
    @Autowired
    protected CategoryDao categoryDao = null;

    @Before
    public void init(){
        User user = new User();
        user.setEmail(DUMMY_EMAIL);

        Book b = new Book();
        b.setTitle(DUMMY_BOOK_TITLE);
        b.setPostedBy(user);

        BookCategory category = new BookCategory();
        category.setCategoryName(SCIENCE_CATEGORY);

        b.setCategory(category);

        userDao.saveUser(user);
        categoryDao.addCategory(category);
        bookDao.postBookOnExchange(b);


    }
    @Test
    public void postBookOnExchange(){
        List<Book> allBooksOnExchange = bookDao.getAllBooksOnExchange();

        assertEquals("Books on exchange should be 1 after the insertion", 1, allBooksOnExchange.size());
        assertEquals("Title of the book on the exchange should be"+DUMMY_BOOK_TITLE,DUMMY_BOOK_TITLE, allBooksOnExchange.get(0).getTitle());
        assertEquals("Category of the book on the exchange should be"+SCIENCE_CATEGORY,SCIENCE_CATEGORY, allBooksOnExchange.get(0).getCategory().getCategoryName());
    }

    @Test
    public void getBookForUsername(){
        Optional<Book> bookForUser = bookDao.getBookPostedByUser(DUMMY_BOOK_TITLE, DUMMY_EMAIL);

       assertNotNull(bookForUser.get());
    }
}
