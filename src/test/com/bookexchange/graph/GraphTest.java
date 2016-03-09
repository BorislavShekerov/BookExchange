package com.bookexchange.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.util.Assert.*;

/**
 * Created by sheke on 11/18/2015.
 */
public class GraphTest {

    Graph testObj;

    /**
     * Testing the following scenario https://commons.wikimedia.org/wiki/File:Tarjan%27s_Algorithm_Animation.gif#/media/File:Tarjan%27s_Algorithm_Animation.gif
     */
    @Test
    public void findStronglyConnectedComponents() {

        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("3");
        Vertex vertex4 = new Vertex("4");
        Vertex vertex5 = new Vertex("5");
        Vertex vertex6 = new Vertex("6");
        Vertex vertex7 = new Vertex("7");
        Vertex vertex8 = new Vertex("8");
        List<Vertex> vertices = Arrays.asList(vertex1, vertex2, vertex3, vertex4, vertex5, vertex6, vertex7, vertex8);


        Edge edge1 = new Edge(vertex1, vertex2);
        Edge edge2 = new Edge(vertex2, vertex3);
        Edge edge3 = new Edge(vertex3, vertex1);
        Edge edge4 = new Edge(vertex4, vertex2);
        Edge edge5 = new Edge(vertex4, vertex3);
        Edge edge6 = new Edge(vertex5, vertex3);
        Edge edge7 = new Edge(vertex5, vertex7);
        Edge edge8 = new Edge(vertex6, vertex4);
        Edge edge9 = new Edge(vertex6, vertex5);
        Edge edge10 = new Edge(vertex7, vertex5);
        Edge edge11 = new Edge(vertex8, vertex6);
        Edge edge12 = new Edge(vertex8, vertex7);
        Edge edge13 = new Edge(vertex8, vertex8);
        Edge edge14 = new Edge(vertex4, vertex6);

        List<Edge> edges = Arrays.asList(edge1,edge2,edge3,edge4,edge5,edge6,edge7,edge8,edge9,edge10,edge11,edge12,edge13,edge14);

        testObj = new Graph(vertices,new ArrayList<>());

        edges.stream().forEach(edge -> testObj.addEdge(edge.getFrom(),edge.getTo(),0));

        Set<Set<Vertex>> stronglyConnectedComponents = testObj.findStronglyConnectedComponents();

        notNull(stronglyConnectedComponents);
        assertEquals("There should be 4 strong connected components", 4, stronglyConnectedComponents.size());

    }

    @Test
    public void breadthFirstSearch(){
        Vertex vertex1 = new Vertex("vertex1");
        Vertex vertex2 = new Vertex("vertex2");
        Vertex vertex3 = new Vertex("vertex3");
        Vertex vertex4 = new Vertex("vertex4");
        Vertex vertex5 = new Vertex("vertex5");
        Vertex vertex6 = new Vertex("vertex6");
        Vertex vertex7 = new Vertex("vertex7");


        vertex1.addOutgoingEdge(vertex2,0);
        vertex2.addOutgoingEdge(vertex1,0);
        vertex2.addOutgoingEdge(vertex4,0);
        vertex3.addOutgoingEdge(vertex2,0);
        vertex4.addOutgoingEdge(vertex3,0);
        vertex4.addOutgoingEdge(vertex5,0);
        vertex4.addOutgoingEdge(vertex6,0);
        vertex6.addOutgoingEdge(vertex4,0);
        vertex6.addOutgoingEdge(vertex7,0);
        vertex7.addOutgoingEdge(vertex4,0);

        Graph graph = new Graph();;
       // graph.breadthFirstSearch(vertex1,new CleverVisitor());

        List<Vertex> pathToVertex = vertex5.getPathsToVertex();

        assertEquals(3, pathToVertex.size());
        assertEquals(vertex1, pathToVertex.get(0));
        assertEquals(vertex2, pathToVertex.get(1));
        assertEquals(vertex4, pathToVertex.get(2));

    }
}
