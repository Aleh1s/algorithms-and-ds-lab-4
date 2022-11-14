package org.example.graph;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.example.graph.node.Node;

import javax.annotation.CheckForNull;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ColourGraph implements MutableGraph<Node> {
    private final MutableGraph<Node> sourceGraph;

    public ColourGraph(MutableGraph<Node> sourceGraph) {
        this.sourceGraph = sourceGraph;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addNode(Node node) {
        return sourceGraph.addNode(node);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putEdge(Node node, Node n1) {
        return sourceGraph.putEdge(node, n1);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putEdge(EndpointPair<Node> endpointPair) {
        return sourceGraph.putEdge(endpointPair);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeNode(Node node) {
        return sourceGraph.removeNode(node);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeEdge(Node node, Node n1) {
        return sourceGraph.removeEdge(node, n1);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeEdge(EndpointPair<Node> endpointPair) {
        return sourceGraph.removeEdge(endpointPair);
    }

    @Override
    public Set<Node> nodes() {
        return sourceGraph.nodes();
    }

    @Override
    public Set<EndpointPair<Node>> edges() {
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
    public ElementOrder<Node> nodeOrder() {
        return sourceGraph.nodeOrder();
    }

    @Override
    public ElementOrder<Node> incidentEdgeOrder() {
        return sourceGraph.incidentEdgeOrder();
    }

    @Override
    public Set<Node> adjacentNodes(Node node) {
        return sourceGraph.adjacentNodes(node);
    }

    @Override
    public Set<Node> predecessors(Node node) {
        return sourceGraph.predecessors(node);
    }

    @Override
    public Set<Node> successors(Node node) {
        return sourceGraph.successors(node);
    }

    @Override
    public Set<EndpointPair<Node>> incidentEdges(Node node) {
        return sourceGraph.incidentEdges(node);
    }

    @Override
    public int degree(Node node) {
        return sourceGraph.degree(node);
    }

    @Override
    public int inDegree(Node node) {
        return sourceGraph.inDegree(node);
    }

    @Override
    public int outDegree(Node node) {
        return sourceGraph.outDegree(node);
    }

    @Override
    public boolean hasEdgeConnecting(Node node, Node n1) {
        return sourceGraph.hasEdgeConnecting(node, n1);
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<Node> endpointPair) {
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
        StringBuffer sb  = new StringBuffer();
        nodes().stream()
                .sorted(Comparator.comparing(Node::getId))
                .forEach(node -> {
            List<Node> nodes = adjacentNodes(node).stream()
                    .sorted(Comparator.comparing(Node::getId))
                    .toList();
            sb.append(node).append(" ==> ").append(nodes).append('\n');
        });
        return sb.toString();
    }
}
