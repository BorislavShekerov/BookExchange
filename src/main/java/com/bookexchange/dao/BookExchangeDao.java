package com.bookexchange.dao;

import com.bookexchange.dto.BookExchange;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.ExchangeChainRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sheke on 11/13/2015.
 */
@Repository
public class BookExchangeDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addBookExchange(BookExchange bookExchange) {
        sessionFactory.getCurrentSession().save(bookExchange);
    }

    public void addBookExchangeChain(BookExchangeChain bookExchangeChain){
        sessionFactory.getCurrentSession().saveOrUpdate(bookExchangeChain);
    }

    public void addBookExchangeChainRequest(ExchangeChainRequest exchangeChainRequest){
        sessionFactory.getCurrentSession().saveOrUpdate(exchangeChainRequest);
    }

    public List<ExchangeChainRequest> getExchangeChainRequestsForUser(String userRequestingEmail,String chainInitiator){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(ExchangeChainRequest.class)
                .createAlias("userOffering", "offeringUser")
                .createAlias("userChoosing", "choosingUser")
                .createAlias("requestFor","parentBookExchangeChain")
                .createAlias("parentBookExchangeChain.exchangeInitiator","exchangeChainInitiator")
                .add(Restrictions.eq("exchangeChainInitiator.email", chainInitiator ))
                .add(Restrictions.or(Restrictions.eq("offeringUser.email", userRequestingEmail),Restrictions.eq("choosingUser.email", userRequestingEmail)));

        List<ExchangeChainRequest> exchangeChainRequests = criteria.list();

        return exchangeChainRequests;
    }
    public List<BookExchangeChain> getExchangeChainsInitiatedByUser(String userEmail){
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookExchangeChain.class)
                .createAlias("exchangeInitiator", "initiator")
                .add(Restrictions.eq("initiator.email", userEmail));

        List<BookExchangeChain> bookExchangeChain = criteria.list();

        return bookExchangeChain;
    }
    public List<BookExchange> getBookExchangesForUser(String userEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(BookExchange.class)
                .createAlias("bookPostedOnExchange", "bookPosted")
                .createAlias("bookPosted.postedBy", "bookPostedUserPosted")
                .createAlias("bookOfferedInExchange", "bookOffered")
                .createAlias("bookOffered.postedBy", "bookOfferedUserPosted")
                .add(Restrictions.or(Restrictions.eq("bookPostedUserPosted.email", userEmail),Restrictions.eq("bookOfferedUserPosted.email", userEmail)));
        List<BookExchange> booksOnExchange = criteria.list();

        return booksOnExchange;
    }
}
