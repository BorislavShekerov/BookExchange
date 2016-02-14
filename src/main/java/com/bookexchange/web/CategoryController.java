package com.bookexchange.web;

import com.bookexchange.dto.BookCategory;
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

    @RequestMapping(value = "/app/category/openAddPreferredCategoryModal", method = RequestMethod.GET)
    public String openAddCategoryModal(ModelMap model) {
        return "addPreferredCategoriesModal";
    }

    @RequestMapping(value="/app/category/add", method=RequestMethod.POST)
    public @ResponseBody
    Set<BookCategory> addPrefferedCategeories(@RequestBody List<BookCategory> categoriesInterestedIn, Model model) throws BookExchangeInternalException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return  userService.addCategoriesInterestedIn(categoriesInterestedIn, userEmail);
    }

    @RequestMapping(value="/app/category/remove", method=RequestMethod.POST)
    public @ResponseBody
    Set<BookCategory> removePrefferedCategeory(@RequestBody BookCategory categoriesToRemove, Model model) throws BookExchangeInternalException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return  userService.removeCategoryInterestedIn(categoriesToRemove.getCategoryName(), userEmail);
    }




}
