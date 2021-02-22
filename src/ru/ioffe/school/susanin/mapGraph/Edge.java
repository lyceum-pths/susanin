package ru.ioffe.school.susanin.mapGraph;

/**
 * Represents a graph edge
 */
public class Edge {

    private final Vertex from;
    private final Vertex to;
    private final int time;
    private final int cost;

    /**
     * Constructs Edge with specific cost and
     * time required to go through it.
     *
     * @param from edge start
     * @param to   edge end
     * @param time time in minutes required to go through edge
     * @param cost edge cost
     */
    public Edge(Vertex from, Vertex to, int time, int cost) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.cost = cost;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }
}
