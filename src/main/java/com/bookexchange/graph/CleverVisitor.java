package com.bookexchange.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sheke on 11/18/2015.
 */
public class CleverVisitor implements VisitorEX {

    @Override
    public void visit(Graph g, Vertex v, Vertex parent) throws Exception {
        if(parent != null){
            List<Vertex> pathsToParent = parent.getPathsToVertex();
            if(pathsToParent.size() == 0){
                v.setPathsToVertex(Arrays.asList(parent));
            } else {
                List<Vertex> pathsToParentClone = new ArrayList<>();
                pathsToParentClone.addAll(pathsToParent);
                pathsToParentClone.add(parent);

                v.setPathsToVertex(pathsToParentClone);
            }
        }
    }
}
