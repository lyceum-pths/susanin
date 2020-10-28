package ru.ioffe.school.susanin.mapParsing;

import ru.ioffe.school.susanin.data.Way;
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

public class Parser {
    public static void parse(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            parse(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parse(Document doc) {

    }

    private static ArrayList<Node> getNodes(Document doc) throws IOException {
        NodeList NodeList = doc.getElementsByTagName("node");
        ArrayList<Node> nodes = new ArrayList<>();
        return nodes;
    }

    private static ArrayList<Way> getRoutes(Document doc) {
        ArrayList<Way> ways = new ArrayList<>();
        return ways;
    }

    private static ArrayList<Way> getWays(Document doc) throws IOException {
        try {
            NodeList nodeList = doc.getElementsByTagName("way");
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node road = nodeList.item(i);
                Element eRoad = (Element) road;
                NodeList subNodes = ((Element) road).getElementsByTagName("tag");
                /* temporary work checker */
                boolean isRoad = false;
                for (int j = 0; j < subNodes.getLength(); j++) {
                    org.w3c.dom.Node subNode = subNodes.item(j);
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
