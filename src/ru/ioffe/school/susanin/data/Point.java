package ru.ioffe.school.susanin.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the point on a map.
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 5889215924698852354L;

    private final long id;
    private final double lat;
    private final double lon;

    /**
     * Constructs Point with id and coordinates
     *
     * @param id  point id
     * @param lat point latitude
     * @param lon point longitude
     */
    public Point(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public Point(int id, int code, String name, double lat, double lon, String type) {
    } // stop have 3 new parameters

    /**
     * Returns id of this point.
     *
     * @return point id
     */
    public long getId() {
        return id;
    }

    /**
     * Returns latitude of this point.
     *
     * @return point latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * Returns longitude of this point.
     *
     * @return point longitude
     */
    public double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        return (id == ((Point) obj).id) &&
                (lat == ((Point) obj).lat) && (lon == ((Point) obj).lon);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
