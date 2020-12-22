package ru.ioffe.school.susanin.data;

/**
 * Represents a public transport stop.
 */
public class Stop extends Point {

    private static final long serialVersionUID = 5689390855290524579L;

    private final String name;

    /**
     * Constructs Stop with specific parameters.
     *
     * @param id stop id
     * @param lat stop latitude
     * @param lon stop longitude
     * @param name stop name
     */
    public Stop(long id, double lat, double lon, String name) {
        super(id, lat, lon);
        this.name = name;
    }

    public String getStop_name() {
        return name;
    }

}
