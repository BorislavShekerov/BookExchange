package com.bookexchange.service;

import com.bookexchange.dto.frontend.ExchangeOrder;
import com.bookexchange.graph.Vertex;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created by sheke on 11/18/2015.
 */
public class ExchangePathSuggestionServiceTest {

    public static final String EXCHANGE_INITATOR = "dummyUser1";
    public static final String USER_UNDER_OFFER = "dummyUser2";
    ExchangePathSuggestionService testObj = new ExchangePathSuggestionService();

    @Test
    public void filterRelevantComponents() {
        Set<Vertex<String>> component1 = new HashSet<Vertex<String>>(Arrays.asList(new Vertex<String>(EXCHANGE_INITATOR), new Vertex<String>(USER_UNDER_OFFER)));
        Set<Vertex<String>> component2 = new HashSet<Vertex<String>>(Arrays.asList(new Vertex<String>("ddd"), new Vertex<String>("zzz")));
        Set<Vertex<String>> component3 = new HashSet<Vertex<String>>(Arrays.asList(new Vertex<String>("ff"), new Vertex<String>("xx"), new Vertex<String>("zzzdf")));
        Set<Vertex<String>> component4 = new HashSet<Vertex<String>>(Arrays.asList(new Vertex<String>("ff"), new Vertex<String>("xx"), new Vertex<String>("zzzdf"), new Vertex<String>(EXCHANGE_INITATOR)));

        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setBookOfferedInExchangeOwner(EXCHANGE_INITATOR);
        exchangeOrder.setBookUnderOfferOwner(USER_UNDER_OFFER);

        Optional<Set<Vertex<String>>> filteredComponents = testObj.filterRelevantComponents(new HashSet<>(Arrays.asList(component1, component2, component3, component4)), exchangeOrder);

        assertEquals(2, filteredComponents.get().size());
    }

//    @Test
//    public void orderRelevantComponents() {
//        Vertex<String> vertex1 = new Vertex<>(EXCHANGE_INITATOR);
//        String vertex2Name = "vertex2";
//        Vertex<String> vertex2 = new Vertex<>(vertex2Name);
//        String vertex3Name = "vertex3";
//        Vertex<String> vertex3 = new Vertex<>(vertex3Name);
//        Vertex<String> vertex4 = new Vertex<>(USER_UNDER_OFFER);
//
//        vertex1.addOutgoingEdge(vertex2,0);
//        vertex2.addOutgoingEdge(vertex3,0);
//        vertex3.addOutgoingEdge(vertex1,0);
//        vertex3.addOutgoingEdge(vertex4,0);
//        vertex4.addOutgoingEdge(vertex1,0);
//
//        ExchangeOrder exchangeOrder = new ExchangeOrder();
//        exchangeOrder.setBookOfferedInExchangeOwner(EXCHANGE_INITATOR);
//        exchangeOrder.setBookUnderOfferOwner(USER_UNDER_OFFER);
//
//        Set<Vertex<String>> component1 = new HashSet<>(Arrays.asList(vertex1,vertex2,vertex3,vertex4));
//        Set<Set<Vertex<String>>> allComponents = new HashSet<>(Arrays.asList(component1));
//
//        List<List<String>> orderedComponents = testObj.orderRelevantComponents(allComponents, exchangeOrder);
//        List<String> orderedComponent = orderedComponents.get(0);
//
//        assertEquals(4, orderedComponent.size());
//        assertEquals(EXCHANGE_INITATOR, orderedComponent.get(0));
//        assertEquals(vertex2Name,orderedComponent.get(1));
//        assertEquals(vertex3Name,orderedComponent.get(2));
//        assertEquals(USER_UNDER_OFFER,orderedComponent.get(3));
//    }
}
