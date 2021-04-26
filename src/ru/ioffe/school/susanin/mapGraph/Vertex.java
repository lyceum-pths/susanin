package ru.ioffe.school.susanin.mapGraph;

import ru.ioffe.school.susanin.data.Point;

import java.util.Comparator;

/**
 * Represents a graph vertex
 */
public class Vertex implements Comparator<Vertex> {

    private static final double infinity = 1000000.0;

    private final Point ref;
    private final int id;
    private double time;

    /**
     * Constructs Vertex connected to specific point on map.
     *
     * @param ref reference to a point on a map
     * @param id  unique number in graph
     */
    public Vertex(Point ref, int id) {
        this.ref = ref;
        this.id = id;
        this.time = infinity;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Point getRef() {
        return ref;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compare(Vertex v1, Vertex v2) {
        if (v1.time > v2.time) {
            return 1;
        }
        if (v1.time < v2.time) {
            return -1;
        }
        return 0;
    }
}
