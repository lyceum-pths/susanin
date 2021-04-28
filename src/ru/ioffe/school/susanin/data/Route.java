package ru.ioffe.school.susanin.data;

import java.util.ArrayList;

public class Route {

    private final int id;
    private final String name;
    private final String transportMean;
    private final ArrayList<Stop> stops;

    public Route(int id, String name, String transportMean) {
        this.id = id;
        this.name = name;
        this.transportMean = transportMean;
        this.stops = new ArrayList<>();
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops.addAll(stops);
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

    public ArrayList<Stop> getStops() {
        return stops;
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
