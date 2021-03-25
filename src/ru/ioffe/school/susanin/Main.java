package ru.ioffe.school.susanin;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.debug.MapDrawer;
import ru.ioffe.school.susanin.mapParsing.POIParser;
import ru.ioffe.school.susanin.mapParsing.Parser;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.util.HashMap;


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
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\ntype \"y\" for yes\n" +
                "!!! Spb is parsed. Don't parse again !!!");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            try {
                HashSet<String> POI = new HashSet<>();

                // Getting first set of POIs
                Path poiWest = Paths.get(MAP_RESOURCES_DIR + "preparse1.xml");
                POIParser poiParserWest = new POIParser();
                poiParserWest.parse(poiWest);
                POI.addAll(poiParserWest.getPOI());

                // Getting second set of POIs
                Path poiEast = Paths.get(MAP_RESOURCES_DIR + "preparse2.xml");
                POIParser poiParserEast = new POIParser();
                poiParserEast.parse(poiEast);
                POI.addAll(poiParserEast.getPOI());

                poiParserWest = null;
                poiParserEast = null;

                HashMap<Long, Point> points = new HashMap<>();
                HashSet<Road> roads = new HashSet<>();

                // Parsing first part of map and getting points and roads
                Path westPart = Paths.get(MAP_RESOURCES_DIR + "part1.xml");
                Parser parserWest = new Parser();
                parserWest.parse(westPart, POI);
                points.putAll(parserWest.getPointsCollection());
                roads.addAll(parserWest.getRoadsCollection());

                // Parsing second part of map and getting points and roads
                Path eastPart = Paths.get(MAP_RESOURCES_DIR + "part2.xml");
                Parser parserEast = new Parser();
                parserEast.parse(eastPart, POI);
                points.putAll(parserEast.getPointsCollection());
                roads.addAll(parserEast.getRoadsCollection());

                // Saving point and roads to a file
                Path data = Paths.get("data\\map.data");
                Parser.saveData(points, roads, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        MapDrawer mapDrawer = new MapDrawer(2048, 1080, 59.75, 60.13,
                29.6, 30.62, false);
        try {
            mapDrawer.drawImage(Paths.get("data\\map.data"));
            mapDrawer.saveImage("spb");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}
