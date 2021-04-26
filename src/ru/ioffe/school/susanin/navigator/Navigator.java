package ru.ioffe.school.susanin.navigator;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.mapGraph.Edge;
import ru.ioffe.school.susanin.mapGraph.Vertex;

import java.util.*;

public class Navigator {

    public Navigator() {
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

    public double findPath(Point start, Point end, int maxCost, HashMap<Vertex, ArrayList<Edge>> graph,
                           ArrayList<Vertex> vertices) {
        final double INFINITY = 1000000.0;
        double[] times = new double[vertices.size()];
        Arrays.fill(times, INFINITY);
        Vertex from = vertices.get(0), dest = vertices.get(0);
        for (Vertex vertex : vertices) {
            if (vertex.getRef().equals(start)) {
                from = vertex;
            } else if (vertex.getRef().equals(end)) {
                dest = vertex;
            }
            vertex.setTime(INFINITY);
        }
        times[vertices.indexOf(from)] = 0.0;
        PriorityQueue<Vertex> queue = new PriorityQueue<>((v1, v2) -> {
            if (v1.getTime() > v2.getTime()) {
                return 1;
            }
            if (v1.getTime() < v2.getTime()) {
                return -1;
            }
            return 0;
        });
        queue.add(from);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            double currentTime = vertex.getTime();
            if (currentTime > times[vertices.indexOf(vertex)]) {
                continue;
            }

            for (Edge edge : graph.get(vertex)) {
                Vertex to = edge.getTo();
                double gap = edge.getTime(0.0);
                if (times[vertex.getId()] + gap < times[to.getId()]) {
                    to.setTime(times[vertex.getId()] + gap);
                    times[to.getId()] = to.getTime();
                    // route[to.getId()] = vertex.getId();
                    queue.add(to);
                }
            }
        }
        return times[dest.getId()];
    }
}
