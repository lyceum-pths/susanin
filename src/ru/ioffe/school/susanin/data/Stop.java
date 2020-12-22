package ru.ioffe.school.susanin.data;

/**
 * Represents a public transport stop.
 */
public class Stop extends Point {

    private static final long serialVersionUID = 5689390855290524579L;

    private final String name;
    private final int code;
    private final String type;

    /**
     * Constructs Stop with specific parameters.
     *  @param id   stop id
     * @param lat  stop latitude
     * @param lon  stop longitude
     * @param name stop name
     * @param code
     */
    public Stop(long id, double lat, double lon, String name, int code) {
        super(id, lat, lon);
        this.name = name;
        this.code = 0;
        this.type = "0";
    }

    public String getStop_name() {
        return name;
    }


    public Stop(long id, int code, String name, double lat, double lon, String type) {
        super(id, lat, lon);
        this.code = code;
        this.name = name;
        this.type = type;
    }


    public int getStops_code() {
        return code;
    }

    public String getStops_name() {
        return name;
    }


    public String getTransport_type() {
        return type;
    }

    @Override
    public String toString() {
        return "Stops{" + System.lineSeparator() +
                "\tstop_code='" + code + '\'' + System.lineSeparator() +
                "\tstop_name='" + name + '\'' + System.lineSeparator() +
                "\ttransport_type='" + type + '\'' + System.lineSeparator() +
                '}';

    }

}
