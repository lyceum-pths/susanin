package ru.ioffe.school.susanin.mapParsing;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import ru.ioffe.school.susanin.data.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class Parser {

    private static HashMap<Long, Point> pointsCollection = new HashMap<>();
    private static HashSet<Road> roadsCollection = new HashSet<>();

    private enum ObjectToParse {

        POINT,
        HOUSE,
        STOP
    }

    public static void parse(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            dBuilder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException e) throws SAXException {
                    //System.err.println("Warning: " + e);
                }

                @Override
                public void error(SAXParseException e) throws SAXException {
                    //System.err.println("Error: " + e);
                }

                @Override
                public void fatalError(SAXParseException e) throws SAXException {
                    System.err.println("Fatal error: " + e);
                }
            });
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            parsePoints(doc, getInterestingPoints(doc));
            parseRoutes(doc);
            new PrintWriter("data/map.data").close();
            saveData(new File("data/map.data"));
            System.out.println("< PARSED >");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> getInterestingPoints(Document doc) {
        HashMap<String, Integer> pointsCounter = new HashMap<>();
        NodeList points = doc.getElementsByTagName("node");
        int size = points.getLength();
        for (int i = 0; i < size; i++) {
            Element point = (Element) points.item(i);
            NodeList properties = point.getElementsByTagName("tag");
            for (int j = 0; j < properties.getLength(); j++) {
                Element property = (Element) properties.item(j);
                String key = property.getAttribute("k");
                String value = property.getAttribute("v");
                if ((key.equals("public_transport") && (value.equals("stop_position") ||
                        value.equals("station"))) || key.equals("building")) {
                    pointsCounter.put(point.getAttribute("id"), 2);
                    break;
                }
            }
        }

        NodeList roads = doc.getElementsByTagName("way");
        size = roads.getLength();
        for (int i = 0; i < size; i++) {
            Element road = (Element) roads.item(i);
            NodeList refs = road.getElementsByTagName("nd");
            for (int j = 0; j < refs.getLength(); j++) {
                Element ref = (Element) refs.item(j);
                String id = ref.getAttribute("ref");
                if (pointsCounter.containsKey(id)) {
                    int count = pointsCounter.get(id);
                    pointsCounter.put(id, count + 1);
                } else {
                    pointsCounter.put(id, 1);
                }
            }
        }
        pointsCounter.entrySet().removeIf(entry -> pointsCounter.get(((Map.Entry) entry).getKey()) == 1);
        return pointsCounter.keySet();
    }

    private static void parsePoints(Document doc, Set<String> interestingPoints) {
        for (String pointId : interestingPoints) {
            ObjectToParse objectToParse = ObjectToParse.POINT;
            Element point = doc.getElementById(pointId);
            if (point != null) {
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

    private static void parseRoads(Document doc, HashMap<Long, HashMap<String, String>> usedRoads) {
        boolean isRoad, isPedestrian;
        String[] roadTypes = {"motorway", "trunk", "primary", "secondary", "tertiary",
                "unclassified", "residential", "motorway_link", "trunk_link", "primary_link",
                "secondary_link", "tertiary_link", "service", "living_street", "footway",
                "pedestrian", "path", "steps"};
        String[] pedestrian = {"service", "footway", "pedestrian", "path", "steps"};
        NodeList roads = doc.getElementsByTagName("way");
        int roadsLength = roads.getLength();
        for (int i = 0; i < roadsLength; i++) {
            isRoad = false;
            isPedestrian = false;
            long roadId, from, to;
            double length = 0.0;
            int speed = 5;
            boolean isOneway = false;
            Element road = (Element) roads.item(i);
            roadId = Long.parseLong(road.getAttribute("id"));
            NodeList properties = road.getElementsByTagName("tag");
            for (int j = 0; j < properties.getLength(); j++) {
                Element property = (Element) properties.item(j);
                String key = property.getAttribute("k");
                String value = property.getAttribute("v");
                if (key.equals("highway")) {
                    if (Arrays.asList(roadTypes).contains(value)) {
                        isRoad = true;
                        if (Arrays.asList(pedestrian).contains(value)) {
                            isPedestrian = true;
                        }
                        for (j = 0; j < properties.getLength(); j++) {
                            property = (Element) properties.item(j);
                            key = property.getAttribute("k");
                            value = property.getAttribute("v");
                            if (key.equals("maxspeed")) {
                                switch (value) {
                                    case "RU:urban":
                                        speed = 60;
                                        break;
                                    case "RU:rural":
                                        speed = 90;
                                        break;
                                    case "RU:motorway":
                                        speed = 110;
                                        break;
                                }
                            } else if (key.equals("oneway") && value.equals("yes")) {
                                isOneway = true;
                            }
                        }
                    }
                } else if (key.equals("railway")) {
                    if (value.equals("subway")) {
                        break;
                    }
                    speed = 50;
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
                    length += (Math.hypot(((curLat - prevLat) * 111400), ((curLon - prevLon) * 56000)));
                    to = Long.parseLong(pointId);
                    if (pointsCollection.get(to) != null) {
                        if (usedRoads.get(roadId) != null) {
                            roadsCollection.add(new Road(roadId, length, speed, from, to, isOneway, usedRoads.get(roadId)));
                        } else {
                            roadsCollection.add(new Road(roadId, length, speed, from, to, isOneway, isPedestrian));
                        }
                        from = to;
                        length = 0.0;
                    }
                }
            }
        }
    }

    private static void parseRoutes(Document doc) {
        HashMap<Long, HashMap<String, String>> usedRoads = new HashMap<>();
        String[] transportMeans = {"bus", "trolleybus", "tram", /* "train", */ "subway"};
        NodeList relations = doc.getElementsByTagName("relation");
        int relationsLength = relations.getLength();
        String transportMean = "foot";
        String routeNumber = "";
        for (int i = 0; i < relationsLength; i++) {
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

    private static void saveData(File file) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(pointsCollection);
        oos.writeObject(roadsCollection);
        oos.close();
        fos.close();
        /** for test
         * HashSet<Point> points = new HashSet<>();
         * HashSet<Road> roads = new HashSet<>();
         * FileInputStream fis = new FileInputStream(file);
         * ObjectInputStream ois = new ObjectInputStream(fis);
         * points = (HashSet<Point>) ois.readObject();
         * roads = (HashSet<Road>) ois.readObject();
         */
    }
}

