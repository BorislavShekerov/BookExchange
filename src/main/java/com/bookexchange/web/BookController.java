package com.bookexchange.web;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Created by sheke on 10/27/2015.
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getAllBooks", method = RequestMethod.GET)
      public @ResponseBody List<Book> getAllBooks(ModelMap model) {
        Authentication authentication = getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        List<Book> allBooks = bookService.getAllBooksOnExchange(currentUserEmail);

        return allBooks;
    }

    @RequestMapping(value="/app/addBook", method=RequestMethod.POST)
    public @ResponseBody String addBookToExchange(@RequestBody User userData, Model model) throws BookExchangeInternalException {
        Book bookToAdd = userData.getBookToAddToExchange();
        bookToAdd.getPostedBy().setFirstName(userData.getEmail());

        bookService.addBookToExchange(bookToAdd);

        return "Success";
    }

}
