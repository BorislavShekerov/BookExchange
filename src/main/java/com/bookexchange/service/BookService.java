package com.bookexchange.service;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.Book;
import com.bookexchange.dto.User;
import com.bookexchange.dto.frontend.BookSearchCriteria;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private BookExchangeDao bookExchangeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RatingService ratingService;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooksOnExchange(String currentUserEmail) {
        List<Book> allBooksOnExchnage = null;
        if (currentUserEmail.equals(ANONYMOUS_USER)) {
            allBooksOnExchnage = bookDao.getAllBooksOnExchange();
        }else{
            allBooksOnExchnage = bookDao.getAllBooksForLoggedInUser(currentUserEmail);

        }

        computeUserRatings(allBooksOnExchnage);
        return allBooksOnExchnage;
    }

    private void computeUserRatings(List<Book> allBooksOnExchnage) {
        allBooksOnExchnage.stream().map(book -> book.getPostedBy()).forEach(user -> user.setOverallRating(ratingService.computeOverallRating(user.getRatingsForUser())));
    }


    public void postBookOnExchange(Book bookToPost) {
        bookToPost.setDatePosted(LocalDateTime.now());

        bookDao.postBookOnExchange(bookToPost);
    }

    public List<Book> addBookToExchange(String currentUserEmail, Book bookToAdd) throws BookExchangeInternalException {
        User userDetails = userDao.findUserByEmail(currentUserEmail).orElseThrow(() -> new BookExchangeInternalException("UNKNOWN USERNAME"));
        userDetails.addBookToExchange(bookToAdd);

        bookToAdd.setPostedBy(userDetails);
        bookToAdd.setDatePosted(LocalDateTime.now());

        userDao.saveUser(userDetails);
        return bookDao.getAllBooksPostedByUser(currentUserEmail);
    }

    public List<Book> removeBook(String currentUserEmail, String title) throws BookExchangeInternalException {
        Book bookToRemove = bookDao.getBookPostedByUser(title, currentUserEmail).orElseThrow(() -> new BookExchangeInternalException("book not found"));
        User userPosted = bookToRemove.getPostedBy();
        userPosted.removeBookPosted(bookToRemove);

        if(!isBookRequestedByAnotherUser(bookToRemove)){
            bookDao.deleteBook(bookToRemove);
            userDao.saveUser(userPosted);
        }

        return bookDao.getAllBooksPostedByUser(currentUserEmail);
    }

    private boolean isBookRequestedByAnotherUser(Book bookToRemove) {
        return bookExchangeDao.isBookRequestedInDirectExchange(bookToRemove.getTitle(),bookToRemove.getPostedBy().getEmail()) || bookExchangeDao.isBookRequestedInChain(bookToRemove.getTitle(),bookToRemove.getPostedBy().getEmail());
    }

    public List<Book> getBooksForTitle(BookSearchCriteria bookSearchCriteria) {
        if(bookSearchCriteria.getBookTitle().equals("")){
            List<Book> booksForCategories = bookDao.getBooksForCategories(bookSearchCriteria.getCategoriesFiltered());
            computeUserRatings(booksForCategories);
            return bookDao.getBooksForCategories(bookSearchCriteria.getCategoriesFiltered());
        }
        List<Book> booksForSearchTitle =  bookDao.getBooksForTitle(bookSearchCriteria.getBookTitle());
        computeUserRatings(booksForSearchTitle);

        if(bookSearchCriteria.getCategoriesFiltered().size() > 0){
           return booksForSearchTitle.stream().filter(book -> bookSearchCriteria.getCategoriesFiltered().contains(book.getCategory().getCategoryName())).collect(Collectors.toList());
        }

        return booksForSearchTitle;
    }
}