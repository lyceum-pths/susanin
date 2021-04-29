package ru.ioffe.school.susanin.debug;

import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Stop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Draws a map image and saves it to a file.
 */
public class MapDrawer {

    private final double minLat;
    private final double minLon;
    private final double maxLat;
    private final double maxLon;
    private final BufferedImage mapImage;
    private final Graphics2D content;

    /**
     * Constructs MapDrawer with specific
     * map image parameters.
     *
     * @param width  image width
     * @param height image height
     * @param minLat lower latitude bound of map part to draw
     * @param maxLat upper latitude bound of map part to draw
     * @param minLon lower longitude bound of map part to draw
     * @param maxLon upper longitude bound of map part to draw
     * @param isBW   true if image is black&amp;white, false otherwise
     */
    public MapDrawer(int width, int height, double minLat, double maxLat, double minLon, double maxLon, boolean isBW) {
        if (isBW) {
            this.mapImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        } else {
            this.mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;
        this.content = mapImage.createGraphics();
        this.content.setStroke(new BasicStroke(0.5f));
    }

    /**
     * Draws a specific map part image.
     *
     * @throws IOException
     */
    public void drawImage(HashMap<Long, Point> points, HashSet<Road> roads,
                          HashMap<Long, Stop> stops) throws IOException {
        double latFactor = mapImage.getHeight() / (maxLat - minLat);
        double lonFactor = mapImage.getWidth() / (maxLon - minLon);
        for (Road road : roads) {
            Point from = points.get(road.getFrom());
            Point to = points.get(road.getTo());
            double fromX = (from.getLon() - minLon) * lonFactor;
            double fromY = Math.abs((from.getLat() - minLat) * latFactor - mapImage.getHeight());
            double toX = (to.getLon() - minLon) * lonFactor;
            double toY = Math.abs((to.getLat() - minLat) * latFactor - mapImage.getHeight());
            if (checkBounds(from.getLon(), from.getLat(), to.getLon(), to.getLat())) {
                if (road.getTransportMeans().containsValue("foot") &&
                        road.getTransportMeans().containsValue("car")) {
                    content.setColor(Color.PINK);
                } else if (road.getTransportMeans().containsValue("subway")) {
                    content.setColor(Color.CYAN);
                } else if (road.getTransportMeans().containsValue("train")) {
                    content.setColor(Color.WHITE);
                } else if (road.getTransportMeans().containsValue("foot")) {
                    content.setColor(Color.LIGHT_GRAY);
                } else {
                    content.setColor(Color.YELLOW);
                }
                content.draw(new Line2D.Double(fromX, fromY, toX, toY));
            }
        }
        for (Stop stop : stops.values()) {
            double x = (stop.getLon() - minLon) * lonFactor;
            double y = Math.abs((stop.getLat() - minLat) * latFactor - mapImage.getHeight());
            if (checkBounds(stop.getLon(), stop.getLat(), stop.getLon(), stop.getLat())) {
                content.setColor(Color.CYAN);
                content.draw(new Ellipse2D.Double(x, y, 4, 4));
            }
        }
    }

    /**
     * Saves map image to a file.
     *
     * @param name name of image to save
     * @throws IOException
     */
    public void saveImage(String name) throws IOException {
        ImageIO.write(mapImage, "bmp", new File("map_images\\" + name + ".bmp"));
    }

    private boolean checkBounds(double fromX, double fromY, double toX, double toY) {
        return (fromX > minLon && fromX < maxLon && toX > minLon && toX < maxLon &&
                fromY > minLat && fromY < maxLat && toY > minLat && toY < maxLat);
    }
}
