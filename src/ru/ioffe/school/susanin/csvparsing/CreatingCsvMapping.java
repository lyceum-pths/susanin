package ru.ioffe.school.susanin.csvparsing;

import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.data.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatingCsvMapping {
    public static void CreatingCsvMapping(String[] args) throws IOException, CsvValidationException {
        final CsvParser parser = new CsvParser();

        final Path stopsPath = Paths.get("stops.txt");
        final List<Stop> stops = parser.parseStop(stopsPath);
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


        final Path stop_timesPath = Paths.get("stop_times.txt");
        final List<StopTimes> stop_times = parser.parseStopTimes(stop_timesPath);
        final Map<Integer, Integer> stop_sequenceById = new HashMap<Integer, Integer>();
        for (StopTimes stop_times1 : stop_times) {
            stop_sequenceById.put(stop_times1.getStopId(), stop_times1.getStopSequence() );
        } // map for sequences


        final Map<Integer, String> stop_arrivalById = new HashMap<Integer, String>();
        for (StopTimes stop_times1 : stop_times) {
            stop_arrivalById.put(stop_times1.getStopId(), stop_times1.getArrival());
        }//map for arrival time

        final Map<Integer, String> stop_departureById = new HashMap<Integer, String>();
        for (StopTimes stop_times1 : stop_times) {
            stop_departureById.put(stop_times1.getStopId(), stop_times1.getDeparture());
        }//map for departure time

        final Map<Integer, Integer> stop_typeById = new HashMap<Integer, Integer>();
        for (StopTimes stop_times1 : stop_times) {
            stop_typeById.put(stop_times1.getStopId(), stop_times1.getTripId() );
        } // map for type

    }
}