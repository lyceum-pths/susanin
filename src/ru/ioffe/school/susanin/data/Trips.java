package ru.ioffe.school.susanin.data;

public class Trips {
    private final int route_id;
    private final int trip_id;

    public Trips(int route_id, int trip_id) {
        this.route_id = route_id;
        this.trip_id = trip_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    @Override
    public String toString() {
        return "Trips{" + System.lineSeparator() +
                "\troute_id=" + route_id + System.lineSeparator() +
                "\ttrip_id='" + trip_id + '\'' + System.lineSeparator() +
                '}';
    }
}
