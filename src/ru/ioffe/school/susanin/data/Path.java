package ru.ioffe.school.susanin.data;

public class Path extends Way {

    private static final int walkSpeed = 10;

    public Path(double length) {
        super(length, walkSpeed, false);
    }
}
