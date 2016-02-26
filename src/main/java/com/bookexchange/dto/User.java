package com.bookexchange.dto;

import com.bookexchange.exception.BookExchangeInternalException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

/**
 * Created by sheke on 10/18/2015.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postedBy",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<Book> booksPostedOnExchange = new HashSet<>();
    @Transient
    Book bookToAddToExchange;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_CATEGORIES_INTERESTED", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    Set<BookCategory> categoriesInterestedIn = new HashSet<>();
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Transient
    private double overallRating;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "commentFor",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RatingComment> ratingsForUser = new ArrayList<>();
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;
    @Column(name = "AVATAR_URL")
    private String avatarUrl;
    @Column(name = "THEME_IMAGE_URL")
    private String themeImg;
    @Column(name = "ENABLED")
    private boolean enabled;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersForUserRole", cascade = {javax.persistence.CascadeType.PERSIST})
    @JsonIgnore
    private Set<UserRole> userRole = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EXCHANGE_CURRENT", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_ID"))
    private Set<DirectBookExchange> currentExchanges = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userNotified", cascade = {javax.persistence.CascadeType.PERSIST})
    private Set<Notification> userNotifications = new HashSet<>();
    @Column(name = "LOGIN_COUNT")
    private int  loginCount;
    @Column(name = "VIP_TOKENS")
    private int vipTokens;


    public void addBookExchange(DirectBookExchange directBookExchange) {
        this.currentExchanges.add(directBookExchange);
    }

    public void addCategoriesInterestedIn(List<BookCategory> categoriesInterestedIn){
        this.categoriesInterestedIn.addAll(categoriesInterestedIn);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public List<RatingComment> getRatingsForUser() {
        return ratingsForUser;
    }

    public void setRatingsForUser(List<RatingComment> ratingsForUser) {
        this.ratingsForUser = ratingsForUser;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public void addBookToExchange(Book book) {
        this.booksPostedOnExchange.add(book);
    }

    public void addUserRole(UserRole userRole) {
        this.userRole.add(userRole);
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

    public Set<DirectBookExchange> getCurrentExchanges() {
        return currentExchanges;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }
    public void setCurrentExchanges(Set<DirectBookExchange> currentExchanges) {
        this.currentExchanges = currentExchanges;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addNotification(Notification newNotification){
        userNotifications.add(newNotification);
    }
    public Set<Notification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(Set<Notification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public int getVipTokens() {
        return vipTokens;
    }

    public void setVipTokens(int vipTokens) {
        this.vipTokens = vipTokens;
    }

    public void addVipToken(){
        this.vipTokens++;
    }

    public Book getBookToAddToExchange() {
        return bookToAddToExchange;
    }

    public void setBookToAddToExchange(Book bookToAddToExchange) {
        this.bookToAddToExchange = bookToAddToExchange;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public void increaseLoginCount(){
        loginCount++;
    }

    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    public Optional<Book> getBookPostedByUser(String bookRequestedTitle) {
        return booksPostedOnExchange.stream().filter(book -> book.getTitle().equals(bookRequestedTitle)).findFirst();
    }

    public void removeBookPosted(Book bookToRemove) throws BookExchangeInternalException {
        booksPostedOnExchange.remove(bookToRemove);
    }

    public void removeCateoryInterestedIn(BookCategory categoriesToRemove) {
        categoriesInterestedIn.remove(categoriesToRemove);
    }

    public Optional<BookCategory> getCategoryInterestedIn(String categoryToRemove) {
        return categoriesInterestedIn.stream().filter(bookCategory -> bookCategory.getCategoryName().equals(categoryToRemove)).findFirst();
    }

    public static class UserBuilder {

        private User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setFirstName(String firstName) {
            user.setFirstName(firstName);
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

        public UserBuilder setBooksPostedOnExchange(Set<Book> booksPostedOnExchange) {
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
        public UserBuilder setLastName(String lastName){
            user.setLastName(lastName);
            return this;
        }

        public UserBuilder setUserRole(Set<UserRole> userRole) {
            user.setUserRole(userRole);
            return this;
        }

        public UserBuilder setCurrentExchanges(Set<DirectBookExchange> currentExchanges) {
            user.setCurrentExchanges(currentExchanges);
            return this;
        }

        public UserBuilder setUserNotifications(Set<Notification> userNotifications) {
            user.setUserNotifications(userNotifications);
            return this;
        }

        public UserBuilder setLoginCount(int loginCount) {
            user.setLoginCount(loginCount);
            return this;
        }

        public User buildUser() {
            return user;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email != null ? email.equals(user.email) : user.email == null;

    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
