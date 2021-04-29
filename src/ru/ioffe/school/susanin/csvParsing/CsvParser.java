package ru.ioffe.school.susanin.csvParsing;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.data.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

public class CsvParser {

    private final Charset charset;

    public CsvParser(Charset charset) {
        this.charset = charset;
    }

    public CsvParser() {
        this(StandardCharsets.UTF_8);
    }

    public List<Stop> parseStops(Path path) throws IOException, CsvValidationException {
        final List<Stop> result = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                long id = Integer.parseInt(data[0]);
                String name = data[2];
                double lat = Double.parseDouble(data[3]);
                double lon = Double.parseDouble(data[4]);
                String type = data[7];

                result.add(new Stop(id, name, lat, lon, type));
            }
            return result;
        }
    }

    public List<Route> parseRoutes(Path path) throws IOException, CsvValidationException {
        final List<Route> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                int routeId = Integer.parseInt(data[0]);
                String routeName = data[2];
                String routeType = data[5];

                result.add(new Route(routeId, routeName, routeType));
            }
        }
        return result;
    }

    public List<Trip> parseTrips(Path path) throws IOException, CsvValidationException {
        final List<Trip> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                int routeId = Integer.parseInt(data[0]);
                int tripId = Integer.parseInt(data[2]);
                int direction = Integer.parseInt(data[3]);

                result.add(new Trip(routeId, tripId, direction));
            }
        }
        return result;
    }

    public HashMap<Integer, ArrayList<Long>> parseStopTimes(
            Path path, Map<Long, Stop> stopsById, Map<Integer, Trip> tripsById)
            throws IOException, CsvValidationException {
        final HashMap<Integer, ArrayList<Long>> result = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                int tripId = Integer.parseInt(data[0]);
                String[] time = data[1].split(":");
                int absTime = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
                long stopId = Long.parseLong(data[3]);

                stopsById.get(stopId).addStopTime(absTime);
                if (!result.containsKey(tripId)) {
                    result.put(tripId, new ArrayList<>(Arrays.asList(stopId)));
                } else {
                    result.get(tripId).add(stopId);
                }
            }
        }
        return result;
    }
}
