package ru.ioffe.school.susanin.csvParsing;

import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.data.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CsvMapper {

    public CsvMapper() {
    }

    public static void createMapping() throws IOException, CsvValidationException {

        final String resourceFolder = "C:\\Users\\Eugene\\Research\\osm\\extra\\";

        final CsvParser parser = new CsvParser();

        final Path stopsPath = Paths.get(resourceFolder + "stops.txt");
        final List<Stop> stops = parser.parseStops(stopsPath);
        final Map<Long, Stop> stopsById = new HashMap<>();
        for (Stop stop : stops) {
            stopsById.put(stop.getId(), stop);
        }

        final Path routesPath = Paths.get(resourceFolder + "routes.txt");
        final List<Route> routes = parser.parseRoutes(routesPath);
        final Map<Integer, Route> routesById = new HashMap<>();
        for (Route route : routes) {
            routesById.put(route.getId(), route);
        }

        final Path tripsPath = Paths.get(resourceFolder + "trips.txt");
        final List<Trip> trips = parser.parseTrips(tripsPath);
        final Map<Integer, Trip> tripsById = new HashMap<>();
        for (Trip trip : trips) {
            tripsById.put(trip.getTripId(), trip);
        }

        final Path stopTimesPath = Paths.get(resourceFolder + "stop_times.txt");
        final HashMap<Integer, ArrayList<Long>> tripsAndTimes =
                parser.parseStopTimes(stopTimesPath, stopsById, tripsById);

        Set<Integer> keys = new HashSet<>(tripsAndTimes.keySet());
        for (int key : keys) {
            HashSet<Stop> routeStops = new HashSet<>();
            for (long stopId : tripsAndTimes.get(key)) {
                routeStops.add(stopsById.get(stopId));
            }
            if (tripsById.get(key).getDirection() == 1) {
                routesById.get(tripsById.get(key).getRouteId()).setClockwise(routeStops);
            } else {
                routesById.get(tripsById.get(key).getRouteId()).setCounterClockwise(routeStops);
            }
        }
    }
}