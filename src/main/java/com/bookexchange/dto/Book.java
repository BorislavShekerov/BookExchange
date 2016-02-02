package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sheke on 10/18/2015.
 */
@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "AUTHOR")
    private String author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private BookCategory category;
    @Column(name = "IMG_URL")
    private String imgUrl;
    @Column(name = "DATE_POSTED")
    @JsonIgnore
    private LocalDateTime datePosted ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User postedBy = new User();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public String getOwnerName(){
        return postedBy.getFirstName()+" "+postedBy.getLastName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public static class BookBuilder{
        private Book book;

        public BookBuilder(){
            book = new Book();
        }

        public BookBuilder setTitle(String title) {
            book.setTitle(title); return this;
        }

        public BookBuilder setAuthor(String author) {
            book.setAuthor(author); return this;
        }

        public BookBuilder setImgUrl(String imgUrl) {
            book.setImgUrl(imgUrl); return this;
        }

        public BookBuilder setCategory(BookCategory category) {
            book.setCategory(category); return this;
        }

        public BookBuilder setDatePosted(LocalDateTime datePosted) {
            book.setDatePosted(datePosted); return this;
        }

        public BookBuilder setPostedBy(User postedBy) {
            book.setPostedBy(postedBy); return this;
        }

        public Book buildBook(){
            return book;
        }
    }
}
