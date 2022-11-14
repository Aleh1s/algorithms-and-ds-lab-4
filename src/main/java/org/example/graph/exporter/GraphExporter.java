package org.example.graph.exporter;

import com.google.common.graph.EndpointPair;
import org.example.graph.ColourGraph;
import org.example.graph.node.Vertex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GraphExporter {

    private ColourGraph colourGraph;
    private List<String> lines;
    private final String fileName;

    public GraphExporter(String fileName) {
        this.fileName = Objects.requireNonNull(fileName);
        this.lines = new LinkedList<>();
    }

    public void setColourGraph(ColourGraph colourGraph) {
        this.colourGraph = Objects.requireNonNull(colourGraph);
    }

    public void export() {
        build();
        write();
    }

    private void build() {
        List<String> lines = new LinkedList<>();
        lines.add("graph G {");
        for (Vertex v : colourGraph.nodes())
            lines.add("  %d [style=filled, color=%s]".formatted(v.getId(), v.getColour().name().toLowerCase()));

        lines.add("");

        for (EndpointPair<Vertex> endpointPair : colourGraph.edges())
            lines.add("  %d -- %d".formatted(endpointPair.nodeU().getId(), endpointPair.nodeV().getId()));
        lines.add("}");
        this.lines = lines;
    }

    private void write() {
        try {
            Files.write(Path.of(fileName), lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
