package ru.ioffe.school.susanin.mapGraph;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a map with a graph.
 */
public class Graph {
    public  Graph(ArrayList<Vertex> vertexes, ArrayList<Edge> edges) {

        ArrayList<ArrayList<Edge> > graph = new  ArrayList< ArrayList<Edge>>(1);

            for (edge: edges){
               Vertex from = Edge.getFrom() ;
               graph.add() = Edge.getTo();
            }
    }
}