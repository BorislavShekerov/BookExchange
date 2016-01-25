package com.bookexchange.web;

import com.bookexchange.dto.User;
import com.bookexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sheke on 1/10/2016.
 */
@Controller
public class UserController {
    public static final String userAlreadyExist = "An user with the same email is already registered";
    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public
    String registerUser(@ModelAttribute User userData, Model model, HttpServletRequest request) {
        boolean userExists = userService.userAlreadyExists(userData);
       // redirectAttributes.addFlashAttribute("mapping1Form", mapping1FormObject);

        if (userExists) {
            return "Failure";
        } else {
            userService.registerUser(userData);
            authenticateUserAndSetSession(userData,request);
            return "redirect:app/";
        }
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String email = user.getEmail();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
