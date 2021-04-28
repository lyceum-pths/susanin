package ru.ioffe.school.susanin.csvParsing;

public class Trip {
    private final int routeId;
    private final int tripId;

    public Trip(int routeId, int tripId) {
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
