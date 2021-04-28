package ru.ioffe.school.susanin.data;

import java.util.HashSet;

public class Route {

    private final int id;
    private final String name;
    private final String transportMean;
    private final HashSet<Stop> clockwise;
    private final HashSet<Stop> counterClockwise;

    public Route(int id, String name, String transportMean) {
        this.id = id;
        this.name = name;
        this.transportMean = transportMean;
        this.clockwise = new HashSet<>();
        this.counterClockwise = new HashSet<>();
    }

    public void setClockwise(HashSet<Stop> stops) {
        this.clockwise.addAll(stops);
    }

    public void setCounterClockwise(HashSet<Stop> stops) {
        this.counterClockwise.addAll(stops);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTransportMean() {
        return transportMean;
    }

    public HashSet<Stop> getCounterClockwise() {
        return counterClockwise;
    }

    @Override
    public String toString() {
        return "Routes{" + System.lineSeparator() +
                "\troute_id=" + id + System.lineSeparator() +
                "\troute_name='" + name + '\'' + System.lineSeparator() +
                "\troute_type='" + transportMean + '\'' + System.lineSeparator() +
                '}';
    }
}
