package ru.ioffe.school.susanin.csvparsing;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.ioffe.school.susanin.data.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    private final Charset charset;

    public CsvParser(Charset charset) {
        this.charset = charset;
    }

    public CsvParser() {
        this(StandardCharsets.UTF_8);
    }


    public List<Stop> parseStop(Path path) throws IOException, CsvValidationException {
        final List<Stop> result = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {

                long id = Integer.parseInt(data[0]);
                int code = Integer.parseInt(data[1]);
                String name = data[2];
                double lat = Double.parseDouble(data[3]);
                double lon = Double.parseDouble(data[4]);
                String type = data[7];

                result.add(new Stop(id, code, name, lat, lon, type));

            }
            return result;
        }
    }

    public List<Routes> parseRoutes(Path path) throws IOException, CsvValidationException {
        final List<Routes> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                int routeId = Integer.parseInt(data[0]);
                String routeName = data[1];
                String routeType = data[4];
                result.add(new Routes(routeId, routeName, routeType));
            }
        }
        return result;
    }

    public List<Trips> parseTrips(Path path) throws IOException, CsvValidationException {
        final List<Trips> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                int routeId = Integer.parseInt(data[0]);
                int tripId = Integer.parseInt(data[2]);
                result.add(new Trips(routeId, tripId));
            }
        }
        return result;
    }

    public List<StopTimes> parseStopTimes(Path path) throws IOException, CsvValidationException {
        final List<StopTimes> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile(), charset))) {
            reader.readNext();
            for (String[] data : reader) {
                int tripId = Integer.parseInt(data[0]);
                String arrival = data[1];//should be int
                String departure = data[2];// should be int
                int stopId = Integer.parseInt(data[3]);
                int stopSequence = Integer.parseInt(data[4]);

                result.add(new StopTimes(tripId, arrival, departure, stopId, stopSequence));
            }
        }
        return result;
    }

}
