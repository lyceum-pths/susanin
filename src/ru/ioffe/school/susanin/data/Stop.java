package ru.ioffe.school.susanin.data;

public class Stop extends Point {

    final String name;

    public Stop(int id, double lat, double lon, String name) {
        super(id, lat, lon);
        this.name = name;
    }
}
