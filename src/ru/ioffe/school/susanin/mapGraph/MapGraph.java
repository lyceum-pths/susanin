package ru.ioffe.school.susanin.mapGraph;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents a map with a graph.
 */
public class MapGraph {
    // private ArrayList<HashMap<Vertex, ArrayList<Edge>>> graph;
    private HashMap<Vertex, ArrayList<RoadEdge>> graph;
    private ArrayList<Vertex> vertices;
    private ArrayList<RoadEdge> edges;

    public MapGraph(int layers, HashMap<Long, Point> points, ArrayList<Road> roads) {
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
                /* will uncomment after
                if (road.getTransportMeans().containsKey("car")) {
                    edges.add(new RoadEdge(vertices.get(id-1), vertices.get(id), road.getLength() / road.getSpeedLimit(), 10));
                    if (!road.isOneway()) {
                        edges.add(new RoadEdge(vertices.get(id), vertices.get(id-1), road.getLength() / road.getSpeedLimit(), 10));
                    }
                }
                */
            }
        }
    }

    public double FindRoute(Point start, Point end /*, int maxCost */) {
        final int INFINITY = 1000;
        double[] times = new double[vertices.size()];
        Arrays.fill(times, INFINITY);
        boolean[] visited = new boolean[vertices.size()];
        Arrays.fill(visited, false);
        Vertex from = vertices.get(0), to = vertices.get(0);
        for (Vertex vertex : vertices) {
            if (vertex.getRef().equals(start)) {
                from = vertex;
            } else if (vertex.getRef().equals(end)) {
                to = vertex;
            }
        }
        times[vertices.indexOf(from)] = 0.0;
        for (int i = 0; i < vertices.size(); i++) {
            int current = -1;
            for (int j = 0; j < vertices.size(); j++) {
                if (!visited[j] && (current == -1 || times[j] < times[current])) {
                    current = j;
                }
            }
            if (times[current] == INFINITY) {
                break;
            }
            visited[current] = true;
            for (RoadEdge edge : graph.get(vertices.get(current))) {
                Vertex dest = edge.getTo();
                double time = edge.getTime();
                if (times[current] + time < times[dest.getId()]) {
                    times[dest.getId()] = times[current] + time;
                }
            }
        }
        return times[to.getId()];
    }

    /**
     * Constructs multi-layer graph
     * from vertices and edges. Each layer
     * represents specific trip price.
     *
     * @param layers   price layers count
     * @param vertices graph vertices
     * @param edges    graph edges
     */
    /* Will be deleted when new constructor eil be created
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
     */
}