package com.bookexchange.graph;

import com.bookexchange.graph.abstraction.BaseElementWithWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by sheke on 3/1/2016.
 */
public class Path implements BaseElementWithWeight {
    private List<Vertex> vertexList = new ArrayList<>();
    private double weight = -1;

    public Path() { }

    public Path(List<Vertex> vertexList, double weight) {
        this.vertexList = vertexList;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    @Override
    public boolean equals(Object right) {

        if (right instanceof Path) {
            Path rPath = (Path) right;
            return vertexList.equals(rPath.vertexList);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return vertexList.hashCode();
    }

    public String toString() {
        return vertexList.stream().map(tComplexVertex -> tComplexVertex.getName()).collect(Collectors.joining(" "));
    }

    public List<String> toStringList(){
        return getVertexList().stream().map(Vertex::getName).collect(toList());
    }
}
