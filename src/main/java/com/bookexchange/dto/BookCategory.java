package com.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Book_Category")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class BookCategory {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<Book> booksForCategory;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Book> getBooksForCategory() {
        return booksForCategory;
    }

    public void setBooksForCategory(List<Book> booksForCategory) {
        this.booksForCategory = booksForCategory;
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
}
