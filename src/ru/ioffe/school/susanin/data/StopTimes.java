package ru.ioffe.school.susanin.data;

public class StopTimes {
    private final int tripId;
    private final String arrival;//should be int
    private final String departure;//should be int
    private final int stopId;
    private final int stopSequence;


    public StopTimes(int tripId, String arrival, String departure, int stopId, int stopSequence) {
        this.tripId = tripId;
        this.arrival = arrival;
        this.departure = departure;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
    }

    public int getTripId() {
        return tripId;
    }

    public String getArrival() {
        return arrival;
    }//int

    public String getDeparture() {
        return departure;
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
                "\tarrival='" + arrival + '\'' + System.lineSeparator() +
                "\tdeparture='" + departure + '\'' + System.lineSeparator() +
                "\tstop_id='" + stopId + '\'' + System.lineSeparator() +
                "\tstop_sequence=" + stopSequence + System.lineSeparator() +
                '}';
    }
}
