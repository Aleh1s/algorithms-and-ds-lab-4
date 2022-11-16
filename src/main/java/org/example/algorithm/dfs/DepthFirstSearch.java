package org.example.algorithm.dfs;

import org.example.graph.ColorGraph;
import org.example.graph.node.Color;
import org.example.graph.node.Vertex;

import java.util.*;

import static org.example.algorithm.dfs.Indicator.FAILURE;
import static org.example.algorithm.dfs.Indicator.SOLUTION;
import static org.example.graph.node.Color.*;

public class DepthFirstSearch {

    private ColorGraph graph;
    private final List<Color> palette;

    public DepthFirstSearch(ColorGraph graph) {
        this.graph = graph;
        this.palette = new ArrayList<>(EnumSet.range(RED, KHAKI));
    }

    public ColorGraph search() {
        Result result = recursive(graph);

        if (result.hasSolution())
            return result.getSolution();

        throw new IllegalStateException("Cannot find solution");
    }

    private Result recursive(ColorGraph curr) {
        if (curr.isSolution())
            return Result.of(curr, SOLUTION);

        List<ColorGraph> successors = getSuccessors(curr);

        if (successors.isEmpty())
            return Result.of(FAILURE);

        for (ColorGraph successor : successors) {
            Result result = recursive(successor);

            if (result.hasSolution())
                return result;
        }

        return Result.of(FAILURE);
    }

    private List<ColorGraph> getSuccessors(ColorGraph curr) {
        Vertex vertex = getVertexWithoutColour(curr);

        Set<Color> adjacentColors = curr.getAdjacentColors(vertex);
        List<ColorGraph> successors = new ArrayList<>();

        for (Color c : palette) {
            if (!adjacentColors.contains(c)) {
                ColorGraph copy = curr.copy();
                paint(vertex.getId(), c, copy);
                successors.add(copy);
            }
        }
        Collections.shuffle(successors);
        return successors;
    }

    private void paint(int id, Color color, ColorGraph state) {
        Vertex vertex = state.nodes().stream()
                .filter(node -> node.getId() == id)
                .findFirst().orElseThrow();
        vertex.setColour(color);
    }

    private Vertex getVertexWithoutColour(ColorGraph state) {
        return state.nodes().stream()
                .filter(n -> n.getColour().equals(EMPTY))
                .findAny().orElseThrow(IllegalStateException::new);
    }

    public void setGraph(ColorGraph graph) {
        this.graph = graph;
    }
}
