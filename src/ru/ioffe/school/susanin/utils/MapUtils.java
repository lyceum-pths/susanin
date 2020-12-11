package ru.ioffe.school.susanin.utils;

public class MapUtils {

    private static final double metersInLonDegree = 56000.0;
    private static final double metersInLatDegree = 111000.0;

    public static double calcLength(double fromLat, double fromLon, double toLat, double toLon) {
        return Math.sqrt((toLat - fromLat) * (toLat - fromLat) * metersInLatDegree * metersInLatDegree +
                (toLon - fromLon) * (toLon - fromLon) * metersInLonDegree * metersInLonDegree);
    }
}
