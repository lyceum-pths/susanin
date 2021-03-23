package ru.ioffe.school.susanin.mapGraph;

/**
 * Represents a graph edge
 */
interface Edge {

    default  int time;

    /**
     * Constructs Edge with specific time required to go through it.
     *
     * @param time in minutes required to go through edge
     */

    default Edge(int time) { this.time = time;}

    default int getTime() {
        return time;
    }
}
