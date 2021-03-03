package ru.ioffe.school.susanin.mapGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a map with a graph.
 */
public class Graph {

    private HashMap<Vertex, ArrayList<Edge>> graph;

    public Graph(ArrayList<Vertex> vertexes, ArrayList<Edge> edges) {
        this.graph = new HashMap<>();

        for (Edge edge : edges) {
            Vertex from = edge.getFrom();
            ArrayList<Edge> info = new ArrayList<>(graph.get(from));
            info.add(edge);
            this.graph.put(from, info);
        }
    }
}