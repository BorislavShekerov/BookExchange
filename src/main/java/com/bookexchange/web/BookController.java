package com.bookexchange.web;

import com.bookexchange.dto.Book;
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

    @RequestMapping(value = "/app/openAddBookModal", method = RequestMethod.GET)
    public String openAddBookModal(ModelMap model) {
        return "addBookModal";
    }

    @RequestMapping(value="/app/book/add", method=RequestMethod.POST)
    public @ResponseBody
    List<Book> addBookToExchange(@RequestBody Book bookToAdd, Model model) throws BookExchangeInternalException {
        Authentication authentication = getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        return bookService.addBookToExchange(currentUserEmail,bookToAdd);
    }

    @RequestMapping(value="/app/book/remove", method=RequestMethod.POST)
    public @ResponseBody
    List<Book> removeBookToExchange(@RequestBody Book bookToRemove, Model model) throws BookExchangeInternalException {
        Authentication authentication = getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        return  bookService.removeBook(currentUserEmail,bookToRemove.getTitle());
    }

}
