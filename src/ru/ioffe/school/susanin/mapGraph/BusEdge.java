package ru.ioffe.school.susanin.mapGraph;

public class BusEdge {
    private final Vertex from;
    private final Vertex to;
    private final int cost;
    private final boolean isScheduled;

    /**
     * Constructs Edge with specific cost and
     * time required to go through it.
     *
     * @param from        edge start
     * @param to          edge end
     * @param cost        edge cost
     * @param isScheduled true if transport has schedule, false otherwise
     */
    public BusEdge(Vertex from, Vertex to, int cost, boolean isScheduled) {
        this.from = from;
        this.to = to;
        this.cost = cost;
        this.isScheduled = isScheduled;
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

    public boolean isScheduled() {
        return isScheduled;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
