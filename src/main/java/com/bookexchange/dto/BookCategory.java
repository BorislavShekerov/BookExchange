package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "BOOK_CATEGORIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class BookCategory {
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categoriesInterestedIn")
    Set<User> usersInterestedInCategory = new HashSet<>();
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<Book> booksForCategory = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void addUserInterestedInCategory(User userInterestedInCategory){
        usersInterestedInCategory.add(userInterestedInCategory);
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // @JsonProperty
    public List<Book> getBooksForCategory() {
        return booksForCategory;
    }

    // @JsonIgnore
    public void setBooksForCategory(List<Book> booksForCategory) {
        this.booksForCategory = booksForCategory;
    }

    public Set<User> getUsersInterestedInCategory() {
        return usersInterestedInCategory;
    }

    public void setUsersInterestedInCategory(Set<User> usersInterestedInCategory) {
        this.usersInterestedInCategory = usersInterestedInCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCategory)) return false;

        BookCategory that = (BookCategory) o;

        if (getCategoryName() != null ? !getCategoryName().equals(that.getCategoryName()) : that.getCategoryName() != null)
            return false;
        return !(getBooksForCategory() != null ? !getBooksForCategory().equals(that.getBooksForCategory()) : that.getBooksForCategory() != null);

    }

    @Override
    public int hashCode() {
        int result = getCategoryName() != null ? getCategoryName().hashCode() : 0;
        result = 31 * result + (getBooksForCategory() != null ? getBooksForCategory().hashCode() : 0);
        return result;
    }

    public static class BookCategoryBuilder {
        private BookCategory bookCategory;

        public BookCategoryBuilder(){
            bookCategory = new BookCategory();
        }
        public BookCategoryBuilder setCategoryName(String categoryName) {
            this.bookCategory.setCategoryName(categoryName);
            return this;
        }

        public BookCategoryBuilder setBooksForCategory(List<Book> booksForCategory) {
            this.bookCategory.setBooksForCategory(booksForCategory);
            return this;
        }

        public BookCategoryBuilder setUsersInterestedInCategory(Set<User> usersInterestedInCategory) {
            this.bookCategory.setUsersInterestedInCategory(usersInterestedInCategory);
            return this;
        }

        public BookCategory buildBookCategory(){
            return bookCategory;
        }


    }
}
