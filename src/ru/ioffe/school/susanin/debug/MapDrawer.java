package ru.ioffe.school.susanin.debug;

import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
     * @param width image width
     * @param height image height
     * @param minLat lower latitude bound of map part to draw
     * @param maxLat upper latitude bound of map part to draw
     * @param minLon lower longitude bound of map part to draw
     * @param maxLon upper longitude bound of map part to draw
     * @param isBW true if image is black&amp;white, false otherwise
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
     * @param map file with map data
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void drawImage(File map) throws IOException, ClassNotFoundException {
        double latFactor = mapImage.getHeight() / (maxLat - minLat);
        double lonFactor = mapImage.getWidth() / (maxLon - minLon);
        HashMap<Long, Point> points;
        HashSet<Road> roads;
        FileInputStream fis = new FileInputStream(map);
        ObjectInputStream ois = new ObjectInputStream(fis);
        points = (HashMap<Long, Point>) ois.readObject();
        roads = (HashSet<Road>) ois.readObject();
        for (Road road : roads) {
            Point from = points.get(road.getFrom());
            Point to = points.get(road.getTo());
            double fromX = (from.getLon() - minLon) * lonFactor;
            double fromY = Math.abs((from.getLat() - minLat) * latFactor - mapImage.getHeight());
            double toX = (to.getLon() - minLon) * lonFactor;
            double toY = Math.abs((to.getLat() - minLat) * latFactor - mapImage.getHeight());
            if (checkBounds(from.getLon(), from.getLat(), to.getLon(), to.getLat())) {
                if (road.getTransportMeans().equals(Map.of("foot", "foot"))) {
                    content.setColor(Color.PINK);
                } else if (road.getTransportMeans().containsValue("subway")) {
                    content.setColor(Color.CYAN);
                } else if (road.getTransportMeans().containsValue("train")) {
                    content.setColor(Color.WHITE);
                } else if (road.getTransportMeans().containsValue("tram")) {
                    content.setColor(Color.RED);
                } else {
                    content.setColor(Color.YELLOW);
                }
                content.draw(new Line2D.Double(fromX, fromY, toX, toY));
            }
        }
    }

    /**
     * Save a map image to a file.
     *
     * @param file file to save map image in
     * @throws IOException
     */
    public void saveImage(File file) throws IOException {
        ImageIO.write(mapImage, "bmp", file);
    }

    private boolean checkBounds(double fromX, double fromY, double toX, double toY) {
        return (fromX > minLon && fromX < maxLon && toX > minLon && toX < maxLon &&
                fromY > minLat && fromY < maxLat && toY > minLat && toY < maxLat);
    }
}
