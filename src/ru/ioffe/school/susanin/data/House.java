package ru.ioffe.school.susanin.data;

/**
 * Represents a building as a type of point.
 */
public class House extends Point {

    private static final long serialVersionUID = 3741894312276648488L;

    private final String street;
    private final String number;
    private final String name;

    /**
     * Constructs House with specific parameters.
     *
     * @param id building id
     * @param lat building latitude
     * @param lon building longitude
     * @param number number in address
     * @param street street in address
     * @param name building name
     */
    public House(long id, double lat, double lon, String number, String street, String name) {
        super(id, lat, lon);
        this.number = number;
        this.street = street;
        this.name = name;
    }
}
