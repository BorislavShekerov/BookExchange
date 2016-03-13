package com.bookexchange.service;

import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.User;
import com.bookexchange.dto.frontend.ExchangeChainProposal;
import com.bookexchange.dto.frontend.ExchangeOrder;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.graph.Graph;
import com.bookexchange.graph.GraphConstructor;
import com.bookexchange.graph.Path;
import com.bookexchange.graph.Vertex;
import com.bookexchange.graph.alogirthms.YenTopKShortestPathsAlg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by sheke on 1/30/2016.
 */
@Service("exchangePathSuggestionService")
@Transactional
public class ExchangePathSuggestionService {
    @Autowired
    UserDao userDao;

    public Optional<ExchangeChainProposal> exploreOptions(String userTriggeredSearch, ExchangeOrder exchangeOrder) throws BookExchangeInternalException {
        List<User> allUsers = userDao.getAllUsers();

        Graph userGraph = new GraphConstructor(allUsers).constructGraph();
        Set<Set<Vertex>> stronglyConnectedComponentsInGraph = userGraph.findStronglyConnectedComponents();
        Optional<Set<Vertex>> relevantStronglyConnectedComponent = filterRelevantComponents(stronglyConnectedComponentsInGraph, exchangeOrder, userTriggeredSearch);

        if (!relevantStronglyConnectedComponent.isPresent()) {
            return Optional.empty();
        }

        Graph graph = constructClosedComponentGraph(relevantStronglyConnectedComponent.get());
        List<String> userEmailsOnShortestPath = findAlternativePath(graph, exchangeOrder.getBookUnderOfferOwner(), userTriggeredSearch).get();
        List<User> usersOnShortestPath = orderUsersOnPath(allUsers, userEmailsOnShortestPath, exchangeOrder.getBookUnderOfferOwner());

        return Optional.of(new ExchangeChainProposal(usersOnShortestPath, graph));
    }

    private List<User> orderUsersOnPath(List<User> allUsers, List<String> orderedRelevantComponent, String bookUnderOfferOwner) {
        List<User> orderedUsers = orderedRelevantComponent.stream().map(relevantUserEmail -> allUsers.stream().filter(user -> user.getEmail().equals(relevantUserEmail)).findFirst().get()).collect(toList());
        //orderedUsers.add(allUsers.stream().filter(user -> user.getEmail().equals(bookUnderOfferOwner)).findFirst().get());

        return orderedUsers;
    }

    private Optional<List<String>> findAlternativePath(Graph relevantComponentGraph, String bookUnderOfferOwner, String userTriggeredSearch) {
        List<String> orderedPath = new ArrayList<>();

        Vertex exchangeInitiatorVertex = relevantComponentGraph.getVertexList().stream().filter(userVertex -> userVertex.getName().equals(userTriggeredSearch)).findFirst().get();
        Vertex exchangeGoalVertex = relevantComponentGraph.getVertexList().stream().filter(userVertex -> userVertex.getName().equals(bookUnderOfferOwner)).findFirst().get();

        YenTopKShortestPathsAlg yenTopKShortestPathsAlg = new YenTopKShortestPathsAlg(relevantComponentGraph, exchangeInitiatorVertex, exchangeGoalVertex);

        int pathsExploredCount = relevantComponentGraph.getPathsExploredCount();

        int currentShortestPath = 0;
        while(yenTopKShortestPathsAlg.hasNext()){
            Path shortestPath = yenTopKShortestPathsAlg.next();

            if(currentShortestPath++ >= pathsExploredCount){
                return Optional.of(shortestPath.toStringList());
            }
        }

        return Optional.empty();
    }

    private Graph constructClosedComponentGraph(Set<Vertex> relevantComponent) {
        Graph closedComponentGraph = new Graph();

        relevantComponent.stream().forEach(vertex -> {
            closedComponentGraph.addVertex(vertex);
            vertex.getIncomingEdges().stream().forEach(incomingEdge -> {
                closedComponentGraph.addVertex(incomingEdge.getFrom());
                closedComponentGraph.addEdgeNoChecks(incomingEdge.getFrom(), vertex, 0);
            });
        });

        return closedComponentGraph;
    }

    Optional<Set<Vertex>> filterRelevantComponents(Set<Set<Vertex>> stronglyConnectedComponentsInGraph, ExchangeOrder exchangeOrder, String userTriggeredSearch) {
        Optional<Set<Vertex>> relevantStronglyConnectedComponent = stronglyConnectedComponentsInGraph.stream().filter(set -> {
            Set<String> usersInStronglyConnectedComponent = set.stream().map(vertex -> vertex.getName()).collect(Collectors.toSet());
            if (usersInStronglyConnectedComponent.contains(userTriggeredSearch) &&
                    usersInStronglyConnectedComponent.contains(exchangeOrder.getBookUnderOfferOwner())) {
                return true;
            }
            return false;
        }).findFirst();

        return relevantStronglyConnectedComponent;
    }

    public ExchangeChainProposal exploreOtherOptions(String exchangeInitiatorEmail, BookExchangeChain exchangeChain) {
        List<User> allUsers = userDao.getAllUsers();
        String userUnderOffer = exchangeChain.getLastBookInChainOwnerEmail();

        boolean chainBreakerInNewChain = true;
        List<String> userEmailsOnShortestPath = null;

        while(chainBreakerInNewChain){
            userEmailsOnShortestPath= findAlternativePath(exchangeChain.getClosedComponent(), userUnderOffer , exchangeInitiatorEmail).orElse(new ArrayList<>());
            if(!userEmailsOnShortestPath.contains(exchangeChain.getChainBreaker().getEmail())){
                chainBreakerInNewChain = false;
            }
        }
        List<User> usersOnShortestPath = orderUsersOnPath(allUsers, userEmailsOnShortestPath, userUnderOffer);

        return new ExchangeChainProposal(usersOnShortestPath, exchangeChain.getClosedComponent());
    }
}
