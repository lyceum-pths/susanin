package ru.ioffe.school.susanin.data;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class Route implements Serializable {

    private static final long serialVersionUID = 1606460755096526779L;

    private final int id;
    private final String name;
    private final String transportMean;
    private final LinkedHashSet<Stop> clockwise;
    private final LinkedHashSet<Stop> counterClockwise;

    public Route(int id, String name, String transportMean) {
        this.id = id;
        this.name = name;
        this.transportMean = transportMean;
        this.clockwise = new LinkedHashSet<>();
        this.counterClockwise = new LinkedHashSet<>();
    }

    public void setClockwise(LinkedHashSet<Stop> stops) {
        this.clockwise.addAll(stops);
    }

    public void setCounterClockwise(LinkedHashSet<Stop> stops) {
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

    public LinkedHashSet<Stop> getClockwise() {
        return clockwise;
    }

    public LinkedHashSet<Stop> getCounterClockwise() {
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
