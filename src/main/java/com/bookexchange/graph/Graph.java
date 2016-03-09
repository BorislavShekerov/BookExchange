package com.bookexchange.graph;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

import com.bookexchange.graph.abstraction.BaseGraph;
import com.bookexchange.graph.abstraction.BaseVertex;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * A directed graph data structure.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@Entity
@Table(name = "GRAPHS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Graph implements BaseGraph{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "PATHS_EXPLORED_COUNT")
    private int pathsExploredCount;
    @Transient
    private Set<String> removedVerticesNameSet = new HashSet<>();
    @Transient
    private Set<Edge> removedEdgesSet = new HashSet<>();
    @Transient
    public static final double DISCONNECTED = Double.MAX_VALUE;
    /**
     * Color used to mark unvisited nodes
     */
    @Transient
    public static final int VISIT_COLOR_WHITE = 1;

    /**
     * Color used to mark nodes as they are first visited in DFS order
     */
    @Transient
    public static final int VISIT_COLOR_GREY = 2;

    /**
     * Color used to mark nodes after descendants are completely visited
     */
    @Transient
    public static final int VISIT_COLOR_BLACK = 3;

    /**
     * Vector<Vertex> of graph verticies
     */
    @OneToMany(mappedBy = "graphBelongingTo",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vertex> verticies;

    /**
     * Vector<Edge> of edges in the graph
     */
    @OneToMany(mappedBy = "graphBelongingTo",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Edge> edges;

    /**
     * The vertex identified as the root of the graph
     */
    @Transient
    private Vertex rootVertex;

    @Transient
    private int index = 0;
    /**
     * Used for finding closed components
     */
    @Transient
    private Deque<Vertex> nodeStack;
    @Transient
    private final Function<Vertex,Set<Vertex>> findAdjacentVerticesFunction = v -> v.getAllAdjacentVertices();
    @Transient
    private final Function<Vertex,Set<Vertex>> findPrecedentVerticesFunction = v -> v.getAllPrecedentVertices();

    /**
     * Construct a new graph without any vertices or edges
     */
    public Graph() {
        verticies = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
    }


    /**
     * Construct a new graph without any vertices or edges
     */
    public Graph(List<Vertex> verticies, List<Edge> edges) {
        this.verticies = verticies;
        this.edges = edges;
    }

    /**
     * Are there any verticies in the graph
     *
     * @return true if there are no verticies in the graph
     */
    public boolean isEmpty() {
        return verticies.size() == 0;
    }

    /**
     * Add a vertex to the graph
     *
     * @param v the Vertex to add
     * @return true if the vertex was added, false if it was already in the graph.
     */
    public boolean addVertex(Vertex v) {
        boolean added = false;
        if (verticies.contains(v) == false) {
            added = verticies.add(v);
        }
        return added;
    }

    /**
     * Get the vertex count.
     *
     * @return the number of verticies in the graph.
     */
    public int size() {
        return verticies.size();
    }

    /**
     * Get the root vertex
     *
     * @return the root vertex if one is set, null if no vertex has been set as
     * the root.
     */
    public Vertex getRootVertex() {
        return rootVertex;
    }

    /**
     * Set a root vertex. If root does no exist in the graph it is added.
     *
     * @param root -
     *             the vertex to set as the root and optionally add if it does not
     *             exist in the graph.
     */
    public void setRootVertex(Vertex root) {
        this.rootVertex = root;
        if (verticies.contains(root) == false)
            this.addVertex(root);
    }

    /**
     * Get the given Vertex.
     *
     * @param n the index [0, size()-1] of the Vertex to access
     * @return the nth Vertex
     */
    public Vertex getVertex(int n) {
        return verticies.get(n);
    }

    /**
     * Get the graph verticies
     *
     * @return the graph verticies
     */
    public List<Vertex> getVerticies() {
        return this.verticies;
    }

    /**
     * Insert a directed, weighted Edge<T> into the graph.
     *
     * @param from -
     *             the Edge<T> starting vertex
     * @param to   -
     *             the Edge<T> ending vertex
     * @param cost -
     *             the Edge<T> weight/cost
     * @return true if the Edge<T> was added, false if from already has this Edge<T>
     * @throws IllegalArgumentException if from/to are not verticies in the graph
     */
    public boolean addEdge(Vertex from, Vertex to, int cost) throws IllegalArgumentException {
        if (verticies.contains(from) == false)
            throw new IllegalArgumentException("from is not in graph");
        if (verticies.contains(to) == false)
            throw new IllegalArgumentException("to is not in graph");

        Edge e = new Edge(from, to, cost);
        if (from.findEdge(to) != null)
            return false;
        else {
            from.addEdge(e);
            to.addEdge(e);
            edges.add(e);
            return true;
        }
    }

    public void addEdgeNoChecks(Vertex from, Vertex to, int cost) throws IllegalArgumentException {
        if (verticies.contains(from) == false)
            throw new IllegalArgumentException("from is not in graph");
        if (verticies.contains(to) == false)
            throw new IllegalArgumentException("to is not in graph");

        Edge e = new Edge(from, to, cost);
//        if (from.findEdge(to) != null)
//            return false;
//        else {
//            from.addEdge(e);
//            to.addEdge(e);
            edges.add(e);
//            return true;

    }


    /**
     * Get the graph edges
     *
     * @return the graph edges
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    public Set<Set<Vertex>> findStronglyConnectedComponents() {
        index = 0;
        nodeStack = new ArrayDeque<>();
        Set<Set<Vertex>> stronglyConnectedComponents = new HashSet<>();

        verticies.stream().filter(vertex -> vertex.getIndex() == -1).forEach(vertex -> strongConnect(vertex,stronglyConnectedComponents));
        return stronglyConnectedComponents;
    }

    private void strongConnect(Vertex vertex, Set<Set<Vertex>> stronglyConnectedComponents) {
        vertex.setIndex(index);
        vertex.setLowIndex(index);
        index++;

        nodeStack.push(vertex);
        vertex.setOnStack(true);

        // Consider successors of v
        List<Edge> outgoingEdges = vertex.getOutgoingEdges();
        outgoingEdges.stream().map(edge -> edge.getTo()).forEach(successorVertex -> {
            if (successorVertex.getIndex() == -1) {
                // Successor w has not yet been visited; recurse on it
                strongConnect(successorVertex, stronglyConnectedComponents);
                vertex.setLowIndex(Math.min(vertex.getLowIndex(), successorVertex.getLowIndex()));
            } else if (successorVertex.isOnStack()) {
                vertex.setLowIndex(Math.min(vertex.getLowIndex(), successorVertex.getLowIndex()));
            }
        });

        // If v is a root node, pop the stack and generate an SCC

        if (vertex.getIndex() == vertex.getLowIndex()){
            //start a new strongly connected component
            Set<Vertex> stronglyConnectedComponent = new HashSet<>();
            Vertex vertexPoppedFromTheStack;

            do{
                vertexPoppedFromTheStack = nodeStack.pop();
                vertexPoppedFromTheStack.setOnStack(false);
                stronglyConnectedComponent.add(vertexPoppedFromTheStack);
            }while(!vertexPoppedFromTheStack.getName().equals(vertex.getName()));

            stronglyConnectedComponents.add(stronglyConnectedComponent);
        }


    }


    /**
     * Perform a breadth first search of this graph, starting at v. The vist may
     * be cut short if visitor throws an exception during a vist callback.
     *
     * @param <E>
     *
     * @param v -
     *          the search starting point
     * @param visitor -
     *          the vistor whose vist method is called prior to visting a vertex.
     * @throws E
     *           if vistor.visit throws an exception
     */
//    public <E extends Exception> void breadthFirstSearch(Vertex v, VisitorEX<T, E> visitor)
//            throws E {
//        LinkedList<Vertex<T>> q = new LinkedList<Vertex<T>>();
//
//        q.add(v);
//        if (visitor != null)
//            visitor.visit(this, v,null);
//        v.visit();
//        while (q.isEmpty() == false) {
//            v = q.removeFirst();
//            for (int i = 0; i < v.getOutgoingEdgeCount(); i++) {
//                Edge<T> e = v.getOutgoingEdge(i);
//                Vertex<T> to = e.getTo();
//                if (!to.visited()) {
//                    q.add(to);
//                    if (visitor != null)
//                        visitor.visit(this, to, v);
//                    to.visit();
//                }
//            }
//        }
//    }

    public String toString() {
        StringBuffer tmp = new StringBuffer("Graph[");
        for (Vertex v : verticies)
            tmp.append(v);
        tmp.append(']');
        return tmp.toString();
    }

    @Override
    public List<Vertex> getVertexList() {
        return verticies;
    }

    @Override
    public double getEdgeWeight(Vertex source, Vertex sink) {
        return 0;
    }

    @Override
    public Set<Vertex> getAdjacentVertices(Vertex vertex) {
        return getNeighbours(vertex,findAdjacentVerticesFunction);
    }

    @Override
    public Set<Vertex> getPrecedentVertices(Vertex vertex) {
        return getNeighbours(vertex,findPrecedentVerticesFunction);
    }

    public int getPathsExploredCount() {
        return pathsExploredCount;
    }

    public void setPathsExploredCount(int pathsExploredCount) {
        this.pathsExploredCount = pathsExploredCount;
    }

    private Set<Vertex> getNeighbours(Vertex vertex, Function<Vertex,Set<Vertex>> adjacentVerticesFunction){
            Set<Vertex> retSet = new HashSet<>();
            String startingVertexName = vertex.getName();

            if (!removedVerticesNameSet.contains(startingVertexName))	{
                Set<Vertex> adjVertexSet = adjacentVerticesFunction.apply(vertex);
                for (Vertex curVertex : adjVertexSet) {
                    String endingVertexName = curVertex.getName();
                    if (removedVerticesNameSet.contains(endingVertexName) ||
                            removedEdgesSet.contains(new Edge(vertex,curVertex))) {
                        continue;
                    }

                    retSet.add(curVertex);
                }
            }
            return retSet;
    }

    public void deleteEdge(String edgeFromVertexName,String edgeToVertexName) {
        removedEdgesSet.add(findEdge(edgeFromVertexName, edgeToVertexName));
    }

    public void deleteVertex(String vertexName) {
        removedVerticesNameSet.add(vertexName);
    }

    public void recoverDeletedVertex(String vertexName) {
        removedVerticesNameSet.remove(vertexName);
    }

    public double getEdgeWeightOfGraph(BaseVertex baseVertex, BaseVertex baseVertex1) {
        return 0;
    }

    public void recoverDeletedEdge(String edgeFromVertexName,String edgeToVertexName) {
        Edge deletedEdge = findEdge(edgeFromVertexName, edgeToVertexName);
        removedEdgesSet.remove(deletedEdge);
    }

    public void recoverDeletedEdges() {
        removedEdgesSet.clear();
    }

    public void recoverDeletedVertices() {
        removedVerticesNameSet.clear();
    }

    private Edge findEdge(String edgeFromVertexName,String edgeToVertexName){
        return edges.stream().filter(edge -> edge.getFrom().getName().equals(edgeFromVertexName) &&
                edge.getTo().getName().equals(edgeToVertexName))
                .findFirst().get();
    }

    public void fixMappings() {
        this.id = 0;
        this.edges.stream().distinct().forEach(edge -> {
            edge.setGraphBelongingTo(this);
            edge.setId(0);
        });
        this.verticies.stream().distinct().forEach(vertex -> {
            if(vertex != null){
                vertex.setId(0);
                vertex.setGraphBelongingTo(this);
            }
        });

    }

    public void removeDuplicates(){
        Set<Vertex> uniqueVertices = new HashSet<>(verticies);
        Set<Edge> uniqueEdges = new HashSet<>(edges);

        verticies = new ArrayList<>(uniqueVertices);
        edges = new ArrayList<>(uniqueEdges);
    }

    public void increaseExploredPathsCount() {
        pathsExploredCount++;
    }

}

