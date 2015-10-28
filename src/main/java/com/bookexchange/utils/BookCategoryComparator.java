package com.bookexchange.utils;

import com.bookexchange.dto.BookCategory;

import java.util.Comparator;

/**
 * Created by sheke on 10/28/2015.
 */
public class BookCategoryComparator implements Comparator<BookCategory> {
    @Override
    public int compare(BookCategory o1, BookCategory o2) {
        return o1.getCategoryName().compareTo(o2.getCategoryName());
    }
}
