package ru.ioffe.school.susanin.mapGraph;

import ru.ioffe.school.susanin.data.Point;

/**
 * Represents a graph vertex
 */
public class Vertex {

    private final Point ref;
    private final int id;

    /**
     * Constructs Vertex connected to specific point on map.
     *
     * @param ref reference to a point on a map
     * @param id  unique number in graph
     */
    public Vertex(Point ref, int id) {
        this.ref = ref;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Point getRef() {
        return ref;
    }
}
