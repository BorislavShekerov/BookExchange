package com.bookexchange.service;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sheke on 10/19/2015.
 */
@Component
@Transactional
public class BookService {
    @Autowired
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooksOnExchange() {
        return bookDao.getAllBooksOnExchange();
    }

    public void postBookOnExchange(Book bookToPost) {
        bookToPost.setDatePosted(LocalDateTime.now());

        bookDao.postBookOnExchange(bookToPost);
    }
}
