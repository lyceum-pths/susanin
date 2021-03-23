package ru.ioffe.school.susanin.mapGraph;

public class NotBusEdge {
    private final Vertex from;
    private final Vertex to;
    private final int cost;

    /**
     * Constructs Edge with specific cost required to go through it.
     *
     * @param from edge start
     * @param to   edge end
     * @param time time in minutes required to go through edge
     * @param cost edge cost
     */
    public NotBusEdge(Vertex from, Vertex to, int time, int cost) {
        this.from = from;
        this.to = to;
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

}
