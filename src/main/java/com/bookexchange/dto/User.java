package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Created by sheke on 10/18/2015.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postedBy")
    @JsonIgnore
    Set<Book> booksPostedOnExchange;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_CATEGORIES_INTERESTED", joinColumns = {@JoinColumn(name = "USER_ID")},inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    Set<BookCategory> categoriesInterestedIn;
    @Column(name = "AVATAR_URL")
    private String avatarUrl;
    @Column(name = "ENABLED")
    private boolean enabled;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersForUserRole",cascade = CascadeType.ALL )
    @JsonIgnore
    private Set<UserRole> userRole;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EXCHANGE_CURRENT", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_ID"))
    private Set<BookExchange> currentExchanges = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EXCHANGE_HISTORY", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_ID"))
    private Set<BookExchange> exchangeHistory = new HashSet<>();

    public void addBookExchange(BookExchange bookExchange){
        this.currentExchanges.add(bookExchange);
    }

    public void completeExchange(BookExchange bookExchange){
        this.exchangeHistory.add(bookExchange);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Set<Book> getBooksPostedOnExchange() {
        return booksPostedOnExchange;
    }

    public void setBooksPostedOnExchange(Set<Book> booksPostedOnExchange) {
        this.booksPostedOnExchange = booksPostedOnExchange;
    }

    public Set<BookCategory> getCategoriesInterestedIn() {
        return categoriesInterestedIn;
    }

    public void setCategoriesInterestedIn(Set<BookCategory> categoriesInterestedIn) {
        this.categoriesInterestedIn = categoriesInterestedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public Set<BookExchange> getCurrentExchanges() {
        return currentExchanges;
    }

    public void setCurrentExchanges(Set<BookExchange> currentExchanges) {
        this.currentExchanges = currentExchanges;
    }

    public Set<BookExchange> getExchangeHistory() {
        return exchangeHistory;
    }

    public void setExchangeHistory(Set<BookExchange> exchangeHistory) {
        this.exchangeHistory = exchangeHistory;
    }
}
