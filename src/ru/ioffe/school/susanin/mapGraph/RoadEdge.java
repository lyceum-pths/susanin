package ru.ioffe.school.susanin.mapGraph;

public class RoadEdge {
    private final Vertex from;
    private final Vertex to;
    private final double time;
    private final int cost;

    /**
     * Constructs RoadEdge with specific cost and
     * time required to go through it.
     *
     * @param from edge start
     * @param to   edge end
     * @param time time in minutes required to go through edge
     * @param cost edge cost
     */
    public RoadEdge(Vertex from, Vertex to, double time, int cost) {
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

    public double getTime() {
        return time;
    }
    
}
