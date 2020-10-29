package ru.ioffe.school.susanin.mapParsing;

import ru.ioffe.school.susanin.data.Way;
import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Path;
import ru.ioffe.school.susanin.data.MapGraph;
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

    private static HashMap<Point, Integer> pointCounter = new HashMap<Point, Integer>();
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
            parse(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parse(Document doc) {
        parsePoints(doc);
    }

    private static void parsePoints(Document doc) {
        try {
            ObjectToParse objectToParse = ObjectToParse.POINT;
            NodeList nodeList = doc.getElementsByTagName("node");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element node = (Element) nodeList.item(i);
                String id = node.getAttribute("id");
                double lat = Double.valueOf(node.getAttribute("lat"));
                double lon = Double.valueOf(node.getAttribute("lon"));
                NodeList properties = node.getElementsByTagName("tag");
                for (int j = 0; j < properties.getLength(); j++) {
                    Node property = properties.item(j);
                    if (property.getNodeType() == Node.ELEMENT_NODE) {
                        Element eProperty = (Element) property;
                        String key = eProperty.getAttribute("k");
                        String value = eProperty.getAttribute("v");
                        if ((key.equals("public_transport") && value.equals("stop_position"))
                                || key.equals("building")) {
                            if (key.equals("public_transport")) {
                                objectToParse = ObjectToParse.STOP;
                            }
                            else {
                                objectToParse = ObjectToParse.HOUSE;
                            }
                            for (j = 0; j < properties.getLength(); j++) {
                                key = eProperty.getAttribute("k");
                                if (key.equals("name")) {
                                    String name = eProperty.getAttribute("v");
                                }
                            }
                        }
                    }
                }
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Way> getRoutes(Document doc) {
        ArrayList<Way> ways = new ArrayList<>();
        return ways;
    }

    private static ArrayList<Way> getWays(Document doc) throws IOException {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Way> ways = new ArrayList<>();
        return ways;
    }
}
