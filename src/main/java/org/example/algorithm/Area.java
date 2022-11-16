package org.example.algorithm;

import org.example.graph.ColorGraph;
import org.example.graph.node.Vertex;

import java.util.Objects;
import java.util.PriorityQueue;

public class Area {
    private int chromaticNumber;
    private PriorityQueue<Vertex> vertices;
    private final ColorGraph graph;

    public Area(ColorGraph graph, int chromaticNumber, PriorityQueue<Vertex> vertices) {
        if (chromaticNumber < 0)
            throw new IllegalArgumentException("Chromatic number cannot be < 0, but actually " + chromaticNumber);

        this.vertices = Objects.requireNonNull(vertices);
        this.graph = Objects.requireNonNull(graph);
        this.chromaticNumber = chromaticNumber;
    }

    public ColorGraph getGraph() {
        return graph;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public void setChromaticNumber(int chromaticNumber) {
        this.chromaticNumber = chromaticNumber;
    }

    public void setVertices(PriorityQueue<Vertex> vertices) {
        this.vertices = vertices;
    }

    public PriorityQueue<Vertex> getVertices() {
        return vertices;
    }
}
