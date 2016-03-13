package com.bookexchange.dao;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by sheke on 10/19/2015.
 */
@Repository
public class BookDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Book> getAllBooksOnExchange() {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("active", true));
        List<Book> booksOnExchange = criteria.list();
        return booksOnExchange;
    }

    public Optional<Book> getBookPostedByUser(String bookTitle, String email) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.createAlias("postedBy", "userPosted")
                .add(Restrictions.eq("title", bookTitle))
                .add(Restrictions.eq("userPosted.email", email))
                .add(Restrictions.eq("active", true));

        Book bookForUsername = (Book) criteria.uniqueResult();
        return Optional.of(bookForUsername);
    }

    public void postBookOnExchange(Book bookToPost) {
        sessionFactory.getCurrentSession().save(bookToPost);
    }

    public List<Book> getAllBooksForLoggedInUser(String currentUserEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("postedBy", "userPosted")
                .add(Restrictions.eq("active", true))
                .add(Restrictions.ne("userPosted.email", currentUserEmail));

        return (List<Book>) criteria.list();
    }

    public List<Book> getAllBooksPostedByUser(String currentUserEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("postedBy", "userPosted")
                .add(Restrictions.eq("active", true))
                .add(Restrictions.eq("userPosted.email", currentUserEmail));

        return (List<Book>) criteria.list();
    }

    public Optional<Book> getBookForId(long bookId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class);
        criteria.createAlias("postedBy", "userPosted")
                .add(Restrictions.eq("active", true))
                .add(Restrictions.eq("id", bookId));


        Book bookForId = (Book) criteria.uniqueResult();
        return Optional.ofNullable(bookForId);
    }

    public void deleteBook(Book bookToDelete) {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.delete(bookToDelete);
    }

    public List<Book> getBooksForTitle(String title){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class)
                .add(Restrictions.like("title", title, MatchMode.ANYWHERE))
                .add(Restrictions.eq("active", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Book> bookForUsername = (List<Book>) criteria.list();
        return bookForUsername;
    }


    public List<Book> getBooksForCategories(List<String> categoriesFiltered) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Book.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("active", true))
                .createAlias("category","bookCategory");

        Disjunction unifier = Restrictions.or();

        categoriesFiltered.stream().map(bookCategory -> Restrictions.eq("bookCategory.categoryName",bookCategory)).forEach(restriction -> unifier.add(restriction));

        criteria.add(unifier);

        List<Book> bookForUsername = (List<Book>) criteria.list();
        return bookForUsername;
    }
}
