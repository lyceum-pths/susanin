package ru.ioffe.school.susanin;

import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.csvParsing.CsvMapper;
import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Route;
import ru.ioffe.school.susanin.data.Stop;
import ru.ioffe.school.susanin.debug.MapDrawer;
import ru.ioffe.school.susanin.mapGraph.MapGraph;
import ru.ioffe.school.susanin.mapParsing.POIParser;
import ru.ioffe.school.susanin.mapParsing.Parser;
import ru.ioffe.school.susanin.navigator.Navigator;
import ru.ioffe.school.susanin.utils.DataSaver;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner in = new Scanner(System.in);
        /*
        System.out.println("Need to parse new file?\ntype \"y\" for yes\n" +
                "!!! Spb is parsed. Don't parse again !!!");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            try {
                HashSet<String> POI = new HashSet<>();
                HashMap<Long, Point> points = new HashMap<>();
                HashSet<Road> roads = new HashSet<>();
                HashMap<Long, Stop> stops = new HashMap<>();
                ArrayList<Route> routes = new ArrayList<>();

                Path POIs = Paths.get(MAP_RESOURCES_DIR + "preparse_final.xml");
                POIParser poiParser = new POIParser();
                poiParser.parse(POIs);
                POI.addAll(poiParser.getPOI());

                Path city = Paths.get(MAP_RESOURCES_DIR + "for_final.osm");
                Parser cityParser = new Parser();
                cityParser.parse(city, POI);
                points.putAll(cityParser.getPointsCollection());
                roads.addAll(cityParser.getRoadsCollection());

                CsvMapper csvMapper = new CsvMapper();
                csvMapper.createMapping();
                stops.putAll(csvMapper.getStops());
                routes.addAll(csvMapper.getRoutes());

                Path data = Paths.get("data\\map.data");
                DataSaver.saveData(points, roads, stops, routes, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         */

        System.out.println(">> LOADING DATA...");
        FileInputStream fis = new FileInputStream(Paths.get("data\\map.data").toFile());
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<Long, Point> points = (HashMap<Long, Point>) ois.readObject();
        HashSet<Road> roads = (HashSet<Road>) ois.readObject();
        HashMap<Long, Stop> stops = (HashMap<Long, Stop>) ois.readObject();
        ArrayList<Route> routes = (ArrayList<Route>) ois.readObject();
        System.out.println(">> BUILDING GRAPH...");
        MapGraph graph = new MapGraph(points, roads, stops, routes);
        double fromLat, fromLon, toLat, toLon;
        System.out.println(">> Enter start and destination coordinates:\n" +
                "(bounds: 59.9923 - 60.0209; 30.3305 : 30.4323)");
        System.out.println("Start:\n");
        fromLat = in.nextDouble();
        fromLon = in.nextDouble();
        System.out.println("Destination:\n");
        toLat = in.nextDouble();
        toLon = in.nextDouble();
        Navigator navigator = new Navigator(fromLat, fromLon, toLat, toLon,
                new ArrayList<Point>(points.values()));
        System.out.println("Time:" +
                (int) navigator.navigate(0, 100, graph));
        // drawMap();
    }

    /*
    private static void drawMap() throws IOException, ClassNotFoundException {
        MapDrawer fullMap = new MapDrawer(2048, 1536, 59.6254, 60.1613,
                29.6068, 30.7343, false);
        MapDrawer test = new MapDrawer(4096, 4096, 59.9923, 60.0209,
                30.3305, 30.4323, false);
        FileInputStream fis = new FileInputStream(Paths.get("data\\map.data").toFile());
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<Long, Point> points = (HashMap<Long, Point>) ois.readObject();
        HashSet<Road> roads = (HashSet<Road>) ois.readObject();
        HashMap<Long, Stop> stops = (HashMap<Long, Stop>) ois.readObject();
        fullMap.drawImage(points, roads, stops);
        fullMap.saveImage("spb_full");
        test.drawImage(points, roads, stops);
        test.saveImage("test");
    }
     */
}
