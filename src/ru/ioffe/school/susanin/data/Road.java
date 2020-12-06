package ru.ioffe.school.susanin.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Road implements Serializable {

    private static final long serialVersionUID = 7821348647156236852L;
    private static final long dummyRoadId = 1L;

    private final long id;
    private final double length;
    private final long from;
    private final long to;
    private final int speedLimit;
    private final boolean isOneway;
    private final HashMap<String, String> transportMeans;

    public Road(double length, int speedLimit, long from, long to,
                boolean isOneway, HashMap<String, String> transportMeans) {
        this.id = dummyRoadId;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashMap<>(transportMeans);
    }

    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway, HashMap<String, String> transportMeans) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashMap<>(transportMeans);
    }
    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway, boolean isPedestrian) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashMap<>();
        if (isPedestrian) {
            this.transportMeans.put("foot", "foot");
        } else {
            this.transportMeans.put("car", "car");
        }
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public double getLength() {
        return length;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public HashMap<String, String> getTransportMeans() {
        return transportMeans;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Road)) {
            return false;
        }
        return ((id == ((Road) obj).id) && (from == ((Road) obj).from) && (to == ((Road) obj).to));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to);
    }
}
