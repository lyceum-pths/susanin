package ru.ioffe.school.susanin;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.debug.MapDrawer;
import ru.ioffe.school.susanin.mapParsing.Parser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\n\"y\" for yes \"n\" for no");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            File northPart = new File("C:\\Users\\Eugene\\Research\\osm\\north.xml");
            File southPart = new File("C:\\Users\\Eugene\\Research\\osm\\south.xml");
            File data = new File("data/map.data");
            Parser north = new Parser();
            Parser south = new Parser();
            north.parse(northPart);
            south.parse(southPart);
            HashMap<Long, Point> points = new HashMap<>();
            HashSet<Road> roads = new HashSet<>();
            points.putAll(north.getPointsCollection());
            points.putAll(south.getPointsCollection());
            roads.addAll(north.getRoadsCollection());
            roads.addAll(south.getRoadsCollection());
            try {
                Parser.saveData(points, roads, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MapDrawer mapDrawer = new MapDrawer(2560, 1920);
        try {
            mapDrawer.drawImage(new File("data\\map.data"));
            mapDrawer.saveImage(new File("C:\\Users\\Eugene\\Desktop\\spbfull_img.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
