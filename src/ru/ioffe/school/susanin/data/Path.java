package ru.ioffe.school.susanin.data;

public class Path extends Way {

    private static final int walkSpeed = 5;

    public Path(double length) {
        super(length, walkSpeed, false);
    }
}
