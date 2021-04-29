package ru.ioffe.school.susanin.mapGraph;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Route;
import ru.ioffe.school.susanin.data.Stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents a map with a graph.
 */
public class MapGraph {
    //private HashMap<Vertex, ArrayList<Edge>>[] graph;
    private HashMap<Vertex, ArrayList<Edge>> graph;
    private ArrayList<Vertex> vertices;
    private ArrayList<RoadEdge> edges;

    public MapGraph(HashMap<Long, Point> points, ArrayList<Road> roads,
                    HashMap<Long, Stop> stops, ArrayList<Route> routes) {
        int id = 0;
        for (Road road : roads) {
            if (road.getTransportMeans().containsKey("foot") || road.getTransportMeans().containsKey("car")) {
                vertices.add(new Vertex(points.get(road.getFrom()), id));
                id++;
                vertices.add(new Vertex(points.get(road.getTo()), id));
                id++;
                if (road.getTransportMeans().containsKey("foot")) {
                    edges.add(new RoadEdge(vertices.get(id - 2), vertices.get(id - 1),
                            road.getLength() / 5, 0));
                    if (!road.isOneway()) {
                        edges.add(new RoadEdge(vertices.get(id - 1), vertices.get(id - 2),
                                road.getLength() / 5, 0));
                    }
                }
            }
        }
    }
}