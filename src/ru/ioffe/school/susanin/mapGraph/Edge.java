package ru.ioffe.school.susanin.mapGraph;

/**
 * Represents a graph edge
 */
interface Edge {

    double getTime();
    int getCost();
    Vertex getFrom();
    Vertex getTo();
}
