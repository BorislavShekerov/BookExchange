package com.bookexchange.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(ModelMap model) {
        return "resources/index.html";

    }

    @RequestMapping(value = "/bookCard", method = RequestMethod.GET)
    public String getBookCard(ModelMap model) {
        return "resources/bookCard.html";

    }

    @RequestMapping(value = "/selectedCategoryBadge", method = RequestMethod.GET)
    public String getSelectedCategoryBadge(ModelMap model) {
        return "resources/selectedCategoryBadge.html";

    }
}