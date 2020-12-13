package ru.ioffe.school.susanin.utils;

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
    public static double calcLength(double fromLat, double fromLon, double toLat, double toLon) {
        return Math.sqrt((toLat - fromLat) * (toLat - fromLat) * metersInLatDegree * metersInLatDegree +
                (toLon - fromLon) * (toLon - fromLon) * metersInLonDegree * metersInLonDegree);
    }
}
