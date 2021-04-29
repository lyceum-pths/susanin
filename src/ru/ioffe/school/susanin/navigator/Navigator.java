package ru.ioffe.school.susanin.navigator;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.mapGraph.Edge;
import ru.ioffe.school.susanin.mapGraph.MapGraph;
import ru.ioffe.school.susanin.mapGraph.RoadEdge;
import ru.ioffe.school.susanin.mapGraph.Vertex;
import ru.ioffe.school.susanin.utils.MapUtils;

import java.util.*;

public class Navigator {

    private Point start;
    private Point end;
    private int maxCost;

    public Navigator(double fromLat, double fromLon, double toLat, double toLon,
                     ArrayList<Point> points) {
        this.start = MapUtils.getClosestPoint(fromLat, fromLon, points);
        this.end = MapUtils.getClosestPoint(toLat, toLon, points);
    }

    public double navigate(double startTime, int maxCost,
                                                MapGraph mapGraph) {
        ArrayList<Vertex> vertices = mapGraph.getVertices();
        HashMap<Vertex, ArrayList<RoadEdge>> graph = mapGraph.getGraph();
        final double INFINITY = 1000000.0;
        double[] times = new double[vertices.size()];
        RoadEdge[] prev = new RoadEdge[vertices.size()];
        LinkedHashMap<RoadEdge, Double> route = new LinkedHashMap<>();
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
        times[origin.getId()] = 0.0;
        origin.setTime(times[origin.getId()]);
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

            for (RoadEdge edge : graph.get(vertex)) {
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

        RoadEdge currentEdge = prev[destination.getId()];
        while (!currentEdge.getFrom().equals(origin)) {
            route.put(currentEdge, times[currentEdge.getTo().getId()] - startTime);
            currentEdge = prev[currentEdge.getFrom().getId()];
        }
        return times[origin.getId()];
    }
}
