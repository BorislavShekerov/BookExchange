package com.bookexchange.dao;

import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.ExchangeChainRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                .createAlias("bookOfferedInExchange", "bookOffered")
                .createAlias("bookOffered.postedBy", "bookOfferedUserPosted")
                .add(Restrictions.eq("bookOfferedUserPosted.email", userEmail));
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

    public Optional<BookExchangeChain> getExchangeChainRequest(int newExchangeChainRequestId) {
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

}
