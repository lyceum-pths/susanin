package ru.ioffe.school.susanin.data;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

    private static final long serialVersionUID = 5889215924698852354L;
    private final long id;
    private final double lat, lon;

    public Point(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public Pair<Double, Double> getCoordinate() {
        return new Pair<>(lat, lon);
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
