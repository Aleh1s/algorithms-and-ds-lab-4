package org.example.graph.controller;

public enum XMLTag {

    GRAPH("graph"),
    VERTEX("vertex"),
    ADJACENT_VERTEX("adjacentVertex");

    private final String name;

    XMLTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static XMLTag from(String name) {
        return switch (name) {
            case "graph" -> GRAPH;
            case "vertex" -> VERTEX;
            case "adjacentVertex" -> ADJACENT_VERTEX;
            default -> throw new IllegalStateException("Unknown tag name");
        };
    }
}
