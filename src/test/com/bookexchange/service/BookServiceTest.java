package com.bookexchange.service;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dto.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by sheke on 10/19/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalDateTime.class,BookService.class})
public class BookServiceTest {

    BookService testObj;
    BookDao bookDAO;

    @Before
    public void setUp(){
        testObj = new BookService();
        bookDAO = mock(BookDao.class);

        testObj.setBookDao(bookDAO);
    }

    @Test
    public void getAllBooksOnExchange(){
        testObj.getAllBooksOnExchange();

        verify(bookDAO).getAllBooksOnExchange();
    }

    @Test
    public void postBookOnExchange(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Book bookToPost = new Book();
        bookToPost.setDatePosted(localDateTime);

        mockStatic(LocalDateTime.class);
        PowerMockito.when(localDateTime.now()).thenReturn(localDateTime);

        testObj.postBookOnExchange(bookToPost);

        verifyStatic();
        LocalDateTime.now();

        verify(bookDAO).postBookOnExchange(bookToPost);
    }
}
