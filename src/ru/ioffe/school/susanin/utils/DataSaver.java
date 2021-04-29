package ru.ioffe.school.susanin.utils;

import ru.ioffe.school.susanin.data.Point;
import ru.ioffe.school.susanin.data.Road;
import ru.ioffe.school.susanin.data.Route;
import ru.ioffe.school.susanin.data.Stop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DataSaver {

    /**
     * Saves parsed data to file.
     *
     * @param points   points to save
     * @param roads    roads to save
     * @param dataPath path to file to save data in
     * @throws IOException
     */
    public static void saveData(HashMap<Long, Point> points, HashSet<Road> roads,
                                HashMap<Long, Stop> stops, ArrayList<Route> routes,
                                Path dataPath) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(dataPath.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(points);
            oos.writeObject(roads);
            oos.writeObject(stops);
            oos.writeObject(routes);
        }
    }
}
