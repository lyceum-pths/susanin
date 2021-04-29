package ru.ioffe.school.susanin.navigator;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.mapGraph.Edge;
import ru.ioffe.school.susanin.mapGraph.Vertex;
import ru.ioffe.school.susanin.utils.MapUtils;

import java.util.*;

public class Navigator {

    private Point from;
    private Point to;
    private int maxCost;

    public Navigator(double fromLat, double fromLon, double toLat, double toLon, ArrayList<Point> points) {
        this.from = MapUtils.getClosestPoint(fromLat, fromLon, points);
        this.to = MapUtils.getClosestPoint(toLat, toLon, points);
    }

    public double findPathSquare(Point start, Point end, int maxCost, HashMap<Vertex, ArrayList<Edge>> graph,
                                 ArrayList<Vertex> vertices) {
        final int INFINITY = 100000;
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
            for (Edge edge : graph.get(vertices.get(current))) {
                Vertex dest = edge.getTo();
                double time = edge.getTime(0.0);
                if (times[current] + time < times[dest.getId()]) {
                    times[dest.getId()] = times[current] + time;
                }
            }
        }
        return times[to.getId()];
    }

    public LinkedHashMap<Edge, Double> navigate(Point start, Point end, double startTime, int maxCost,
                                                HashMap<Vertex, ArrayList<Edge>> graph, ArrayList<Vertex> vertices) {
        final double INFINITY = 1000000.0;
        double[] times = new double[vertices.size()];
        Edge[] prev = new Edge[vertices.size()];
        LinkedHashMap<Edge, Double> route = new LinkedHashMap<>();
        Arrays.fill(times, INFINITY);
        Vertex origin = vertices.get(0), destination = vertices.get(0);
        for (Vertex vertex : vertices) {
            if (vertex.getRef().equals(start)) {
                origin = vertex;
            } else if (vertex.getRef().equals(end)) {
                destination = vertex;
            }
            vertex.setTime(INFINITY);
        }
        times[vertices.indexOf(origin)] = 0.0;
        PriorityQueue<Vertex> queue = new PriorityQueue<>((v1, v2) -> {
            if (v1.getTime() > v2.getTime()) {
                return 1;
            }
            if (v1.getTime() < v2.getTime()) {
                return -1;
            }
            return 0;
        });
        queue.add(origin);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            double currentTime = vertex.getTime();
            if (currentTime > times[vertices.indexOf(vertex)]) {
                continue;
            }

            for (Edge edge : graph.get(vertex)) {
                Vertex to = edge.getTo();
                double gap = edge.getTime(startTime + times[vertices.indexOf(vertex)]);
                if (times[vertex.getId()] + gap < times[to.getId()]) {
                    to.setTime(times[vertex.getId()] + gap);
                    times[to.getId()] = to.getTime();
                    prev[to.getId()] = edge;
                    queue.add(to);
                }
            }
        }

        Edge currentEdge = prev[destination.getId()];
        while (!currentEdge.getFrom().equals(origin)) {
            route.put(currentEdge, times[currentEdge.getTo().getId()] - startTime);
            currentEdge = prev[currentEdge.getFrom().getId()];
        }
        return route;
    }
}
