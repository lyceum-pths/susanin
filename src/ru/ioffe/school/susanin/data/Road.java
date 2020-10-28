package ru.ioffe.school.susanin.data;

public class Road extends Way {

    final String transportMean;
    final int routeNumber;

    public Road(double length, int speed, boolean isOneway, String transportMean, int routeNumber) {
        super(length, speed, isOneway);
        this.transportMean = transportMean;
        this.routeNumber = routeNumber;
    }
}
