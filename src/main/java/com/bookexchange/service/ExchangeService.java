package com.bookexchange.service;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.BookExchangeDao;
import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookExchange;
import com.bookexchange.dto.ExchangeOrder;
import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.graph.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sheke on 11/17/2015.
 */
@Service("exchangeService")
@Transactional
public class ExchangeService {

    @Autowired
    BookExchangeDao bookExchangeDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    UserDao userDao;

    public void recordExchange(ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        Book bookUnderOffer = bookDao.getBookForUsername(exchangeOrder.getBookUnderOffer(), exchangeOrder.getBookUnderOfferOwner()).orElseThrow(() -> new BookExchangeInternalException("Book under offer not found"));
        Book bookOfferedInExchange = bookDao.getBookForUsername(exchangeOrder.getBookOfferedInExchange(), exchangeOrder.getBookOfferedInExchangeOwner()).orElseThrow(() -> new BookExchangeInternalException("Book offered in exhange not found"));

        BookExchange exchangeToRecord = buildBookExchange(bookUnderOffer, bookOfferedInExchange);

        bookExchangeDao.addBookExchange(exchangeToRecord);
        updateUserDataWithNewExchange(bookUnderOffer.getPostedBy(), bookOfferedInExchange.getPostedBy(), exchangeToRecord);
    }

    private void updateUserDataWithNewExchange(User bookUnderOfferUser, User exchangeInitiator, BookExchange exchangeToRecord) {
        bookUnderOfferUser.addBookExchange(exchangeToRecord);
        exchangeInitiator.addBookExchange(exchangeToRecord);

        userDao.updateUser(bookUnderOfferUser);
        userDao.updateUser(exchangeInitiator);
    }

    private BookExchange buildBookExchange(Book bookPostedOnExchange, Book bookOfferedInExchange) {
        BookExchange bookExchange = new BookExchange();
        bookExchange.setBookPostedOnExchange(bookPostedOnExchange);
        bookExchange.setBookOfferedInExchange(bookOfferedInExchange);

        return bookExchange;
    }

    public List<String> exploreOptions(ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        List<User> allUsers = userDao.getAllUsers();

        Graph<String> userGraph = new GraphConstructor(allUsers).constructGraph();
        Set<Set<Vertex<String>>> stronglyConnectedComponentsInGraph = userGraph.findStronglyConnectedComponents();
        Optional<Set<Vertex<String>>> relevantStronglyConnectedComponent = filterRelevantComponents(stronglyConnectedComponentsInGraph, exchangeOrder);

        if (!relevantStronglyConnectedComponent.isPresent()){
            return new ArrayList<>();
        }
        List<String> orderedRelevantComponent = orderPathsFromInitiatorToGoal(relevantStronglyConnectedComponent.get(),exchangeOrder);
      //  List<List<String>> orderedComponents = orderRelevantComponents(relevantComponents,exchangeOrder);
        return orderedRelevantComponent;
    }

    private List<String> orderPathsFromInitiatorToGoal(Set<Vertex<String>> relevantComponents,ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        List<String> orderedPath = new ArrayList<>();

            Graph graph = new Graph<String>();

            //Breadth First From Exchange Initiator
            Vertex<String> exchangeInitiatorVertex = relevantComponents.stream().filter(userVertex -> userVertex.getName().equals(exchangeOrder.getBookOfferedInExchangeOwner())).findFirst().get();
        try {
            graph.breadthFirstSearch(exchangeInitiatorVertex,new CleverVisitor<String>());

            Vertex<String> exchangeGoalVertex = relevantComponents.stream().filter(userVertex -> userVertex.getName().equals(exchangeOrder.getBookUnderOfferOwner())).findFirst().get();
            return exchangeGoalVertex.getPathsToVertex().stream().map( vertexOnPath -> vertexOnPath.getName()).collect(Collectors.toList());
        } catch (Exception e) {
           throw new BookExchangeInternalException("A problem executing BFS",e);
        }

    }

    Optional<Set<Vertex<String>>> filterRelevantComponents(Set<Set<Vertex<String>>> stronglyConnectedComponentsInGraph, ExchangeOrder exchangeOrder) {
        Optional<Set<Vertex<String>>> relevantStronglyConnectedComponent = stronglyConnectedComponentsInGraph.stream().filter(set -> {
            Set<String> usersInStronglyConnectedComponent = set.stream().map(vertex -> vertex.getName()).collect(Collectors.toSet());
            if (usersInStronglyConnectedComponent.contains(exchangeOrder.getBookOfferedInExchangeOwner()) &&
                    usersInStronglyConnectedComponent.contains(exchangeOrder.getBookUnderOfferOwner())) {
                return true;
            }
            return false;
        }).findFirst();

        return relevantStronglyConnectedComponent;
    }
}
