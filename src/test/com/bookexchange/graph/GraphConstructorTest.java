package com.bookexchange.graph;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import com.bookexchange.utils.Utils;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 11/17/2015.
 */
public class GraphConstructorTest {

    public static final String USER_1 = "user1";
    public static final String CATEGORY_1 = "category1";
    private static final String USER_2 = "user2";
    private static final String CATEGORY_2 = "category2";
    private static final String USER_3 = "user3";
    private static final String CATEGORY_3 = "category3";
    GraphConstructor testObj;

    @Test
    public void constructGraph_2users_bidirectional(){
        User user1 = Utils.constructUser(USER_1,Arrays.asList(CATEGORY_1), Arrays.asList(CATEGORY_2));
        User user2 = Utils.constructUser(USER_2,Arrays.asList(CATEGORY_2), Arrays.asList(CATEGORY_1));

        testObj = new GraphConstructor(Arrays.asList(user1,user2));
        Graph<String> graph = testObj.constructGraph();

        List<Vertex<String>> verticies = graph.getVerticies();
        List<Edge<String>> edges = graph.getEdges();

        assertEquals("2 vertices in the graph", 2, verticies.size());
        assertEquals("1 vertex has name"+USER_1, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_1)));
        assertEquals("1 vertex has name"+USER_1, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_2)));

        assertEquals("2 edges in the graph", 2, edges.size());
        assertEquals("1 edges from "+USER_1+" to "+USER_2, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_1) && edge.getTo().getName().equals(USER_2)).count());
        assertEquals("1 edges from " + USER_2 + " to " + USER_1, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_2) && edge.getTo().getName().equals(USER_1)).count());

    }


    @Test
    public void constructGraph_moreThan2users_noCycle(){
        User user1 = Utils.constructUser(USER_1,Arrays.asList(CATEGORY_1), Arrays.asList(CATEGORY_3));
        User user2 = Utils.constructUser(USER_2,Arrays.asList(CATEGORY_2), Arrays.asList(CATEGORY_1));
        User user3 = Utils.constructUser(USER_3,new ArrayList<>(), Arrays.asList(CATEGORY_2));

        testObj = new GraphConstructor(Arrays.asList(user1,user2,user3));
        Graph<String> graph = testObj.constructGraph();

        List<Vertex<String>> verticies = graph.getVerticies();
        List<Edge<String>> edges = graph.getEdges();

        assertEquals("3 vertices in the graph", 3, verticies.size());
        assertEquals("1 vertex has name"+USER_1, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_1)));
        assertEquals("1 vertex has name"+USER_2, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_2)));
        assertEquals("1 vertex has name"+USER_3, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_3)));

        assertEquals("2 edges in the graph",2, edges.size());
        assertEquals("1 edges from "+USER_1+" to "+USER_2, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_1) && edge.getTo().getName().equals(USER_2)).count());
        assertEquals("1 edges from " + USER_2 + " to " + USER_3, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_2) && edge.getTo().getName().equals(USER_3)).count());

    }

    @Test
    public void constructGraph_moreThan2users_cycle(){
        User user1 = Utils.constructUser(USER_1,Arrays.asList(CATEGORY_1), Arrays.asList(CATEGORY_3));
        User user2 = Utils.constructUser(USER_2,Arrays.asList(CATEGORY_2), Arrays.asList(CATEGORY_1));
        User user3 = Utils.constructUser(USER_3,Arrays.asList(CATEGORY_3), Arrays.asList(CATEGORY_2));

        testObj = new GraphConstructor(Arrays.asList(user1,user2,user3));
        Graph<String> graph = testObj.constructGraph();

        List<Vertex<String>> verticies = graph.getVerticies();
        List<Edge<String>> edges = graph.getEdges();

        assertEquals("3 vertices in the graph", 3, verticies.size());
        assertEquals("1 vertex has name"+USER_1, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_1)));
        assertEquals("1 vertex has name"+USER_2, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_2)));
        assertEquals("1 vertex has name"+USER_3, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_3)));

        assertEquals("2 edges in the graph",3, edges.size());
        assertEquals("1 edges from "+USER_1+" to "+USER_2, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_1) && edge.getTo().getName().equals(USER_2)).count());
        assertEquals("1 edges from " + USER_2 + " to " + USER_3, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_2) && edge.getTo().getName().equals(USER_3)).count());
        assertEquals("1 edges from " + USER_3 + " to " + USER_1, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_3) && edge.getTo().getName().equals(USER_1)).count());

    }
}
