package ru.ioffe.school.susanin.data;

public abstract class Point {
    final long id;
    final double lat, lon;
    public Point(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
