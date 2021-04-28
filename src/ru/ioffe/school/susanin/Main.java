package ru.ioffe.school.susanin;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import ru.ioffe.school.susanin.csvParsing.CsvMapper;
import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.debug.MapDrawer;
import ru.ioffe.school.susanin.mapParsing.POIParser;
import ru.ioffe.school.susanin.mapParsing.Parser;


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
    public static void main(String[] args) throws IOException, CsvValidationException {

        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\ntype \"y\" for yes\n" +
                "!!! Spb is parsed. Don't parse again !!!");
        String choice = in.nextLine();
        HashMap<Long, Point> points = new HashMap<>();
        HashSet<Road> roads = new HashSet<>();
        if (choice.equals("y")) {
            try {
                HashSet<String> POI = new HashSet<>();

                Path pois = Paths.get(MAP_RESOURCES_DIR + "preparsefinal.xml");
                POIParser poiParser = new POIParser();
                poiParser.parse(pois);
                POI.addAll(poiParser.getPOI());

                Path city = Paths.get(MAP_RESOURCES_DIR + "final.xml");
                Parser cityParser = new Parser();
                cityParser.parse(city, POI);
                points.putAll(cityParser.getPointsCollection());
                roads.addAll(cityParser.getRoadsCollection());

                Path data = Paths.get("data\\map.data");
                Parser.saveData(points, roads, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvMapper.createMapping();

        MapDrawer fullMap = new MapDrawer(2048, 1536, 59.6254, 60.1613,
                29.6068, 30.7343, false);
        MapDrawer center = new MapDrawer(4096, 3072, 59.9034, 59.9617,
                30.2392, 30.4187, false);
        try {
            FileInputStream fis = new FileInputStream(Paths.get("data\\map.data").toFile());
            ObjectInputStream ois = new ObjectInputStream(fis);
            points = (HashMap<Long, Point>) ois.readObject();
            roads = (HashSet<Road>) ois.readObject();
            fullMap.drawImage(points, roads);
            fullMap.saveImage("spb_full");
            center.drawImage(points, roads);
            center.saveImage("spb_center");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
