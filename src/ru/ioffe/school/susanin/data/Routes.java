package ru.ioffe.school.susanin.data;

public class Routes {
    private final int route_id;
    private final String route_name;
    private final String route_type;
    public Routes(int route_id,  String route_name,  String route_type) {
        this.route_id = route_id;
        this.route_name = route_name;
        this.route_type = route_type;
    }

    public int getRoute_id() {
        return route_id;
    }


    public String getRoute_name() {
        return route_name;
    }


    public String getRoute_type() {
        return route_type;
    }

    @Override
    public String toString() {
        return "Routes{" + System.lineSeparator() +
                "\troute_id=" + route_id + System.lineSeparator() +
                "\troute_name='" + route_name + '\'' + System.lineSeparator() +
                "\troute_type='" + route_type + '\'' + System.lineSeparator() +
                '}';
    }
}
