package com.bookexchange.service;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.Book;
import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sheke on 10/19/2015.
 */
@Service("bookService")
@Transactional
public class BookService {
    public static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooksOnExchange(String currentUserEmail) {
        if(currentUserEmail.equals(ANONYMOUS_USER)){
            return bookDao.getAllBooksOnExchange();
        }
        return bookDao.getAllBooksReqestedByUser(currentUserEmail);
    }


    public void postBookOnExchange(Book bookToPost) {
        bookToPost.setDatePosted(LocalDateTime.now());

        bookDao.postBookOnExchange(bookToPost);
    }

    public List<Book> addBookToExchange(String currentUserEmail, Book bookToAdd) throws BookExchangeInternalException {
        User userDetails = userDao.findUserByEmail(currentUserEmail).orElseThrow(()-> new BookExchangeInternalException("UNKNOWN USERNAME"));
        userDetails.addBookToExchange(bookToAdd);

        bookToAdd.setPostedBy(userDetails);
        bookToAdd.setDatePosted(LocalDateTime.now());

        userDao.saveUser(userDetails);
        return bookDao.getAllBooksPostedByUser(currentUserEmail);
    }

    public List<Book> removeBook(String currentUserEmail, String title) throws BookExchangeInternalException {
        Book bookToRemove = bookDao.getBookPostedByUser(title, currentUserEmail).orElseThrow(()-> new BookExchangeInternalException("book not found"));
        User userPosted = bookToRemove.getPostedBy();
        userPosted.removeBookPosted(bookToRemove);

        bookDao.deleteBook(bookToRemove);
        userDao.saveUser(userPosted);
        return bookDao.getAllBooksPostedByUser(currentUserEmail);
    }

}
