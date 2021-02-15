package ru.ioffe.school.susanin.csvparsing;

import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.data.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvMapper {
    public static void createMapping(String[] args) throws IOException, CsvValidationException {
        final CsvParser parser = new CsvParser();

        final Path stopsPath = Paths.get("stops.txt");
        final List<Stop> stops = parser.parseStops(stopsPath);
        final Map<Long, Stop> stopsById = new HashMap<Long, Stop>();
        for (Stop stops1 : stops) {
            stopsById.put(stops1.getId(), stops1);
        }

        final Path routesPath = Paths.get("routes.txt");
        final List<Routes> routes = parser.parseRoutes(routesPath);
        final Map<Integer, String> routesById = new HashMap<Integer, String>();
        for (Routes routes1 : routes) {
            routesById.put(routes1.getId(), routes1.getType());
        }//map for type

        final Path tripsPath = Paths.get("trips.txt");
        final List<Trips> trips = parser.parseTrips(tripsPath);
        final Map<Integer, Integer> tripsById = new HashMap<Integer, Integer>();
        for (Trips trips1 : trips) {
            tripsById.put(trips1.getTripId(), trips1.getRouteId());
        }//map for type

        final Path stopTimesPath = Paths.get("stop_times.txt");
        final List<StopTimes> stopTimes = parser.parseStopTimes(stopTimesPath);
        final Map<Integer, Integer> stop_sequenceById = new HashMap<Integer, Integer>();
        for (StopTimes stopTimes1 : stopTimes) {
            stop_sequenceById.put(stopTimes1.getStopId(), stopTimes1.getStopSequence());
        } // map for sequences

        final Map<Integer, String> stopArrivalById = new HashMap<Integer, String>();
        for (StopTimes stopTimes1 : stopTimes) {
            stopArrivalById.put(stopTimes1.getStopId(), stopTimes1.getArrival());
        }//map for arrival time

        final Map<Integer, String> stopDepartureById = new HashMap<Integer, String>();
        for (StopTimes stopTimes1 : stopTimes) {
            stopDepartureById.put(stopTimes1.getStopId(), stopTimes1.getDeparture());
        }//map for departure time

        final Map<Integer, Integer> stopTypeById = new HashMap<Integer, Integer>();
        for (StopTimes stopTimes1 : stopTimes) {
            stopTypeById.put(stopTimes1.getStopId(), stopTimes1.getTripId());
        } // map for type
    }
}