package com.bookexchange.service;

import com.bookexchange.dao.CategoryDao;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.utils.BookCategoryComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
@Transactional
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public Set<BookCategory> getAllCategories() {
        List<BookCategory> allCategories = categoryDao.getAllCategories();

        TreeSet<BookCategory> allCategoriesSet = new TreeSet<>(new BookCategoryComparator());
        allCategoriesSet.addAll(allCategories);

        return allCategoriesSet;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
}
