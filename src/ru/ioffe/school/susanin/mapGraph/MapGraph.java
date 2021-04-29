package ru.ioffe.school.susanin.mapGraph;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Route;
import ru.ioffe.school.susanin.data.Stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a map with a graph.
 */
public class MapGraph {
    //private HashMap<Vertex, ArrayList<Edge>>[] graph;
    private HashMap<Vertex, ArrayList<RoadEdge>> graph;
    private ArrayList<Vertex> vertices;
    private ArrayList<RoadEdge> edges;

    public MapGraph(HashMap<Long, Point> points, HashSet<Road> roads,
                    HashMap<Long, Stop> stops, ArrayList<Route> routes) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.graph = new HashMap<>();
        int id = 0;
        int edgesCount = 0;
        for (Road road : roads) {
            if (road.getTransportMeans().containsKey("foot")) {
                if (points.get(road.getFrom()) != null && points.get(road.getTo()) != null) {
                    vertices.add(new Vertex(points.get(road.getFrom()), id));
                    id++;
                    vertices.add(new Vertex(points.get(road.getTo()), id));
                    id++;
                    if (road.getTransportMeans().containsKey("foot")) {
                        edges.add(new RoadEdge(vertices.get(id - 2), vertices.get(id - 1),
                                road.getLength() / 5, 0));
                        if (!graph.containsKey(vertices.get(id - 2))) {
                            graph.put(vertices.get(id - 2), new ArrayList<RoadEdge>());
                        }
                        graph.get(vertices.get(id - 2)).add(edges.get(edgesCount));
                        edgesCount++;
                        if (!road.isOneway()) {
                            edges.add(new RoadEdge(vertices.get(id - 1), vertices.get(id - 2),
                                    road.getLength() / 5, 0));
                            if (!graph.containsKey(vertices.get(id - 1))) {
                                graph.put(vertices.get(id - 1), new ArrayList<RoadEdge>());
                            }
                            graph.get(vertices.get(id - 2)).add(edges.get(edgesCount));
                            edgesCount++;
                        }
                    }
                }
            }
        }
    }

    public HashMap<Vertex, ArrayList<RoadEdge>> getGraph() {
        return graph;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<RoadEdge> getEdges() {
        return edges;
    }
}