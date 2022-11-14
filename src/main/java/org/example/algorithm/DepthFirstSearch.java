package org.example.algorithm;

import org.example.graph.ColourGraph;
import org.example.graph.node.Color;
import org.example.graph.node.Vertex;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.algorithm.Indicator.*;
import static org.example.graph.node.Color.*;

public class DepthFirstSearch {

    private ColourGraph graph;

    public DepthFirstSearch(ColourGraph graph) {
        this.graph = graph;
    }

    public Stack<Node> search() {
        Stack<Node> path = new Stack<>();
        Indicator indicator = recursive(new Node(null, graph.copy()), path);
        if (indicator.equals(SOLUTION))
            return path;

        throw new IllegalStateException("Cannot find solution");
    }

    private Indicator recursive(Node curr, Stack<Node> path) {
        path.add(curr);

        if (curr.isSolution())
            return SOLUTION;

        List<Node> successors = getSuccessors(curr);

        if (successors.isEmpty())
            return FAILURE;

        for (Node successor : successors) {
            Indicator indicator = recursive(successor, path);

            if (indicator.equals(SOLUTION))
                return indicator;
            else if (indicator.equals(FAILURE))
                path.pop();
        }

        return FAILURE;
    }

    private List<Node> getSuccessors(Node curr) {
        ColourGraph state = curr.getState();
        Vertex vertex = getVertexWithoutColour(state);

        Set<Vertex> vertices = state.adjacentNodes(vertex);
        Set<Color> adjacentColors = vertices.stream()
                .map(Vertex::getColour)
                .collect(Collectors.toSet());

        List<Node> successors = new ArrayList<>();
        for (Color c : EnumSet.range(RED, WHITE)) {
            if (!adjacentColors.contains(c)) {
                Node copy = curr.copy();
                Action paint = paint(vertex.getId(), c, copy.getState());
                copy.setAction(paint);
                successors.add(copy);
            }
        }
        return successors;
    }

    private Action paint(int id, Color color, ColourGraph state) {
        Vertex vertex = state.nodes().stream()
                .filter(node -> node.getId() == id)
                .findFirst().orElseThrow();
        vertex.setColour(color);
        return new Action(id, color);
    }

    private Vertex getVertexWithoutColour(ColourGraph state) {
        return state.nodes().stream()
                .filter(n -> n.getColour().equals(EMPTY))
                .findAny().orElseThrow(IllegalStateException::new);
    }
}
