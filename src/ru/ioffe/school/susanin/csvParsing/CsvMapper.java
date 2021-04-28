package ru.ioffe.school.susanin.csvParsing;

import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.data.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvMapper {

    public CsvMapper() {
    }

    public static void createMapping() throws IOException, CsvValidationException {

        final CsvParser parser = new CsvParser();

        final Path stopsPath = Paths.get("stops.txt");
        final List<Stop> stops = parser.parseStops(stopsPath);
        final Map<Long, Stop> stopsById = new HashMap<>();
        for (Stop stop : stops) {
            stopsById.put(stop.getId(), stop);
        }

        final Path routesPath = Paths.get("routes.txt");
        final List<Route> routes = parser.parseRoutes(routesPath);
        final Map<Integer, Route> routesById = new HashMap<>();
        for (Route route : routes) {
            routesById.put(route.getId(), route);
        }

        final Path tripsPath = Paths.get("trips.txt");
        final List<Trip> trips = parser.parseTrips(tripsPath);
        final Map<Integer, Trip> tripsById = new HashMap<>();
        for (Trip trip : trips) {
            tripsById.put(trip.getTripId(), trip);
        }

        final Path stopTimesPath = Paths.get("stop_times.txt");
        final List<StopTimes> stopTimes = parser.parseStopTimes(stopTimesPath, stopsById, tripsById);
        final Map<Integer, Integer> stop_sequenceById = new HashMap<>();
        for (StopTimes stopTime : stopTimes) {
            stop_sequenceById.put(stopTime.getStopId(), stopTime.getStopSequence());
        }
    }
}