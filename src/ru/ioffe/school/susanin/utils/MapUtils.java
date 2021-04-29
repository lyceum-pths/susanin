package ru.ioffe.school.susanin.utils;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Aggregates utilities needed during parsing.
 */
public class MapUtils {

    private static final double metersInLonDegree = 56000.0;
    private static final double metersInLatDegree = 111000.0;

    /**
     * Evaluates road segment length
     * from its ends coordinates.
     *
     * @param fromLat segment start latitude
     * @param fromLon segment start longitude
     * @param toLat segment end latitude
     * @param toLon segment end longitude
     * @return length of road segment
     */
    public static double calculateLength(double fromLat, double fromLon, double toLat, double toLon) {
        return Math.sqrt((toLat - fromLat) * (toLat - fromLat) * metersInLatDegree * metersInLatDegree +
                (toLon - fromLon) * (toLon - fromLon) * metersInLonDegree * metersInLonDegree);
    }

    /**
     * Finds closest point on a map
     * to a current point.
     *
     * @param lat current point latitude
     * @param lon current point longitude
     * @param points map points list
     * @return closest point
     */
    public static Point getClosestPoint(double lat, double lon, ArrayList<Point> points) {
        double minDist = 10000000.0;
        Point closest = points.get(0);
        for (Point point : points) {
            double dist = calculateLength(lat, lon, point.getLat(), point.getLon());
            if (dist <= minDist) {
                minDist = dist;
                closest = point;
            }
        }
        return closest;
    }

    /**
     * Makes road segment which is not in map file.
     *
     * @param length length of segment
     * @param speedLimit segment speed restriction
     * @param from segment start
     * @param to segment end
     * @param isOneway true if road has a direction (from-&gt;to), false otherwise
     * @param transportMeans transport which goes through segment
     * @return new road segment
     */
    public static Road makeFakeRoad(double length, int speedLimit, long from, long to,
                                    boolean isOneway, HashMap<String, String> transportMeans) {
        return new Road(length, speedLimit, from, to, isOneway, transportMeans);
    }
}
