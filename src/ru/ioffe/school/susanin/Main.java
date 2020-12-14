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

/**
 * Main program class.
 */
public class Main {

    // Directory there all map files are stored
    private static final String MAP_RESOURCES_DIR = "C:\\Users\\Eugene\\Research\\osm\\";

    /**
     * Main program method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\ntype \"y\" for yes");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            try {
                HashSet<String> POI = new HashSet<>();

                // Getting first set of POIs
                File poi1 = new File(MAP_RESOURCES_DIR + "preparse1.xml");
                POIParser poiParser1 = new POIParser();
                poiParser1.parse(poi1);
                POI.addAll(poiParser1.getPOI());

                // Getting second set of POIs
                File poi2 = new File(MAP_RESOURCES_DIR + "preparse2.xml");
                POIParser poiParser2 = new POIParser();
                poiParser2.parse(poi2);
                POI.addAll(poiParser2.getPOI());

                poiParser1 = null;
                poiParser2 = null;

                HashMap<Long, Point> points = new HashMap<>();
                HashSet<Road> roads = new HashSet<>();

                // Parsing first part of map and getting points and roads
                File part1 = new File(MAP_RESOURCES_DIR + "part1.xml");
                Parser parser1 = new Parser();
                parser1.parse(part1, POI);
                points.putAll(parser1.getPointsCollection());
                roads.addAll(parser1.getRoadsCollection());

                // Parsing second part of map and getting points and roads
                File part2 = new File(MAP_RESOURCES_DIR + "part2.xml");
                Parser parser2 = new Parser();
                parser2.parse(part2, POI);
                points.putAll(parser2.getPointsCollection());
                roads.addAll(parser2.getRoadsCollection());

                // Saving point and roads to a file
                File data = new File("data\\map.data");
                Parser.saveData(points, roads, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MapDrawer mapDrawer = new MapDrawer(2048, 1080, 59.75, 60.13,
                29.6, 30.62, false);
        try {
            mapDrawer.drawImage(new File("data\\map.data"));
            mapDrawer.saveImage(new File("map_images\\spb.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
