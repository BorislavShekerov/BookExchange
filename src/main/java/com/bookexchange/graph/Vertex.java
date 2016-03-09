package com.bookexchange.graph;
/*
 * JBoss, Home of Professional Open Source Copyright 2006, Red Hat Middleware
 * LLC, and individual contributors by the @authors tag. See the copyright.txt
 * in the distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */

import com.bookexchange.graph.abstraction.BaseVertex;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A named graph vertex with optional data.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@Entity
@Table(name = "GRAPH_VERTICES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vertex implements BaseVertex,Comparable<Vertex> {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @OneToMany(mappedBy = "to")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Edge> incomingEdges;
    @OneToMany(mappedBy = "from")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Edge> outgoingEdges;
    @Column(name = "NAME")
    private String name;
    @Transient
    private boolean mark;
    @Transient
    private int markState;
    @Transient
    private String data;
    @Transient
    private int index = -1;
    @Transient
    private int lowIndex = -1;
    @Transient
    private boolean onStack;
    @Transient
    private int weight = 0;
    @Transient
    private List<Vertex> pathsToVertex = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRAPH_BELONGING_TO")
    private Graph graphBelongingTo;
    /**
     * Calls this(null, null).
     */
    public Vertex() {
        this(null, null);
    }

    /**
     * Create a vertex with the given name and no data
     *
     * @param n
     */
    public Vertex(String n) {
        this(n, null);
    }

    /**
     * Create a Vertex with name n and given data
     *
     * @param name -
     *          name of vertex
     * @param data -
     *          data associated with vertex
     */
    public Vertex(String name,  String data) {
        incomingEdges = new ArrayList<Edge>();
        outgoingEdges = new ArrayList<Edge>();
        this.name = name;
        mark = false;
        this.data = data;
    }

    /**
     * @return the possibly null name of the vertex
     */
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double weight) {

    }

    /**
     * @return the possibly null data of the vertex
     */
    public String getData() {
        return this.data;
    }

    /**
     * @param data
     *          The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Add an edge to the vertex. If edge.from is this vertex, its an outgoing
     * edge. If edge.to is this vertex, its an incoming edge. If neither from or
     * to is this vertex, the edge is not added.
     *
     * @param e -
     *          the edge to add
     * @return true if the edge was added, false otherwise
     */
    public boolean addEdge(Edge e) {
        if (e.getFrom() == this)
            outgoingEdges.add(e);
        else if (e.getTo() == this)
            incomingEdges.add(e);
        else
            return false;
        return true;
    }
    /**
     * Add an outgoing edge ending at to.
     *
     * @param to -
     *          the destination vertex
     * @param cost
     *          the edge cost
     */
    public void addOutgoingEdge(Vertex to, int cost) {
        Edge out = new Edge(this, to, cost);
        outgoingEdges.add(out);
    }

    /**
     * Add an incoming edge starting at from
     *
     * @param from -
     *          the starting vertex
     * @param cost
     *          the edge cost
     */
    public void addIncomingEdge(Vertex from, int cost) {
        Edge out = new Edge(this, from, cost);
        incomingEdges.add(out);
    }

    /**
     * Check the vertex for either an incoming or outgoing edge mathcing e.
     *
     * @param e
     *          the edge to check
     * @return true it has an edge
     */
    public boolean hasEdge(Edge e) {
        if (e.getFrom() == this)
            return incomingEdges.contains(e);
        else if (e.getTo() == this)
            return outgoingEdges.contains(e);
        else
            return false;
    }

    /**
     * Remove an edge from this vertex
     *
     * @param e -
     *          the edge to remove
     * @return true if the edge was removed, false if the edge was not connected
     *         to this vertex
     */
    public boolean remove(Edge e) {
        if (e.getFrom() == this)
            incomingEdges.remove(e);
        else if (e.getTo() == this)
            outgoingEdges.remove(e);
        else
            return false;
        return true;
    }

    /**
     *
     * @return the count of incoming edges
     */
    public int getIncomingEdgeCount() {
        return incomingEdges.size();
    }

    /**
     * Get the ith incoming edge
     *
     * @param i
     *          the index into incoming edges
     * @return ith incoming edge
     */
    public Edge getIncomingEdge(int i) {
        return incomingEdges.get(i);
    }

    /**
     * Get the incoming edges
     *
     * @return incoming edge list
     */
    public List<Edge> getIncomingEdges() {
        return this.incomingEdges;
    }

    /**
     *
     * @return the count of incoming edges
     */
    public int getOutgoingEdgeCount() {
        return outgoingEdges.size();
    }

    /**
     * Get the ith outgoing edge
     *
     * @param i
     *          the index into outgoing edges
     * @return ith outgoing edge
     */
    public Edge getOutgoingEdge(int i) {
        return outgoingEdges.get(i);
    }

    /**
     * Get the outgoing edges
     *
     * @return outgoing edge list
     */
    public List<Edge> getOutgoingEdges() {
        return this.outgoingEdges;
    }

    /**
     * Search the outgoing edges looking for an edge whose's edge.to == dest.
     *
     * @param dest
     *          the destination
     * @return the outgoing edge going to dest if one exists, null otherwise.
     */
    public Edge findEdge(Vertex dest) {
        for (Edge e : outgoingEdges) {
            if (e.getTo() == dest)
                return e;
        }
        return null;
    }

    /**
     * Search the outgoing edges for a match to e.
     *
     * @param e -
     *          the edge to check
     * @return e if its a member of the outgoing edges, null otherwise.
     */
    public Edge findEdge(Edge e) {
        if (outgoingEdges.contains(e))
            return e;
        else
            return null;
    }

    /**
     * Is there an outgoing edge ending at dest.
     *
     * @param dest -
     *          the vertex to check
     * @return true if there is an outgoing edge ending at vertex, false
     *         otherwise.
     */
    public boolean hasEdge(Vertex dest) {
        return (findEdge(dest) != null);
    }

    /**
     * Has this vertex been marked during a visit
     *
     * @return true is visit has been called
     */
    public boolean visited() {
        return mark;
    }

    /**
     * Set the vertex mark flag.
     *
     */
    public void mark() {
        mark = true;
    }

    /**
     * Set the mark state to state.
     *
     * @param state
     *          the state
     */
    public void setMarkState(int state) {
        markState = state;
    }

    /**
     * Get the mark state value.
     *
     * @return the mark state
     */
    public int getMarkState() {
        return markState;
    }

    public Set<Vertex> getAllAdjacentVertices(){
        return this.outgoingEdges.stream().map(Edge::getTo).collect(Collectors.toSet());
    }

    public Set<Vertex> getAllPrecedentVertices() {
        return this.incomingEdges.stream().map(Edge::getFrom).collect(Collectors.toSet());
    }
    /**
     * Visit the vertex and set the mark flag to true.
     *
     */
    public void visit() {
        mark();
    }

    public List<Vertex> getPathsToVertex() {
        return pathsToVertex;
    }

    public void setPathsToVertex(List<Vertex> pathsToVertex) {
        this.pathsToVertex = pathsToVertex;
    }

    /**
     * Clear the visited mark flag.
     *
     */
    public void clearMark() {
        mark = false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLowIndex() {
        return lowIndex;
    }

    public void setLowIndex(int lowIndex) {
        this.lowIndex = lowIndex;
    }

    public boolean isOnStack() {
        return onStack;
    }

    public void setOnStack(boolean onStack) {
        this.onStack = onStack;
    }

    /**
     * @return a string form of the vertex with in and out edges.
     */
    public String toString() {
        StringBuffer tmp = new StringBuffer("Vertex(");
        tmp.append(name);
        tmp.append(", data=");
        tmp.append(data);
        tmp.append("), in:[");
        for (int i = 0; i < incomingEdges.size(); i++) {
            Edge e = incomingEdges.get(i);
            if (i > 0)
                tmp.append(',');
            tmp.append('{');
            tmp.append(e.getFrom().name);
            tmp.append('}');
        }
        tmp.append("], out:[");
        for (int i = 0; i < outgoingEdges.size(); i++) {
            Edge e = outgoingEdges.get(i);
            if (i > 0)
                tmp.append(',');
            tmp.append('{');
            tmp.append(e.getTo().name);
            tmp.append('}');
        }
        tmp.append(']');
        return tmp.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return !(getName() != null ? !getName().equals(vertex.getName()) : vertex.getName() != null);

    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }


    @Override
    public int compareTo(Vertex o) {
        double diff = this.weight - o.weight;
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIncomingEdges(List<Edge> incomingEdges) {
        this.incomingEdges = incomingEdges;
    }

    public void setOutgoingEdges(List<Edge> outgoingEdges) {
        this.outgoingEdges = outgoingEdges;
    }

    public Graph getGraphBelongingTo() {
        return graphBelongingTo;
    }

    public void setGraphBelongingTo(Graph graphBelongingTo) {
        this.graphBelongingTo = graphBelongingTo;
    }

}

