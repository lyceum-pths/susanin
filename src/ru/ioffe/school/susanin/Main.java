package ru.ioffe.school.susanin;

import ru.ioffe.school.susanin.debug.MapDrawer;
import ru.ioffe.school.susanin.mapParsing.Parser;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Need to parse new file?\n\"y\" for yes \"n\" for no");
        String choice = in.nextLine();
        if (choice.equals("y")) {
            File map = new File("C:\\Users\\Eugene\\Research\\osm\\spb.xml");
            Parser.parse(map);
        }
        MapDrawer mapDrawer = new MapDrawer(2560, 1920);
        try {
            mapDrawer.drawImage(new File("data\\map.data"));
            mapDrawer.saveImage(new File("C:\\Users\\Eugene\\Desktop\\spbfull_img.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
