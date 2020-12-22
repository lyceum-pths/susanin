package ru.ioffe.school.susanin.data;

public class Stops {
    private final int id;
    private final int code;
    private final String name;
    private final double lat;
    private final double lon;
    private final String type;

    public Stops(int id, int code, String name, double lat, double lon, String type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }

    public int getStops_id() {
        return id;
    }

    public int getStops_code() {
        return code;
    }

    public String getStops_name() {
        return name;
    }

    public double getStops_lat() {
        return lat;
    }

    public double getStops_lon() {
        return lon;
    }

    public String getTransport_type() {
        return type;
    }

    @Override
    public String toString() {
        return "Stops{" + System.lineSeparator() +
                "\tstop_id=" + id + System.lineSeparator() +
                "\tstop_code='" + code + '\'' + System.lineSeparator() +
                "\tstop_name='" + name + '\'' + System.lineSeparator() +
                "\tstop_lat='" + lat + '\'' + System.lineSeparator() +
                "\tstop_lon=" + lon + System.lineSeparator() +
                "\ttransport_type='" + type + '\'' + System.lineSeparator() +
                '}';
    }
}
