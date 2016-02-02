package com.bookexchange.service;

import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.ExchangeOrder;
import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.graph.CleverVisitor;
import com.bookexchange.graph.Graph;
import com.bookexchange.graph.GraphConstructor;
import com.bookexchange.graph.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sheke on 1/30/2016.
 */
@Service("exchangePathSuggestionService")
@Transactional
public class ExchangePathSuggestionService {
    @Autowired
    UserDao userDao;

    public List<User> exploreOptions(ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        List<User> allUsers = userDao.getAllUsers();

        Graph<String> userGraph = new GraphConstructor(allUsers).constructGraph();
        Set<Set<Vertex<String>>> stronglyConnectedComponentsInGraph = userGraph.findStronglyConnectedComponents();
        Optional<Set<Vertex<String>>> relevantStronglyConnectedComponent = filterRelevantComponents(stronglyConnectedComponentsInGraph, exchangeOrder);

        if (!relevantStronglyConnectedComponent.isPresent()){
            return new ArrayList<>();
        }
        List<String> orderedRelevantComponent = orderPathsFromInitiatorToGoal(relevantStronglyConnectedComponent.get(),exchangeOrder);
        List<User> orderedUserDetails = orderUsersOnPath(allUsers,orderedRelevantComponent,exchangeOrder.getBookUnderOfferOwner());
        return orderedUserDetails;
    }

    private List<User> orderUsersOnPath(List<User> allUsers, List<String> orderedRelevantComponent, String bookUnderOfferOwner) {
        List<User> orderedUsers = orderedRelevantComponent.stream().map(relevantUserEmail -> allUsers.stream().filter(user -> user.getEmail().equals(relevantUserEmail)).findFirst().get()).collect(Collectors.toList());
        orderedUsers.add(allUsers.stream().filter(user -> user.getEmail().equals(bookUnderOfferOwner)).findFirst().get());

        return orderedUsers;
    }

    private List<String> orderPathsFromInitiatorToGoal(Set<Vertex<String>> relevantComponents,ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        List<String> orderedPath = new ArrayList<>();

        Graph graph = new Graph<String>();
        String bookUnderOfferOwner = exchangeOrder.getBookUnderOfferOwner();
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
