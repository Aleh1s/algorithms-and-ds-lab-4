package org.example.graph.exporter;

import com.google.common.graph.EndpointPair;
import org.example.graph.ColorGraph;
import org.example.graph.node.Vertex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GraphExporter {

    private ColorGraph colorGraph;
    private List<String> lines;
    private final String fileName;

    public GraphExporter(String fileName) {
        this.fileName = Objects.requireNonNull(fileName);
        this.lines = new LinkedList<>();
    }

    public void setColourGraph(ColorGraph colorGraph) {
        this.colorGraph = Objects.requireNonNull(colorGraph);
    }

    public void export() {
        build();
        write();
    }

    private void build() {
        List<String> lines = new LinkedList<>();
        lines.add("graph G {");
        for (Vertex v : colorGraph.nodes())
            lines.add("  %d [style=filled, color=%s]".formatted(v.getId(), v.getColour().name().toLowerCase()));

        lines.add("");

        for (EndpointPair<Vertex> endpointPair : colorGraph.edges())
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
