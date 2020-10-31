package ru.ioffe.school.susanin;

import ru.ioffe.school.susanin.mapParsing.Parser;

import java.io.FileNotFoundException;
import java.io.File;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File map = new File("C:/Users/Eugene/Downloads/map3.xml");
        Parser.parse(map);
    }
}
