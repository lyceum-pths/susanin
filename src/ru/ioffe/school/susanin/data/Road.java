package ru.ioffe.school.susanin.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a segment of road/rail/pedestrian path
 * and other objects you can move through.
 */
public class Road implements Serializable {

    private static final long serialVersionUID = 7821348647156236852L;
    private static final long DEFAULT_ROAD_ID = 1L;

    private final long id;
    private final double length;
    private final long from;
    private final long to;
    private final int speedLimit;
    private final boolean isOneway;
    //the key is a route name or number, value is the type of transport
    private final HashMap<String, String> transportMeans;

    /**
     * Constructs Road which is not in a map file
     * so has no specific id.
     *
     * @param length road length
     * @param speedLimit road speed restriction
     * @param from road start
     * @param to road end
     * @param isOneway true if road has a direction (from-&gt;to), false otherwise
     * @param transportMeans transport which goes through the road
     */
    public Road(double length, int speedLimit, long from, long to,
                boolean isOneway, HashMap<String, String> transportMeans) {
        this.id = DEFAULT_ROAD_ID;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashMap<>(transportMeans);
    }

    /**
     * Constructs Road which is contained
     * in public transport routes.
     *
     * @param id road id
     * @param length road length
     * @param speedLimit road speed restriction
     * @param from road start
     * @param to road end
     * @param isOneway true if road has a direction (from-&gt;to), false otherwise
     * @param transportMeans transport which goes through the road
     */
    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway, HashMap<String, String> transportMeans) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashMap<>(transportMeans);
        this.transportMeans.put("car", "car");
    }

    /**
     * Constructs Road which is not contained
     * in public transport routes.
     *
     * @param id road id
     * @param length road length
     * @param speedLimit road speed restriction
     * @param from id of road start point
     * @param to id of road end point
     * @param isOneway true if road has a direction (from-&gt;to), false otherwise
     * @param isPedestrian true if people can move through, false otherwise
     */
    public Road(long id, double length, int speedLimit, long from, long to,
                boolean isOneway, boolean isPedestrian) {
        this.id = id;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
        this.transportMeans = new HashMap<>();
        if (isPedestrian) {
            this.transportMeans.put("foot", "foot");
        } else {
            this.transportMeans.put("car", "car");
        }
    }

    /**
     * Returns id of this road.
     *
     * @return road id
     */
    public long getId() {
        return id;
    }

    /**
     * Returns id of this road start point.
     *
     * @return id of start point
     */
    public long getFrom() {
        return from;
    }

    /**
     * Returns id of this road end point.
     *
     * @return id of end point
     */
    public long getTo() {
        return to;
    }

    /**
     * Returns length of this road.
     *
     * @return road length
     */
    public double getLength() {
        return length;
    }

    /**
     * Returns speed restriction of this road.
     *
     * @return road speed restriction
     */
    public int getSpeedLimit() {
        return speedLimit;
    }

    /**
     * Returns a {@link java.util.HashMap} of transport which can be used to
     * move through this road.
     *
     * @return transport which can be used to move through this road
     */
    public HashMap<String, String> getTransportMeans() {
        return transportMeans;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Road)) {
            return false;
        }
        return ((id == ((Road) obj).id) && (from == ((Road) obj).from) && (to == ((Road) obj).to));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to);
    }
}
