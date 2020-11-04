package ru.ioffe.school.susanin.data;

public class Path extends Way {

    final int walkSpeed = 5;

    public Path(long id, double length, long from, long to) {
        super(id, length, 5, from, to, false);
    }
}
