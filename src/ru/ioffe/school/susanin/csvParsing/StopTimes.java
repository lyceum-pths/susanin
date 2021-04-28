package ru.ioffe.school.susanin.csvParsing;

public class StopTimes {
    private final int tripId;
    private final String time;//should be int
    private final int stopId;
    private final int stopSequence;


    public StopTimes(int tripId, String time, int stopId, int stopSequence) {
        this.tripId = tripId;
        this.time = time;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
    }

    public int getTripId() {
        return tripId;
    }

    public String getTime() {
        return time;
    }//int

    public int getStopId() {
        return stopId;
    }

    public int getStopSequence() {
        return stopSequence;
    }


    @Override
    public String toString() {
        return "Stops{" + System.lineSeparator() +
                "\ttrip_id=" + tripId + System.lineSeparator() +
                "\tarrival='" + time + '\'' + System.lineSeparator() +
                "\tstop_id='" + stopId + '\'' + System.lineSeparator() +
                "\tstop_sequence=" + stopSequence + System.lineSeparator() +
                '}';
    }
}
