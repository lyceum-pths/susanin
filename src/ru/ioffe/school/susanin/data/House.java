package ru.ioffe.school.susanin.data;

public class House extends Node {

    final int number;
    final String street, name;

    public House(long id, double lat, double lon, int number, String street, String name) {
        super(id, lat, lon);
        this.number = number;
        this.street = street;
        this.name = name;
    }
}
