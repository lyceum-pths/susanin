package ru.ioffe.school.susanin;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
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
    private static final String MAP_RESOURCES_DIR = "D:\\osm\\";

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

                Path pois = Paths.get(MAP_RESOURCES_DIR + "preparsefinal.xml");
                POIParser poiParser = new POIParser();
                poiParser.parse(pois);
                POI.addAll(poiParser.getPOI());

                // Getting first set of POIs
                /*
                Path poiWest = Paths.get(MAP_RESOURCES_DIR + "preparse1.xml");
                POIParser poiParserWest = new POIParser();
                poiParserWest.parse(poiWest);
                POI.addAll(poiParserWest.getPOI());
                 */

                // Getting second set of POIs
                /*
                Path poiEast = Paths.get(MAP_RESOURCES_DIR + "preparse2.xml");
                POIParser poiParserEast = new POIParser();
                poiParserEast.parse(poiEast);
                POI.addAll(poiParserEast.getPOI());
                 */

                //poiParserWest = null;
                //poiParserEast = null;
                poiParser = null;

                HashMap<Long, Point> points = new HashMap<>();
                HashSet<Road> roads = new HashSet<>();

                Path city = Paths.get(MAP_RESOURCES_DIR + "final.xml");
                Parser cityParser = new Parser();
                cityParser.parse(city, POI);
                points.putAll(cityParser.getPointsCollection());
                roads.addAll(cityParser.getRoadsCollection());

                // Parsing first part of map and getting points and roads
                /*
                Path westPart = Paths.get(MAP_RESOURCES_DIR + "part1.xml");
                Parser parserWest = new Parser();
                parserWest.parse(westPart, POI);
                points.putAll(parserWest.getPointsCollection());
                roads.addAll(parserWest.getRoadsCollection());
                 */

                // Parsing second part of map and getting points and roads
                /*
                Path eastPart = Paths.get(MAP_RESOURCES_DIR + "part2.xml");
                Parser parserEast = new Parser();
                parserEast.parse(eastPart, POI);
                points.putAll(parserEast.getPointsCollection());
                roads.addAll(parserEast.getRoadsCollection());
                 */

                // Saving point and roads to a file
                Path data = Paths.get("data\\map.data");
                Parser.saveData(points, roads, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MapDrawer mapDrawer = new MapDrawer(2048, 1536, 59.6254, 60.1613,
                29.6068, 30.7343, false);
        try {
            mapDrawer.drawImage(Paths.get("data\\map.data"));
            mapDrawer.saveImage("spb");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
