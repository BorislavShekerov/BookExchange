package com.bookexchange.web;

import com.bookexchange.dto.RatingComment;
import com.bookexchange.dto.frontend.NewRating;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Created by sheke on 2/20/2016.
 */
@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/app/ratings/", method = RequestMethod.GET)
    public @ResponseBody
    List<RatingComment> getAllRatingsForUser(ModelMap model) {
        Authentication authentication = getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        return ratingService.getAllRatingsForUser(currentUserEmail);
    }

    @RequestMapping(value = "/app/ratings/add", method = RequestMethod.POST)
    public void addRating(@RequestBody NewRating newRating, ModelMap model) throws BookExchangeInternalException {
        Authentication authentication = getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        ratingService.addRating(newRating,currentUserEmail);
    }
}
