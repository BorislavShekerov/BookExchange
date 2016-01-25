package com.bookexchange.web;

import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

/*    @RequestMapping(value = "/registration/success/", method = RequestMethod.POST)
    public ModelAndView getIndex(ModelMap model) {

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("registrationSuccess",true);
        return modelAndView;

    }*/

    @RequestMapping(value = "/app/", method = RequestMethod.GET)
    public ModelAndView launchApp(ModelMap model, @ModelAttribute User user) throws BookExchangeInternalException {
        Authentication authentication = getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User userDetails = null;
        if(currentPrincipalName!=null){
            userDetails = userService.findUserByEmail(currentPrincipalName);
            userService.updateUserLoginCount(userDetails);
        }else{
            userDetails = new User();
        }
        userDetails.setPassword("");


        LOGGER.debug("USER :" + currentPrincipalName);
        ModelAndView modelAndView = new ModelAndView("app");
        modelAndView.addObject("userDetails",userDetails);
        return modelAndView;

    }

    @RequestMapping(value = "/app/udetails/", method = RequestMethod.GET)
    public @ResponseBody User getUserData(ModelMap model) throws BookExchangeInternalException {
        Authentication authentication = getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        User userDetails = userService.findUserByEmail(currentPrincipalEmail);

        return userDetails;
    }

    @RequestMapping(value = "/app/firstTimeLogin", method = RequestMethod.GET)
    public String userFirstTimeLogin(ModelMap model) {
        return "firstTimeLoginModal";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView userLogin(@RequestParam(value = "error", required = false) String error,
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
    public String getAccountSettingsPage(ModelMap model) {
        return "account";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getSignUpPage(ModelMap model) {

        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("userData",new User());
        return modelAndView;
    }

}