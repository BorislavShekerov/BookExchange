package com.bookexchange.dto;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by sheke on 10/18/2015.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postedBy")
    List<Book> booksPostedOnExchange;
    @Column(name = "avatarUrl")
    private String avatarUrl;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Book> getBooksPostedOnExchange() {
        return booksPostedOnExchange;
    }

    public void setBooksPostedOnExchange(List<Book> booksPostedOnExchange) {
        this.booksPostedOnExchange = booksPostedOnExchange;
    }
}
