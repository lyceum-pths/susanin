package ru.ioffe.school.susanin.data;

import java.util.ArrayList;

/**
 * Represents a public transport stop.
 */
public class Stop extends Point {

    private static final long serialVersionUID = 5689390855290524579L;

    private final String name;
    private final int code;
    private final String transportMean;
    private final ArrayList<String> stopTimes;

    /**
     * Constructs Stop with specific parameters.
     *
     * @param id   stop id
     * @param lat  stop latitude
     * @param lon  stop longitude
     * @param name stop name
     */
    public Stop(long id, double lat, double lon, String name) {
        super(id, lat, lon);
        this.name = name;
        this.code = 0;
        this.transportMean = "0";
        this.stopTimes = new ArrayList<>();
    }

    public Stop(long id, int code, String name, double lat, double lon, String transportMean) {
        super(id, lat, lon);
        this.code = code;
        this.name = name;
        this.transportMean = transportMean;
        this.stopTimes = new ArrayList<>();
    }

    public void addStopTime(String stopTime) {
        this.stopTimes.add(stopTime);
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getTransportMean() {
        return transportMean;
    }

    public ArrayList<String> getStopTimes() {
        return stopTimes;
    }

    @Override
    public String toString() {
        return "Stops{" + System.lineSeparator() +
                "\tstop_code='" + code + '\'' + System.lineSeparator() +
                "\tstop_name='" + name + '\'' + System.lineSeparator() +
                "\ttransport_type='" + transportMean + '\'' + System.lineSeparator() +
                '}';

    }

}
