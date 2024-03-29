package com.bookexchange.dao;

import com.bookexchange.dto.BookRequestedInChain;
import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.ExchangeChainRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by sheke on 11/13/2015.
 */
@Repository
public class BookExchangeDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void saveBookExchange(DirectBookExchange directBookExchange) {
        sessionFactory.getCurrentSession().saveOrUpdate(directBookExchange);
    }

    public void saveBookExchangeChain(BookExchangeChain bookExchangeChain){
        sessionFactory.getCurrentSession().saveOrUpdate(bookExchangeChain);
    }

    public void addBookExchangeChainRequest(ExchangeChainRequest exchangeChainRequest){
        sessionFactory.getCurrentSession().saveOrUpdate(exchangeChainRequest);
    }

    public List<BookExchangeChain> getExchangeChainsInitiatedByUser(String userEmail){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookExchangeChain.class)
                .createAlias("exchangeInitiator", "initiator")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("initiator.email", userEmail));

        List<BookExchangeChain> bookExchangeChain = criteria.list();

        return bookExchangeChain;
    }

    public List<ExchangeChainRequest> getExchangeChainsUserInvitedIn(String userEmail){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(ExchangeChainRequest.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("requestFor", "userRequestFor")
                .add(Restrictions.eq("userRequestFor.email", userEmail ));

        return (List<ExchangeChainRequest>) criteria.list();
    }

    public List<DirectBookExchange> getDirectExchangesInitiatedByUser(String userEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(DirectBookExchange.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("exchangeInitiator", "initiator")
                .add(Restrictions.eq("initiator.email", userEmail));
        List<DirectBookExchange> booksOnExchange = criteria.list();

        return booksOnExchange;
    }

    public List<DirectBookExchange> getDirectExchangesOfferedToUser(String userEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(DirectBookExchange.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("bookRequested", "requestedBook")
                .createAlias("requestedBook.postedBy", "bookRequestedUserPosted")
                .add(Restrictions.eq("bookRequestedUserPosted.email", userEmail));
        List<DirectBookExchange> booksOnExchange = criteria.list();

        return booksOnExchange;
    }

    public Optional<BookExchangeChain> getExchangeChainForId(int newExchangeChainRequestId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookExchangeChain.class)
                .add(Restrictions.eq("id", newExchangeChainRequestId));

        return Optional.ofNullable((BookExchangeChain) criteria.uniqueResult());
    }

    public Optional<DirectBookExchange> getDirectBookExchange(int directBookExchangeId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(DirectBookExchange.class)
                .add(Restrictions.eq("id", directBookExchangeId));

        return Optional.ofNullable((DirectBookExchange) criteria.uniqueResult());
    }

    public List<DirectBookExchange> getSuccessfulDirectExchangesCompletedYesterday(){
        Session currentSession = sessionFactory.getCurrentSession();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime dayBeforeYesterday = LocalDateTime.now().minusDays(2);

        Criteria criteria = currentSession.createCriteria(DirectBookExchange.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.ge("dateCompleted", dayBeforeYesterday))
                .add(Restrictions.lt("dateCompleted", yesterday));

        return criteria.list();
    }

    public List<BookExchangeChain> getSuccessfulExchangeChainsCompletedYesterday() {
        Session currentSession = sessionFactory.getCurrentSession();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime dayBeforeYesterday = LocalDateTime.now().minusDays(2);

        Criteria criteria = currentSession.createCriteria(BookExchangeChain.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.ge("dateCompleted", dayBeforeYesterday))
                .add(Restrictions.lt("dateCompleted", yesterday));

        return criteria.list();
    }

    public boolean isBookRequestedInDirectExchange(String bookTitle,String userPostedEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(DirectBookExchange.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("bookRequested", "requestedBook")
                .createAlias("requestedBook.postedBy", "userPostedBook")
                .add(Restrictions.eq("userPostedBook.email", userPostedEmail))
                .add(Restrictions.eq("requestedBook.title", bookTitle));

        return criteria.list().size() > 0;

    }

    public boolean isBookRequestedInChain(String bookTitle,String userPostedEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookRequestedInChain.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("requestedBook", "bookRequested")
                .createAlias("bookRequested.postedBy", "userPostedBook")
                .add(Restrictions.eq("userPostedBook.email", userPostedEmail))
                .add(Restrictions.eq("bookRequested.title", bookTitle));

        return criteria.list().size() > 0;
    }
}
