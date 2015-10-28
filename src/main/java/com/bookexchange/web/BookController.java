package com.bookexchange.web;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dto.Book;
import com.bookexchange.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sheke on 10/27/2015.
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getAllBooks", method = RequestMethod.GET)
    public @ResponseBody List<Book> getAllBooks(ModelMap model) {
        return bookService.getAllBooksOnExchange();
    }
}
