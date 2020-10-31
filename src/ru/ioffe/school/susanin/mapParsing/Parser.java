package ru.ioffe.school.susanin.mapParsing;

import ru.ioffe.school.susanin.data.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    private static HashMap<Point, Long> pointsCollection = new HashMap<>();

    private enum ObjectToParse {

        POINT,
        HOUSE,
        STOP,
        WAY,
        PATH,
        ROAD
    }

    public static void parse(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            parsePoints(doc);
            parseWays(doc);
            System.out.println("< PARSED >");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parsePoints(Document doc) throws OutOfMemoryError {
        NodeList nodeList = doc.getElementsByTagName("node");
        for (int i = 0; i < nodeList.getLength(); i++) {
            ObjectToParse objectToParse = ObjectToParse.POINT;
            Element node = (Element) nodeList.item(i);
            long id = Long.parseLong(node.getAttribute("id"));
            double lat = Double.parseDouble(node.getAttribute("lat"));
            double lon = Double.parseDouble(node.getAttribute("lon"));
            String name = null, street = null, number = null;
            NodeList properties = node.getElementsByTagName("tag");
            for (int j = 0; j < properties.getLength(); j++) {
                Element property = (Element) properties.item(j);
                String key = property.getAttribute("k");
                String value = property.getAttribute("v");
                if ((key.equals("public_transport") && value.equals("stop_position"))
                        || key.equals("building")) {
                    if (key.equals("public_transport")) {
                        objectToParse = ObjectToParse.STOP;
                    } else {
                        objectToParse = ObjectToParse.HOUSE;
                    }
                    for (j = 0; j < properties.getLength(); j++) {
                        property = (Element) properties.item(j);
                        key = property.getAttribute("k");
                        if (key.equals("name")) {
                            name = property.getAttribute("v");
                        } else if (key.equals("addr:street")) {
                            street = property.getAttribute("v");
                        } else if (key.equals("addr:housenumber")) {
                            number = property.getAttribute("v");
                        }
                    }
                }
            }
            switch (objectToParse) {
                case POINT:
                    pointsCollection.put(new Point(id, lat, lon), (long) 0);
                    break;
                case STOP:
                    pointsCollection.put(new Stop(id, lat, lon, name), (long) 2);
                    break;
                case HOUSE:
                    pointsCollection.put(new House(id, lat, lon, number, street, name), (long) 2);
                    break;
            }
        }
    }

    private static ArrayList<Way> getRoutes(Document doc) {
        ArrayList<Way> ways = new ArrayList<>();
        return ways;
    }

    private static ArrayList<Way> parseWays(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("way");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node road = nodeList.item(i);
            Element eRoad = (Element) road;
            NodeList subNodes = ((Element) road).getElementsByTagName("tag");
            /* temporary work checker */
            boolean isRoad = false;
            for (int j = 0; j < subNodes.getLength(); j++) {
                Node subNode = subNodes.item(j);
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eSubNode = (Element) subNode;
                    String attribute1 = eSubNode.getAttribute("k");
                    String attribute2 = eSubNode.getAttribute("v");
                    if (attribute1.equals("highway")) {
                        isRoad = true;
                    }
                    if (attribute1.equals("name") && isRoad) {
                        System.out.println(attribute2);
                    }
                }
            }
        }
        ArrayList<Way> ways = new ArrayList<>();
        return ways;
    }
}
