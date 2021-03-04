package ru.ioffe.school.susanin.data;

public class Trips {
    private final int routeId;
    private final int tripId;

    public Trips(int routeId, int tripId) {
        this.routeId = routeId;
        this.tripId = tripId;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getTripId() {
        return tripId;
    }

    @Override
    public String toString() {
        return "Trips{" + System.lineSeparator() +
                "\troute_id=" + routeId + System.lineSeparator() +
                "\ttrip_id='" + tripId + '\'' + System.lineSeparator() +
                '}';
    }
}
