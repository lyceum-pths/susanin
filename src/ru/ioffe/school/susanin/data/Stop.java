package ru.ioffe.school.susanin.data;

public class Stop extends Point {

    private static final long serialVersionUID = 5689390855290524579L;

    private final String name;

    public Stop(long id, double lat, double lon, String name) {
        super(id, lat, lon);
        this.name = name;
    }
}
