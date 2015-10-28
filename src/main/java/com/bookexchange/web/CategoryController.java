package com.bookexchange.web;

import com.bookexchange.dto.BookCategory;
import com.bookexchange.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * Created by sheke on 10/28/2015.
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<BookCategory> getAllCategories(ModelMap model) {
        return categoryService.getAllCategories();
    }
}
