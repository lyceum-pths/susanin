package ru.ioffe.school.susanin.data;

public class Routes {
    private final int id;
    private final String name;
    private final String type;
    public Routes(int id,  String name,  String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Routes{" + System.lineSeparator() +
                "\troute_id=" + id + System.lineSeparator() +
                "\troute_name='" + name + '\'' + System.lineSeparator() +
                "\troute_type='" + type + '\'' + System.lineSeparator() +
                '}';
    }
}
