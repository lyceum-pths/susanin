package ru.ioffe.school.susanin.data;

public abstract class Way {

    final double length;
    final int speed;
    final boolean isOneway;

    public Way(double length, int speed, boolean isOneway) {
        this.length = length;
        this.speed = speed;
        this.isOneway = isOneway;
    }
}