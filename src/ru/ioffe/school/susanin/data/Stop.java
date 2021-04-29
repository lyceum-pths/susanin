package ru.ioffe.school.susanin.data;

import java.util.ArrayList;

/**
 * Represents a public transport stop.
 */
public class Stop extends Point {

    private static final long serialVersionUID = 5689390855290524579L;

    private final String name;
    private final String transportMean;
    // stop times in minutes from 00:00
    private final ArrayList<Integer> stopTimes;

    /**
     * Constructs Stop with specific parameters.
     *
     * @param id   stop id
     * @param name stop name
     * @param lat  stop latitude
     * @param lon  stop longitude
     * @param transportMean type of transport which stops
     */
    public Stop(long id, String name, double lat, double lon, String transportMean) {
        super(id, lat, lon);
        this.name = name;
        this.transportMean = transportMean;
        this.stopTimes = new ArrayList<>();
    }

    public void addStopTime(Integer stopTime) {
        this.stopTimes.add(stopTime);
    }

    public String getName() {
        return name;
    }


    public String getTransportMean() {
        return transportMean;
    }

    public ArrayList<Integer> getStopTimes() {
        return stopTimes;
    }
}
