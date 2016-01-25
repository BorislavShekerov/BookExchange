package com.bookexchange.web;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.CategoryService;
import com.bookexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * Created by sheke on 10/28/2015.
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<BookCategory> getAllCategories(ModelMap model) {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value="/app/addPrefferedCategeories", method=RequestMethod.POST)
    public @ResponseBody String addPrefferedCategeories(@RequestBody List<BookCategory> categoriesInterestedIn, Model model) throws BookExchangeInternalException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        userService.addCategoriesInterestedIn(categoriesInterestedIn, userEmail);

        return "Success";
    }


}
