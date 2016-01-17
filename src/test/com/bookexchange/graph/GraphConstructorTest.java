package com.bookexchange.graph;

import com.bookexchange.dto.User;
import com.bookexchange.utils.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 11/17/2015.
 */
public class GraphConstructorTest {

    public static final String USER_1_EMAIL = "user1";
    public static final String CATEGORY_1 = "category1";
    private static final String USER_2_EMAIL = "user2";
    private static final String CATEGORY_2 = "category2";
    private static final String USER_3_EMAIL = "user3";
    private static final String CATEGORY_3 = "category3";
    GraphConstructor testObj;

    @Test
    public void constructGraph_2users_bidirectional(){
        User user1 = Utils.constructUser(USER_1_EMAIL,Arrays.asList(CATEGORY_1), Arrays.asList(CATEGORY_2));
        User user2 = Utils.constructUser(USER_2_EMAIL,Arrays.asList(CATEGORY_2), Arrays.asList(CATEGORY_1));

        testObj = new GraphConstructor(Arrays.asList(user1,user2));
        Graph<String> graph = testObj.constructGraph();

        List<Vertex<String>> verticies = graph.getVerticies();
        List<Edge<String>> edges = graph.getEdges();

        assertEquals("2 vertices in the graph", 2, verticies.size());
        assertEquals("1 vertex has name"+ USER_1_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_1_EMAIL)));
        assertEquals("1 vertex has name"+ USER_1_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_2_EMAIL)));

        assertEquals("2 edges in the graph", 2, edges.size());
        assertEquals("1 edges from "+ USER_1_EMAIL +" to "+ USER_2_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_1_EMAIL) && edge.getTo().getName().equals(USER_2_EMAIL)).count());
        assertEquals("1 edges from " + USER_2_EMAIL + " to " + USER_1_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_2_EMAIL) && edge.getTo().getName().equals(USER_1_EMAIL)).count());

    }


    @Test
    public void constructGraph_moreThan2users_noCycle(){
        User user1 = Utils.constructUser(USER_1_EMAIL,Arrays.asList(CATEGORY_1), Arrays.asList(CATEGORY_3));
        User user2 = Utils.constructUser(USER_2_EMAIL,Arrays.asList(CATEGORY_2), Arrays.asList(CATEGORY_1));
        User user3 = Utils.constructUser(USER_3_EMAIL,new ArrayList<>(), Arrays.asList(CATEGORY_2));

        testObj = new GraphConstructor(Arrays.asList(user1,user2,user3));
        Graph<String> graph = testObj.constructGraph();

        List<Vertex<String>> verticies = graph.getVerticies();
        List<Edge<String>> edges = graph.getEdges();

        assertEquals("3 vertices in the graph", 3, verticies.size());
        assertEquals("1 vertex has name"+ USER_1_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_1_EMAIL)));
        assertEquals("1 vertex has name"+ USER_2_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_2_EMAIL)));
        assertEquals("1 vertex has name"+ USER_3_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_3_EMAIL)));

        assertEquals("2 edges in the graph",2, edges.size());
        assertEquals("1 edges from "+ USER_1_EMAIL +" to "+ USER_2_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_1_EMAIL) && edge.getTo().getName().equals(USER_2_EMAIL)).count());
        assertEquals("1 edges from " + USER_2_EMAIL + " to " + USER_3_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_2_EMAIL) && edge.getTo().getName().equals(USER_3_EMAIL)).count());

    }

    @Test
    public void constructGraph_moreThan2users_cycle(){
        User user1 = Utils.constructUser(USER_1_EMAIL,Arrays.asList(CATEGORY_1), Arrays.asList(CATEGORY_3));
        User user2 = Utils.constructUser(USER_2_EMAIL,Arrays.asList(CATEGORY_2), Arrays.asList(CATEGORY_1));
        User user3 = Utils.constructUser(USER_3_EMAIL,Arrays.asList(CATEGORY_3), Arrays.asList(CATEGORY_2));

        testObj = new GraphConstructor(Arrays.asList(user1,user2,user3));
        Graph<String> graph = testObj.constructGraph();

        List<Vertex<String>> verticies = graph.getVerticies();
        List<Edge<String>> edges = graph.getEdges();

        assertEquals("3 vertices in the graph", 3, verticies.size());
        assertEquals("1 vertex has name"+ USER_1_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_1_EMAIL)));
        assertEquals("1 vertex has name"+ USER_2_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_2_EMAIL)));
        assertEquals("1 vertex has name"+ USER_3_EMAIL, true, verticies.stream().anyMatch(vertex -> vertex.getName().equals(USER_3_EMAIL)));

        assertEquals("2 edges in the graph",3, edges.size());
        assertEquals("1 edges from "+ USER_1_EMAIL +" to "+ USER_2_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_1_EMAIL) && edge.getTo().getName().equals(USER_2_EMAIL)).count());
        assertEquals("1 edges from " + USER_2_EMAIL + " to " + USER_3_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_2_EMAIL) && edge.getTo().getName().equals(USER_3_EMAIL)).count());
        assertEquals("1 edges from " + USER_3_EMAIL + " to " + USER_1_EMAIL, 1, edges.stream().filter(edge -> edge.getFrom().getName().equals(USER_3_EMAIL) && edge.getTo().getName().equals(USER_1_EMAIL)).count());

    }
}
