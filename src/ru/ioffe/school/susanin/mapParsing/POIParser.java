package ru.ioffe.school.susanin.mapParsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import ru.ioffe.school.susanin.data.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Parses source file and gives a {@link java.util.Set}
 * of points which are needed during map parsing.
 */
public class POIParser {

    private Set<String> POI;

    public POIParser() {
        this.POI = new HashSet<>();
    }

    /**
     * Creates a {@link org.w3c.dom.Document} from source file
     * and initiates map preparsing sequence.
     *
     * @param sourcePath path to file with needed data
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public void parse(Path sourcePath) throws SAXException, ParserConfigurationException, IOException {
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
            public void fatalError(SAXParseException e) {
                System.err.println("Fatal error: " + e);
            }
        });
        Document doc = dBuilder.parse(sourcePath.toFile());
        doc.getDocumentElement().normalize();

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
            Element from = (Element) refs.item(0);
            Element to = (Element) refs.item(refs.getLength() - 1);
            String fromId = from.getAttribute("ref");
            String toId = to.getAttribute("ref");
            pointsCounter.put(fromId, pointsCounter.getOrDefault(fromId, 1) + 1);
            pointsCounter.put(toId, pointsCounter.getOrDefault(toId, 1) + 1);
        }
        pointsCounter.entrySet().removeIf(entry -> entry.getValue() == 1);
        POI = pointsCounter.keySet();
    }

    /**
     * Returns {@link java.util.Set} of points
     * which are needed during map parsing.
     *
     * @return set of points needed during map parsing
     */
    public Set<String> getPOI() {
        return POI;
    }
}
