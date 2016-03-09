package com.bookexchange.graph;

/**
 * A graph visitor interface.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
interface Visitor {
    /**
     * Called by the graph traversal methods when a vertex is first visited.
     *
     * @param g -
     *          the graph
     * @param v -
     *          the vertex being visited.
     */
    public void visit(Graph g, Vertex v);
}

/**
 * A graph visitor interface that can throw an exception during a visit
 * callback.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
interface VisitorEX{
    /**
     * Called by the graph traversal methods when a vertex is first visited.
     *
     * @param g -
     *          the graph
     * @param v -
     *          the vertex being visited.
     * @param o
     *           exception for any error
     */
    public void visit(Graph g, Vertex v, Vertex o) throws Exception;
}
