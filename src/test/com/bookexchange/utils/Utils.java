package com.bookexchange.utils;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory.BookCategoryBuilder;
import com.bookexchange.dto.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sheke on 11/18/2015.
 */
public class Utils {

    public static User constructUser(String email, List<String> booksPostedOnExchange, List<String> categoriesInterestedIn) {
        User.UserBuilder userBuilder = new User.UserBuilder();

        User user = userBuilder.setEmail(email).setBooksPostedOnExchange(booksPostedOnExchange.stream()
                .map(bookPostedCategory -> {
                    return new Book.BookBuilder().setCategory(new BookCategoryBuilder().setCategoryName(bookPostedCategory).buildBookCategory()).buildBook();
                }).collect(Collectors.toSet()))
                .setCategoriesInterestedIn(categoriesInterestedIn.stream().map(categoryString -> {
                    return new BookCategoryBuilder().setCategoryName(categoryString).buildBookCategory();
                }).collect(Collectors.toSet())).buildUser();

        return user;
    }
}
