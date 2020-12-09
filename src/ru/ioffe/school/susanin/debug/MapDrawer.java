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

public class MapDrawer {
    /**
     private static final double minLat = 59.8342;
     private static final double minLon = 30.3236;
     private static final double maxLat = 59.8438;
     private static final double maxLon = 30.3573;
     */
    /**
     * For medium
     * private static final double minLat = 59.8283;
     * private static final double minLon = 30.3084;
     * private static final double maxLat = 59.8677;
     * private static final double maxLon = 30.4432;
     */

    private static final double minLat = 59.79;
    private static final double minLon = 30.15;
    private static final double maxLat = 60.11;
    private static final double maxLon = 30.6;

    private BufferedImage mapImage;
    private Graphics2D content;

    public MapDrawer(int width, int height) {
        this.mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.content = mapImage.createGraphics();
        this.content.setStroke(new BasicStroke(0.5f));
    }

    public void drawImage(File map) throws IOException, ClassNotFoundException {
        double latFactor = mapImage.getHeight() / (maxLat - minLat);
        double lonFactor = mapImage.getWidth() / (maxLon - minLon);
        HashMap<Long, Point> points = new HashMap<>();
        HashSet<Road> roads = new HashSet<>();
        FileInputStream fis = new FileInputStream(map);
        ObjectInputStream ois = new ObjectInputStream(fis);
        points = (HashMap<Long, Point>) ois.readObject();
        roads = (HashSet<Road>) ois.readObject();
        content.setColor(new Color(50, 50, 50));
        content.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
        for (Road road : roads) {
            Point from = points.get(road.getFrom());
            Point to = points.get(road.getTo());
            double fromX = (from.getLon() - minLon) * lonFactor;
            double fromY = Math.abs((from.getLat() - minLat) * latFactor - mapImage.getHeight());
            double toX = (to.getLon() - minLon) * lonFactor;
            double toY = Math.abs((to.getLat() - minLat) * latFactor - mapImage.getHeight());
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
            if (checkBounds(fromX, fromY, toX, toY)) {
                content.draw(new Line2D.Double(fromX, fromY, toX, toY));
            }
        }
    }

    public void saveImage(File file) throws IOException {
        ImageIO.write(mapImage, "bmp", file);
    }

    private boolean checkBounds(double fromX, double fromY, double toX, double toY) {
        return (fromX > 0.0 && fromX < mapImage.getWidth() && toX > 0.0 && toX < mapImage.getWidth() &&
                fromY > 0.0 && fromY < mapImage.getHeight() && toY > 0.0 && toY < mapImage.getHeight());
    }
}
