package ru.ioffe.school.susanin.data;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Road implements Serializable {

    private static final long serialVersionUID = 7821348647156236852L;
    private final long id;
    private final double length;
    private final long from, to;
    private int speedLimit;
    private boolean isOneway;
    private HashSet<Pair<String, String>> transportMeans;

    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway, HashSet<Pair<String, String>> transportMeans) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashSet<>();
        this.transportMeans = transportMeans;
    }
    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway, boolean isPedestrian) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashSet<>();
        if (isPedestrian) {
            this.transportMeans.add(new Pair<>("foot", "foot"));
        } else {
            this.transportMeans.add(new Pair<>("car", "car"));
        }
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public void setOneway(boolean isOneway) {
        this.isOneway = isOneway;
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
