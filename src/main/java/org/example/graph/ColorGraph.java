package org.example.graph;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.example.graph.node.Color;
import org.example.graph.node.Vertex;

import javax.annotation.CheckForNull;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ColorGraph implements MutableGraph<Vertex> {
    private final MutableGraph<Vertex> sourceGraph;

    public ColorGraph(MutableGraph<Vertex> sourceGraph) {
        this.sourceGraph = sourceGraph;
    }

    public boolean isSolution() {
        return sourceGraph.nodes().stream()
                .noneMatch(node -> node.getColour().equals(Color.EMPTY));
    }

    public ColorGraph copy() {
        MutableGraph<Vertex> sourceCopy = GraphBuilder.undirected()
                .allowsSelfLoops(false).build();

        Set<EndpointPair<Vertex>> sourceEdges = sourceGraph.edges();
        Map<Integer, Vertex> copyVertices = new TreeMap<>();

        sourceEdges.forEach(edge -> {
            Vertex vertexU = edge.nodeU();
            Vertex vertexV = edge.nodeV();

            Vertex u = copyVertices.get(vertexU.getId());
            Vertex v = copyVertices.get(vertexV.getId());

            if (isNull(u)) {
                vertexU = vertexU.copy();
                copyVertices.put(vertexU.getId(), vertexU);
            } else vertexU = u;

            if (isNull(v)) {
                vertexV = vertexV.copy();
                copyVertices.put(vertexV.getId(), vertexV);
            } else vertexV = v;

            sourceCopy.putEdge(vertexU, vertexV);
        });


        return new ColorGraph(sourceCopy);
    }

    public Set<Color> getAdjacentColors(Vertex vertex) {
        checkVertexExists(vertex);
        return adjacentNodes(vertex).stream()
                .map(Vertex::getColour).collect(Collectors.toSet());
    }

    public Vertex getVertexWithMaxPower() {
        return nodes().stream()
                .max(Comparator.comparing(vertex -> adjacentNodes(vertex).size()))
                .orElseThrow(() -> new IllegalStateException("Cannot find vertex with max power"));
    }

    public int getPower(Vertex vertex) {
        checkVertexExists(vertex);
        return adjacentNodes(vertex).size();
    }

    private void checkVertexExists(Vertex vertex) {
        if (!nodes().contains(vertex))
            throw new IllegalArgumentException("Vertex does not exist");
    }

    public Set<Color> getUsedColors() {
        return sourceGraph.nodes().stream()
                .map(Vertex::getColour)
                .collect(Collectors.toSet());
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addNode(Vertex vertex) {
        return sourceGraph.addNode(vertex);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putEdge(Vertex vertex, Vertex n1) {
        return sourceGraph.putEdge(vertex, n1);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putEdge(EndpointPair<Vertex> endpointPair) {
        return sourceGraph.putEdge(endpointPair);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeNode(Vertex vertex) {
        return sourceGraph.removeNode(vertex);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeEdge(Vertex vertex, Vertex n1) {
        return sourceGraph.removeEdge(vertex, n1);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeEdge(EndpointPair<Vertex> endpointPair) {
        return sourceGraph.removeEdge(endpointPair);
    }

    @Override
    public Set<Vertex> nodes() {
        return sourceGraph.nodes();
    }

    @Override
    public Set<EndpointPair<Vertex>> edges() {
        return sourceGraph.edges();
    }

    @Override
    public boolean isDirected() {
        return sourceGraph.isDirected();
    }

    @Override
    public boolean allowsSelfLoops() {
        return sourceGraph.allowsSelfLoops();
    }

    @Override
    public ElementOrder<Vertex> nodeOrder() {
        return sourceGraph.nodeOrder();
    }

    @Override
    public ElementOrder<Vertex> incidentEdgeOrder() {
        return sourceGraph.incidentEdgeOrder();
    }

    @Override
    public Set<Vertex> adjacentNodes(Vertex vertex) {
        return sourceGraph.adjacentNodes(vertex);
    }

    @Override
    public Set<Vertex> predecessors(Vertex vertex) {
        return sourceGraph.predecessors(vertex);
    }

    @Override
    public Set<Vertex> successors(Vertex vertex) {
        return sourceGraph.successors(vertex);
    }

    @Override
    public Set<EndpointPair<Vertex>> incidentEdges(Vertex vertex) {
        return sourceGraph.incidentEdges(vertex);
    }

    @Override
    public int degree(Vertex vertex) {
        return sourceGraph.degree(vertex);
    }

    @Override
    public int inDegree(Vertex vertex) {
        return sourceGraph.inDegree(vertex);
    }

    @Override
    public int outDegree(Vertex vertex) {
        return sourceGraph.outDegree(vertex);
    }

    @Override
    public boolean hasEdgeConnecting(Vertex vertex, Vertex n1) {
        return sourceGraph.hasEdgeConnecting(vertex, n1);
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<Vertex> endpointPair) {
        return sourceGraph.hasEdgeConnecting(endpointPair);
    }

    @Override
    public boolean equals(@CheckForNull Object o) {
        return sourceGraph.equals(o);
    }

    @Override
    public int hashCode() {
        return sourceGraph.hashCode();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        nodes().stream()
                .sorted(Comparator.comparing(Vertex::getId))
                .forEach(vertex -> {
                    List<Vertex> vertices = adjacentNodes(vertex).stream()
                            .sorted(Comparator.comparing(Vertex::getId))
                            .toList();
                    sb.append(vertex).append(" ==> ").append(vertices).append('\n');
                });
        return sb.toString();
    }
}
