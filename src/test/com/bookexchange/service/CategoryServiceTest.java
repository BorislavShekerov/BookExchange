package com.bookexchange.service;

import com.bookexchange.dao.CategoryDao;
import com.bookexchange.dto.BookCategory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sheke on 10/28/2015.
 */
public class CategoryServiceTest {
    CategoryService testObj;
    CategoryDao categoryDao;

    @Before
    public void setUp(){
        testObj = new CategoryService();

        categoryDao = mock(CategoryDao.class);
        testObj.setCategoryDao(categoryDao);
    }

    @Test
    public void getAllCategories(){
        BookCategory category1 = new BookCategory();
        category1.setCategoryName("Adventure");
        BookCategory category2 = new BookCategory();
        category2.setCategoryName("Dictionaries");
        BookCategory category3 = new BookCategory();
        category3.setCategoryName("Science");

        List<BookCategory> categoriesList = Arrays.asList(category2,category3,category1);
        when(categoryDao.getAllCategories()).thenReturn(categoriesList);

        Set<BookCategory> allCategories = testObj.getAllCategories();
        Iterator<BookCategory> iterator = allCategories.iterator();

        assertEquals("Name of first category should be Adventure(sorted alphabetically)","Adventure", iterator.next().getCategoryName());
        assertEquals("Name of second category should be Dictionaries(sorted alphabetically)","Dictionaries", iterator.next().getCategoryName());
        assertEquals("Name of third category should be Science(sorted alphabetically)","Science", iterator.next().getCategoryName());
    }
}
