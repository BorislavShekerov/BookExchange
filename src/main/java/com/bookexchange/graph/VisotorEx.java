package com.bookexchange.graph;

/**
 * A graph visitor interface.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 * @param <T>
 */
interface Visitor<T> {
    /**
     * Called by the graph traversal methods when a vertex is first visited.
     *
     * @param g -
     *          the graph
     * @param v -
     *          the vertex being visited.
     */
    public void visit(Graph<T> g, Vertex<T> v);
}

/**
 * A graph visitor interface that can throw an exception during a visit
 * callback.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 * @param <T>
 * @param <E>
 */
interface VisitorEX<T, E extends Exception> {
    /**
     * Called by the graph traversal methods when a vertex is first visited.
     *
     * @param g -
     *          the graph
     * @param v -
     *          the vertex being visited.
     * @param o
     * @throws E
     *           exception for any error
     */
    public void visit(Graph<T> g, Vertex<T> v, Vertex<T> o) throws E;
}
