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

    private final double minLat;
    private final double minLon;
    private final double maxLat;
    private final double maxLon;
    private final BufferedImage mapImage;
    private final Graphics2D content;

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

    public void drawImage(File map) throws IOException, ClassNotFoundException {
        double latFactor = mapImage.getHeight() / (maxLat - minLat);
        double lonFactor = mapImage.getWidth() / (maxLon - minLon);
        HashMap<Long, Point> points = new HashMap<>();
        HashSet<Road> roads = new HashSet<>();
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

    public void saveImage(File file) throws IOException {
        ImageIO.write(mapImage, "bmp", file);
    }

    private boolean checkBounds(double fromX, double fromY, double toX, double toY) {
        return (fromX > minLon && fromX < maxLon && toX > minLon && toX < maxLon &&
                fromY > minLat && fromY < maxLat && toY > minLat && toY < maxLat);
    }
}
