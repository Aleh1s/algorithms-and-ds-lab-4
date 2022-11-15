package org.example.algorithm;

import org.example.graph.ColorGraph;

import java.util.Objects;

public class Area {
    private final ColorGraph graph;
    private final int chromaticNumber;

    public Area(ColorGraph graph, int chromaticNumber) {
        if (chromaticNumber < 0)
            throw new IllegalArgumentException("Chromatic number cannot be < 0, but actually " + chromaticNumber);

        this.graph = Objects.requireNonNull(graph);
        this.chromaticNumber = chromaticNumber;
    }

    public ColorGraph getGraph() {
        return graph;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }
}
