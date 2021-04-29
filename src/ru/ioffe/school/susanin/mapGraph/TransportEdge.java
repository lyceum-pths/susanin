package ru.ioffe.school.susanin.mapGraph;

public class TransportEdge {
    private final Vertex from;
    private final Vertex to;
    private final int cost;

    /**
     * Constructs Edge with specific cost and
     * time required to go through it.
     *
     * @param from edge start
     * @param to   edge end
     * @param cost edge cost
     */
    public TransportEdge(Vertex from, Vertex to, int cost) {
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

    public double getTime(double currentTime) {
        return 3.0 + ((int) currentTime) % 5; //some magic time
    }
}
