package ru.ioffe.school.susanin.data;

public class House extends Point {

    final String street, number, name;

    public House(long id, double lat, double lon, String number, String street, String name) {
        super(id, lat, lon);
        this.number = number;
        this.street = street;
        this.name = name;
    }
}
