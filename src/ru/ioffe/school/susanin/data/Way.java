package ru.ioffe.school.susanin.data;

public abstract class Way {

    final long id;
    final double length;
    final int speed;
    final long from, to;
    final boolean isOneway;

    public Way(long id, double length, int speed, long from, long to, boolean isOneway) {
        this.id = id;
        this.length = length;
        this.speed = speed;
        this.from = from;
        this.to = to;
        this.isOneway = isOneway;
    }
}