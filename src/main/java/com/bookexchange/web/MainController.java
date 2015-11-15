package com.bookexchange.web;

import com.bookexchange.dto.User;
import com.bookexchange.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Controller
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(ModelMap model) {

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", new User());
        return modelAndView;

    }

    @RequestMapping(value = "/app/", method = RequestMethod.GET)
    public ModelAndView goToApp(ModelMap model, @ModelAttribute User user) {
        Authentication authentication = getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User userDetails = userService.findUserByUsername(currentPrincipalName);
        userDetails.setPassword("");


        LOGGER.info("USER :" + currentPrincipalName);
        ModelAndView modelAndView = new ModelAndView("app");
        modelAndView.addObject("userDetails",userDetails);
        return modelAndView;

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }

        if (logout != null) {
            model.addObject("message", "Logged out from JournalDEV successfully.");
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String getExchangeHistory(ModelMap model) {
        return "history";
    }


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String getUserAccount(ModelMap model) {
        return "account";
    }

}