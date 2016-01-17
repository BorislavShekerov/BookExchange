package com.bookexchange.web;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.User;
import com.bookexchange.service.BookService;
import com.bookexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sheke on 1/10/2016.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/registerUser", method= RequestMethod.POST)
    public @ResponseBody
    String registerUser(@RequestBody User userData, Model model) {
        boolean userExists = userService.userAlreadyExists(userData);

        if (userExists){
            return "An user with the same email is already registered.";
        }else{
            userService.registerUser(userData);
        }
        return "Success";
    }
}
