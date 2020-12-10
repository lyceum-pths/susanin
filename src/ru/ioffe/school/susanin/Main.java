package ru.ioffe.school.susanin;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.debug.MapDrawer;
import ru.ioffe.school.susanin.mapParsing.POIParser;
import ru.ioffe.school.susanin.mapParsing.Parser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\n\"y\" for yes \"n\" for no");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            try {
                File poi1 = new File("C:\\Users\\Eugene\\Research\\osm\\preparse1.xml");
                File poi2 = new File("C:\\Users\\Eugene\\Research\\osm\\preparse2.xml");
                File part1 = new File("C:\\Users\\Eugene\\Research\\osm\\part1.xml");
                File part2 = new File("C:\\Users\\Eugene\\Research\\osm\\part2.xml");
                File data = new File("data/map.data");
                POIParser poiParser1 = new POIParser();
                POIParser poiParser2 = new POIParser();
                poiParser1.parse(poi1);
                poiParser2.parse(poi2);
                HashSet<String> POI = new HashSet<>();
                POI.addAll(poiParser1.getPOI());
                POI.addAll(poiParser2.getPOI());
                poiParser1 = null;
                poiParser2 = null;
                Parser north = new Parser();
                Parser south = new Parser();
                north.parse(part1, POI);
                south.parse(part2, POI);
                HashMap<Long, Point> points = new HashMap<>();
                HashSet<Road> roads = new HashSet<>();
                points.putAll(north.getPointsCollection());
                points.putAll(south.getPointsCollection());
                roads.addAll(north.getRoadsCollection());
                roads.addAll(south.getRoadsCollection());
                Parser.saveData(points, roads, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
        MapDrawer mapDrawer = new MapDrawer(3072, 1920, 59.75, 60.13,
                29.6, 30.62, true);
        try {
            mapDrawer.drawImage(new File("data\\map.data"));
            mapDrawer.saveImage(new File("C:\\Users\\Eugene\\Desktop\\spbfullBW_img.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
        MapDrawer mapDrawer = new MapDrawer(3840, 1280, 59.91, 59.98,
                30.1, 30.6, true);
        try {
            mapDrawer.drawImage(new File("data\\map.data"));
            mapDrawer.saveImage(new File("C:\\Users\\Eugene\\Desktop\\centerBW_img.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
