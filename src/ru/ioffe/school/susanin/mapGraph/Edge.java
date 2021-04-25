package ru.ioffe.school.susanin.mapGraph;

/**
 * Represents a graph edge
 */
public interface Edge {

    double getTime(double currentTime);
    int getCost();
    Vertex getFrom();
    Vertex getTo();
}
