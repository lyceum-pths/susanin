package ru.ioffe.school.susanin.data;


public class StopTimes {
    private final int trip_id;
    private final String arrival;//should be int
    private final String departure;//should be int
    private final int stop_id;
    private final int stop_sequence;


    public StopTimes(int trip_id, String arrival, String departure, int stop_id, int stop_sequence) {
        this.trip_id = trip_id;
        this.arrival = arrival;
        this.departure = departure;
        this.stop_id = stop_id;
        this.stop_sequence = stop_sequence;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public String getArrival() {
        return arrival;
    }//int

    public String getDeparture() {
        return departure;
    }//int

    public int getStop_id() {
        return stop_id;
    }

    public int getStop_sequence() {
        return stop_sequence;
    }


    @Override
    public String toString() {
        return "Stops{" + System.lineSeparator() +
                "\ttrip_id=" + trip_id + System.lineSeparator() +
                "\tarrival='" + arrival + '\'' + System.lineSeparator() +
                "\tdeparture='" + departure + '\'' + System.lineSeparator() +
                "\tstop_id='" + stop_id + '\'' + System.lineSeparator() +
                "\tstop_sequence=" + stop_sequence + System.lineSeparator() +
                '}';
    }
}
