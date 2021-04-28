package ru.ioffe.school.susanin.mapParsing;

import com.sun.jdi.connect.Transport;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import ru.ioffe.school.susanin.data.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ru.ioffe.school.susanin.utils.MapUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * Parses the map into collections of points and roads
 * and saves the parsing result to a file.
 */
public class Parser {

    private static final Map<String, Integer> DEFAULT_SPEED_LIMITS = Map.of(
            "pedestrian", 5, "railway", 50, "urban", 60, "rural", 90,
            "highway", 110);
    private static final List<String> roadTypes = Arrays.asList(
            "motorway", "trunk", "primary", "secondary", "tertiary", "unclassified", "residential",
            "motorway_link", "trunk_link", "primary_link", "secondary_link", "tertiary_link",
            "service", "living_street", "footway", "pedestrian", "path", "steps", "track");
    private static final List<String> pedestrianTypes = Arrays.asList(
            "service", "footway", "pedestrian", "path", "steps", "track");
    private static final List<String> multiAccessTypes = Arrays.asList(
            "service", "track");

    private static final List<String> hugeRoads = Arrays.asList(
            "motorway", "trunk", "primary", "motorway_link", "trunk_link", "primary_link");
    private static final List<String> bigRoads = Arrays.asList(
            "tertiary", "unclassified", "tertiary_link");
    private static final List<String> middleRoads = Arrays.asList(
            "residential", "track", "service", "living_street");
    private static final List<String> smallRoads = Arrays.asList(
            "footway", "pedestrian", "path", "steps");

    private enum ObjectToParse {

        POINT,
        HOUSE,
        STOP
    }

    private final HashMap<String, Integer> speedLimits;
    private final HashMap<Long, Point> pointsCollection;
    private final HashSet<Road> roadsCollection;

    private static class RoadState {

        boolean isRoad;
        boolean isOneway;
        HashMap<String, String> transportMeans;
        int speed;

        RoadState(boolean isRoad, boolean isOneway,
                  HashMap<String, String> transportMeans, int speed) {
            this.isRoad = isRoad;
            this.isOneway = isOneway;
            this.transportMeans = new HashMap<>(transportMeans);
            this.speed = speed;
        }
    }

    /**
     * Constructs a Parser with specific road speed parameters.
     *
     * @param speedLimits a map of road types and speeds for them
     */
    public Parser(Map<String, Integer> speedLimits) {
        this.speedLimits = new HashMap<>(speedLimits);
        this.pointsCollection = new HashMap<>();
        this.roadsCollection = new HashSet<>();
    }

    /**
     * Constructs a Parser with default road speed parameters.
     */
    public Parser() {
        this.speedLimits = new HashMap<>(DEFAULT_SPEED_LIMITS);
        this.pointsCollection = new HashMap<>();
        this.roadsCollection = new HashSet<>();
    }

    /**
     * Creates a {@link org.w3c.dom.Document} from map file
     * and initiates map parsing sequence.
     *
     * @param mapPath path to map file to parse
     * @param POI     points that are needed during parsing
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public void parse(Path mapPath, Set<String> POI) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        dBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException e) {
            }

            @Override
            public void error(SAXParseException e) {
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXParseException {
                throw e;
            }
        });
        Document doc = dBuilder.parse(mapPath.toFile());
        doc.getDocumentElement().normalize();
        parsePoints(doc, POI);
        parseRoutes(doc);
        System.out.println("< PARSED >");
    }

    private void parsePoints(Document doc, Set<String> POI) {
        for (String pointId : POI) {
            ObjectToParse objectToParse = ObjectToParse.POINT;
            Element point = doc.getElementById(pointId);
            if (point != null && point.getTagName().equals("node")) {
                long id = Long.parseLong(point.getAttribute("id"));
                double lat = Double.parseDouble(point.getAttribute("lat"));
                double lon = Double.parseDouble(point.getAttribute("lon"));
                String name = null, street = null, number = null;
                NodeList properties = point.getElementsByTagName("tag");
                for (int j = 0; j < properties.getLength(); j++) {
                    Element property = (Element) properties.item(j);
                    String key = property.getAttribute("k");
                    String value = property.getAttribute("v");
                    if ((key.equals("public_transport") && (value.equals("stop_position") ||
                            value.equals("station"))) || key.equals("building")) {
                        if (key.equals("public_transport")) {
                            objectToParse = ObjectToParse.STOP;
                        } else {
                            objectToParse = ObjectToParse.HOUSE;
                        }
                        for (j = 0; j < properties.getLength(); j++) {
                            property = (Element) properties.item(j);
                            key = property.getAttribute("k");
                            switch (key) {
                                case "name":
                                    name = property.getAttribute("v");
                                    break;
                                case "addr:street":
                                    street = property.getAttribute("v");
                                    break;
                                case "addr:housenumber":
                                    number = property.getAttribute("v");
                                    break;
                            }
                        }
                    }
                }
                switch (objectToParse) {
                    case POINT:
                        pointsCollection.put(id, new Point(id, lat, lon));
                        break;
                    case STOP:
                        pointsCollection.put(id, new Stop(id, lat, lon, name));
                        break;
                    case HOUSE:
                        pointsCollection.put(id, new House(id, lat, lon, number, street, name));
                        break;
                }
            }
        }
    }

    private RoadState parseRoad(Element road) {
        boolean isRoad = false;
        boolean isOneway = false;
        int speed = speedLimits.get("pedestrian");
        HashMap<String, String> transportMeans = new HashMap<>();
        NodeList properties = road.getElementsByTagName("tag");
        for (int j = 0; j < properties.getLength(); j++) {
            Element property = (Element) properties.item(j);
            String key = property.getAttribute("k");
            String value = property.getAttribute("v");
            if (key.equals("highway")) {
                if (roadTypes.contains(value)) {
                    isRoad = true;
                    if (multiAccessTypes.contains(value)) {
                        transportMeans.put("foot", "foot");
                        transportMeans.put("car", "car");
                    } else if (pedestrianTypes.contains(value)) {
                        transportMeans.put("foot", "foot");
                    } else if (roadTypes.contains(value)) {
                        transportMeans.put("car", "car");
                    }
                    for (j = 0; j < properties.getLength(); j++) {
                        property = (Element) properties.item(j);
                        key = property.getAttribute("k");
                        value = property.getAttribute("v");
                        if (key.equals("maxspeed")) {
                            switch (value) {
                                case "RU:urban":
                                    speed = speedLimits.get("urban");
                                    break;
                                case "RU:rural":
                                    speed = speedLimits.get("rural");
                                    break;
                                case "RU:motorway":
                                    speed = speedLimits.get("highway");
                                    break;
                            }
                        } else if (key.equals("oneway") && value.equals("yes")) {
                            isOneway = true;
                        }
                    }
                }
            } else if (key.equals("railway")) {
                // That's because subway data in .osm is incorrect
                //transportMeans.put("train", "train");
                if (value.equals("subway")) {
                    break;
                }
                speed = speedLimits.get("railway");
                isRoad = true;
                for (j = 0; j < properties.getLength(); j++) {
                    property = (Element) properties.item(j);
                    key = property.getAttribute("k");
                    value = property.getAttribute("v");
                    if (key.equals("oneway") && value.equals("yes")) {
                        isOneway = true;
                    }
                }
            }
        }
        return new RoadState(isRoad, isOneway, transportMeans, speed);
    }

    private void parseRoads(Document doc, HashMap<Long, HashMap<String, String>> usedRoads) {
        boolean isRoad, isOneway;
        double length;
        long roadId, from, to;
        int speed;
        RoadState roadState;
        NodeList roads = doc.getElementsByTagName("way");
        int roadsLength = roads.getLength();
        for (int i = 0; i < roadsLength; i++) {
            Element road = (Element) roads.item(i);
            roadId = Long.parseLong(road.getAttribute("id"));
            if (roadId == 96700760) {
                System.out.println("stop");
            }
            roadState = parseRoad(road);
            isRoad = roadState.isRoad;
            isOneway = roadState.isOneway;
            HashMap<String, String> transportMeans = new HashMap<>(roadState.transportMeans);
            speed = roadState.speed;
            length = 0.0;
            if (isRoad) {
                NodeList refs = road.getElementsByTagName("nd");
                int refsLength = refs.getLength();
                from = Long.parseLong(((Element) refs.item(0)).getAttribute("ref"));
                double prevLat = Double.parseDouble(doc.getElementById(Long.toString(from)).getAttribute("lat"));
                double prevLon = Double.parseDouble(doc.getElementById(Long.toString(from)).getAttribute("lon"));
                double curLat = prevLat, curLon = prevLon;
                pointsCollection.put(from, new Point(from, curLat, curLon));
                for (int j = 1; j < refsLength; j++) {
                    Element ref = (Element) refs.item(j);
                    String pointId = ref.getAttribute("ref");
                    Element point = doc.getElementById(pointId);
                    prevLat = curLat;
                    prevLon = curLon;
                    curLat = Double.parseDouble(point.getAttribute("lat"));
                    curLon = Double.parseDouble(point.getAttribute("lon"));
                    length += MapUtils.calculateLength(prevLat, prevLon, curLat, curLon);
                    to = Long.parseLong(pointId);
                    if (pointsCollection.get(to) != null) {
                        if (usedRoads.get(roadId) != null) {
                            roadsCollection.add(new Road(roadId, length, speed, from, to, isOneway, usedRoads.get(roadId)));
                        } else {
                            roadsCollection.add(new Road(roadId, length, speed, from, to, isOneway, transportMeans));
                        }
                        from = to;
                        length = 0.0;
                    }
                }
            }
        }
    }

    private void parseRoutes(Document doc) {
        HashMap<Long, HashMap<String, String>> usedRoads = new HashMap<>();
        String[] transportMeans = {"bus", "trolleybus", "tram", "train", "subway"};
        NodeList relations = doc.getElementsByTagName("relation");
        int relationsLength = relations.getLength();
        String transportMean;
        String routeNumber;
        for (int i = 0; i < relationsLength; i++) {
            transportMean = "foot";
            routeNumber = "";
            Element relation = (Element) relations.item(i);
            NodeList properties = relation.getElementsByTagName("tag");
            for (int j = 0; j < properties.getLength(); j++) {
                Element property = (Element) properties.item(j);
                String key = property.getAttribute("k");
                String value = property.getAttribute("v");
                if (key.equals("ref")) {
                    routeNumber = value;
                } else if (key.equals("route") && Arrays.asList(transportMeans).contains(value)) {
                    transportMean = value;
                }
            }
            if (!transportMean.equals("foot") && !routeNumber.equals("")) {
                NodeList members = relation.getElementsByTagName("member");
                for (int j = 0; j < members.getLength(); j++) {
                    Element member = (Element) members.item(j);
                    if (member.getAttribute("type").equals("way") && member.getAttribute("role").equals("")) {
                        long id = Long.parseLong(member.getAttribute("ref"));
                        HashMap<String, String> transportList = new HashMap<>();
                        if (usedRoads.get(id) != null) {
                            transportList.putAll(usedRoads.get(id));
                        }
                        transportList.put(routeNumber, transportMean);
                        usedRoads.put(id, transportList);
                    }
                }
            }
        }
        parseRoads(doc, usedRoads);
    }

    /**
     * Saves parsed data to file.
     *
     * @param points   points to save
     * @param roads    roads to save
     * @param dataPath path to file to save data in
     * @throws IOException
     */
    public static void saveData(HashMap<Long, Point> points, HashSet<Road> roads,
                                Path dataPath) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(dataPath.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(points);
            oos.writeObject(roads);
        }
    }

    /**
     * Returns {@link java.util.HashMap} of parsed and stored in this class points and their ids.
     *
     * @return a map of parsed points stored in this class
     */
    public HashMap<Long, Point> getPointsCollection() {
        return pointsCollection;
    }

    /**
     * Returns {@link java.util.HashSet} of parsed and stored in this class roads.
     *
     * @return a set of parsed roads stored in this class
     */
    public HashSet<Road> getRoadsCollection() {
        return roadsCollection;
    }
}
