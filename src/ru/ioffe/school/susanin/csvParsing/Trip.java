package ru.ioffe.school.susanin.csvParsing;

public class Trip {
    private final int routeId;
    private final int tripId;
    private final int direction;

    public Trip(int routeId, int tripId, int direction) {
        this.routeId = routeId;
        this.tripId = tripId;
        this.direction = direction;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getTripId() {
        return tripId;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Trips{" + System.lineSeparator() +
                "\troute_id=" + routeId + System.lineSeparator() +
                "\ttrip_id='" + tripId + '\'' + System.lineSeparator() +
                "\troute_id=" + direction + System.lineSeparator() +
                '}';
    }
}
