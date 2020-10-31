package ru.ioffe.school.susanin.data;

public class Stop extends Point {

    final String name;

    public Stop(long id, double lat, double lon, String name) {
        super(id, lat, lon);
        this.name = name;
    }
}
