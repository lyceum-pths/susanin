package ru.ioffe.school.susanin.navigator;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.mapGraph.Edge;
import ru.ioffe.school.susanin.mapGraph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Navigator {

    public Navigator() {};

    public double findPathSquare(Point start, Point end, int maxCost, HashMap<Vertex, ArrayList<Edge>> graph,
                           ArrayList<Vertex> vertices) {
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
}
