package ru.ioffe.school.susanin.data;

import java.util.Objects;

public class Road {

    final long id;
    final double length;
    final long from, to;
    private int speedLimit;
    private boolean isOneway;
    private String transportMean;
    private int routeNumber;

    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMean = "foot";
        this.routeNumber = 0;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public void setOneway(boolean isOneway) {
        this.isOneway = isOneway;
    }

    public void setTransport(String transportMean, int routeNumber) {
        this.transportMean = transportMean;
        this.routeNumber = routeNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Road)) {
            return false;
        }
        return ((id == ((Road) obj).id) && (from == ((Road) obj).from) && (to == ((Road) obj).to)
                && (transportMean == ((Road) obj).transportMean) && (routeNumber == ((Road) obj).routeNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, transportMean, routeNumber);
    }
}
