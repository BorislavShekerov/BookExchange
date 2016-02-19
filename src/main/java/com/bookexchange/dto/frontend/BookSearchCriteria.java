package com.bookexchange.dto.frontend;

import com.bookexchange.dto.BookCategory;

import java.util.List;

/**
 * Created by sheke on 2/18/2016.
 */
public class BookSearchCriteria {
    private List<String> categoriesFiltered;
    private String bookTitle;

    public List<String> getCategoriesFiltered() {
        return categoriesFiltered;
    }

    public void setCategoriesFiltered(List<String> categoriesFiltered) {
        this.categoriesFiltered = categoriesFiltered;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
