package ru.ioffe.school.susanin.mapGraph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a map with a graph.
 */
public class MapGraph {
    private ArrayList<HashMap<Vertex, ArrayList<Edge>>> graph;
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    /**
     * Constructs multi-layer graph
     * from vertices and edges. Each layer
     * represents specific trip price.
     *
     * @param layers   price layers count
     * @param vertices graph vertices
     * @param edges    graph edges
     */
    public MapGraph(int layers, ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
        this.graph = new ArrayList<>();
        this.vertices = vertices;
        this.edges = edges;

        HashMap<Vertex, ArrayList<Edge>> layer = new HashMap<>();
        for (Edge edge : edges) {
            Vertex from = edge.getFrom();
            ArrayList<Edge> info = new ArrayList<>(layer.get(from));
            info.add(edge);
            layer.put(from, info);
        }
        for (int i = 0; i < layers; i++) {
            this.graph.add(layer);
        }
    }
}