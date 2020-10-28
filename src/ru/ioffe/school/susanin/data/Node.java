package ru.ioffe.school.susanin.data;

public abstract class Node {
    final long id;
    final double lat, lon;
    public Node(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
