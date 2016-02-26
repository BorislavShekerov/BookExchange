package com.bookexchange.dto.frontend;

/**
 * Created by sheke on 2/19/2016.
 */
public class NewRating {
    private String commentMessage;
    private String commentFor;
    private int rating;

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public String getCommentFor() {
        return commentFor;
    }

    public void setCommentFor(String commentFor) {
        this.commentFor = commentFor;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
