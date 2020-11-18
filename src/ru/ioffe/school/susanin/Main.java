package ru.ioffe.school.susanin;

import ru.ioffe.school.susanin.mapParsing.Parser;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\n\"y\" for yes \"n\" for no");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            File map = new File("D:/osm/spb_full.xml");
            Parser.parse(map);
        }
    }
}
