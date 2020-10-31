package ru.ioffe.school.susanin.data;

import java.util.Objects;

public class Point {

    final long id;
    final double lat, lon;

    public Point(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        return id == ((Point) obj).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lon);
    }
}
