package com.bookexchange.service;

import com.bookexchange.dao.RatingDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.RatingComment;
import com.bookexchange.dto.User;
import com.bookexchange.dto.frontend.NewRating;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by sheke on 2/19/2016.
 */
@Service("ratingService")
public class RatingService {

    @Autowired
    private RatingDao ratingDao;
    @Autowired
    private UserDao userDao;

    public void addRating(NewRating ratingToAdd,String userCommentedEmail) throws BookExchangeInternalException {
        RatingComment ratingComment = constructComment(ratingToAdd,userCommentedEmail);

        ratingDao.addRating(ratingComment);
    }

    private RatingComment constructComment(NewRating ratingToAdd, String userCommentedEmail) throws BookExchangeInternalException {
        User userCommented = userDao.findUserByEmail(userCommentedEmail).orElseThrow(() -> new BookExchangeInternalException("No user found for email"));
        User userCommentFor = userDao.findUserByEmail(ratingToAdd.getCommentFor()).orElseThrow(() -> new BookExchangeInternalException("No user found for email"));

        return new RatingComment.RatingCommentBuilder()
                .setCommentFrom(userCommented)
                .setCommentFor(userCommentFor)
                .setCommentMessage(ratingToAdd.getCommentMessage())
                .setRating(ratingToAdd.getRating())
                .build();
    }


    public List<RatingComment> getAllRatingsForUser(String userEmail){
        return ratingDao.getAllRatingsForUser(userEmail);
    }

    public double computeOverallRating(List<RatingComment> ratingsForUser){
        if(ratingsForUser.size() > 0){
            int ratingsSum = ratingsForUser.stream().mapToInt(RatingComment::getId).sum();
            int averateRating = ratingsSum / ratingsForUser.size();

            BigDecimal bd = new BigDecimal(averateRating).setScale(2, RoundingMode.HALF_EVEN);
            return bd.doubleValue();
        }

        return 0;
    }
}
