package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by sheke on 10/18/2015.
 */
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "IMG_URL")
    private String imgUrl;
    @Column(name = "DATE_POSTED")
    private LocalDateTime datePosted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User postedBy;
    @ElementCollection
    @CollectionTable(name="Categories", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="CATEGORIES_INTERESTED_IN")
    List<String> categoriesInterestedIn;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
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

    public List<String> getCategoriesInterestedIn() {
        return categoriesInterestedIn;
    }

    public void setCategoriesInterestedIn(List<String> categoriesInterestedIn) {
        this.categoriesInterestedIn = categoriesInterestedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (!getTitle().equals(book.getTitle())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        if (!getCategory().equals(book.getCategory())) return false;
        if (!getImgUrl().equals(book.getImgUrl())) return false;
        return getDatePosted().equals(book.getDatePosted());

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getCategory().hashCode();
        result = 31 * result + getImgUrl().hashCode();
        result = 31 * result + getDatePosted().hashCode();
        return result;
    }
}
