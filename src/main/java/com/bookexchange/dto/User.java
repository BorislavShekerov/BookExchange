package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sheke on 10/18/2015.
 */
@Entity
@Table(name = "USERS")
public class User {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postedBy")
    List<Book> booksPostedOnExchange = new ArrayList<>();
    @Transient
    Book bookToAddToExchange;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_CATEGORIES_INTERESTED", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    Set<BookCategory> categoriesInterestedIn;
    @Id
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "AVATAR_URL")
    private String avatarUrl;
    @Column(name = "ENABLED")
    private boolean enabled;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersForUserRole", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserRole> userRole;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EXCHANGE_CURRENT", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_ID"))
    private Set<BookExchange> currentExchanges = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EXCHANGE_HISTORY", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_ID"))
    private Set<BookExchangeCompleted> exchangeHistory = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userNotified")
    private Set<Notification> userNotifications;

    public void addBookExchange(BookExchange bookExchange) {
        this.currentExchanges.add(bookExchange);
    }

    public void completeExchange(BookExchangeCompleted bookExchange) {
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

    public List<Book> getBooksPostedOnExchange() {
        return booksPostedOnExchange;
    }

    public void setBooksPostedOnExchange(List<Book> booksPostedOnExchange) {
        this.booksPostedOnExchange = booksPostedOnExchange;
    }

    public Set<BookCategory> getCategoriesInterestedIn() {
        return categoriesInterestedIn;
    }

    public void setCategoriesInterestedIn(Set<BookCategory> categoriesInterestedIn) {
        this.categoriesInterestedIn = categoriesInterestedIn;
    }

    public void addBookToExchange(Book book) {
        this.booksPostedOnExchange.add(book);
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

    public Set<BookExchangeCompleted> getExchangeHistory() {
        return exchangeHistory;
    }

    public void setExchangeHistory(Set<BookExchangeCompleted> exchangeHistory) {
        this.exchangeHistory = exchangeHistory;
    }

    public Set<Notification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(Set<Notification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public Book getBookToAddToExchange() {
        return bookToAddToExchange;
    }

    public void setBookToAddToExchange(Book bookToAddToExchange) {
        this.bookToAddToExchange = bookToAddToExchange;
    }

    public static class UserBuilder {

        private User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setUsername(String username) {
            user.setUsername(username);
            return this;
        }

        public UserBuilder setEnabled(boolean enabled) {
            user.setEnabled(enabled);
            return this;
        }

        public UserBuilder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder setAvatarUrl(String avatarUrl) {
            user.setAvatarUrl(avatarUrl);
            return this;
        }

        public UserBuilder setBooksPostedOnExchange(List<Book> booksPostedOnExchange) {
            user.setBooksPostedOnExchange(booksPostedOnExchange);
            return this;
        }

        public UserBuilder setCategoriesInterestedIn(Set<BookCategory> categoriesInterestedIn) {
            user.setCategoriesInterestedIn(categoriesInterestedIn);
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder setUserRole(Set<UserRole> userRole) {
            user.setUserRole(userRole);
            return this;
        }

        public UserBuilder setCurrentExchanges(Set<BookExchange> currentExchanges) {
            user.setCurrentExchanges(currentExchanges);
            return this;
        }

        public UserBuilder setExchangeHistory(Set<BookExchangeCompleted> exchangeHistory) {
            user.setExchangeHistory(exchangeHistory);
            return this;
        }

        public UserBuilder setUserNotifications(Set<Notification> userNotifications) {
            user.setUserNotifications(userNotifications);
            return this;
        }

        public User buildUser() {
            return user;
        }

    }
}
