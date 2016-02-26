package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sheke on 2/19/2016.
 */
@Entity
@Table(name = "RATING_COMMENTS")
public class RatingComment {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @Column(name = "RATING")
    private int rating;
    @Column(name = "COMMENT")
    private String commentMessage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_COMMENTED", nullable = false)
    private User commentFrom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_COMMENT_FOR", nullable = false)
    private User commentFor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public User getCommentFrom() {
        return commentFrom;
    }

    public User getCommentFor() {
        return commentFor;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public void setCommentFrom(User commentFrom) {
        this.commentFrom = commentFrom;
    }

    public void setCommentFor(User commentFor) {
        this.commentFor = commentFor;
    }

    public static class RatingCommentBuilder {
        private RatingComment rating;

        public RatingCommentBuilder(){
            rating = new RatingComment();
        }

        public RatingCommentBuilder setRating(int rating) {
            this.rating.setRating(rating); return this;
        }

        public RatingCommentBuilder setCommentMessage(String commentMessage) {
            rating.setCommentMessage(commentMessage); return this;
        }

        public RatingCommentBuilder setCommentFrom(User commentFrom) {
            rating.setCommentFrom(commentFrom); return this;
        }

        public RatingCommentBuilder setCommentFor(User commentFor) {
            rating.setCommentFor(commentFor); return this;
        }

        public RatingComment build(){
            return rating;
        }

    }
}
