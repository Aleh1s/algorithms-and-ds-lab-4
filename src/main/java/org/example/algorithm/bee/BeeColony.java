package org.example.algorithm.bee;

import org.example.algorithm.Area;
import org.example.graph.ColorGraph;
import org.example.graph.node.Color;
import org.example.graph.node.Vertex;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.graph.node.Color.*;
import static org.example.graph.node.Color.AQUAMARINE3;

public class BeeColony {

    private List<ColorGraph> colorGraphs;
    private List<Area> areas;
    private final int numberOfEmployedBees;
    private final int numberOfScoutBees;
    private final Set<Color> palette;

    public BeeColony(int numberOfEmployedBees, int numberOfScoutBees) {
        this.numberOfEmployedBees = numberOfEmployedBees;
        this.numberOfScoutBees = numberOfScoutBees;
        this.palette = EnumSet.range(RED, AQUAMARINE3);
    }

    public void setColorGraphs(List<ColorGraph> colorGraphs) {
        this.colorGraphs = Objects.requireNonNull(colorGraphs);
    }

    public void search() {
        for (int i = 0; i < 1000; i++) {
            scout();
            List<Area> mostPerspectiveAreas
                    = getMostPerspectiveAreas();
            int numberOfFreeBees = numberOfEmployedBees;
            for (Area area : mostPerspectiveAreas) {
                ColorGraph graph = area.getGraph();
                Vertex vertex = graph.getVertexWithMaxPower();
                int numberOfBees = Math.min(numberOfFreeBees, graph.getPower(vertex));
                numberOfFreeBees -= numberOfBees;
                paint(graph, vertex, numberOfBees);
            }
        }
    }

    private void paint(ColorGraph graph, Vertex vertex, int iteration) {
        List<Vertex> adjacentVertices = graph.adjacentNodes(vertex)
                .stream().toList();

        for (int i = 0; i < iteration; i++) {
            Vertex adjacentVertex = adjacentVertices.get(i);
            boolean isSwapped = swapColors(graph, vertex, adjacentVertex);
            if (isSwapped) tryToChangeColor(graph, adjacentVertex);
        }
    }

    private void tryToChangeColor(ColorGraph graph, Vertex vertex) {
        Set<Color> usedColors = graph.getUsedColors();
        Set<Color> adjacentColors = graph.getAdjacentColors(vertex);

        for (Color c : usedColors) {
            if (!adjacentColors.contains(c) && !c.equals(vertex.getColour())) {
                vertex.setColour(c);
                break;
            }
        }
    }

    private boolean swapColors(ColorGraph graph, Vertex v1, Vertex v2) {
        boolean canBeSwapped;

        Color v1Colour = v1.getColour();
        Color v2Colour = v2.getColour();

        Set<Color> v1AdjacentColors = graph.getAdjacentColorsExcept(v1, v2);
        canBeSwapped = !v1AdjacentColors.contains(v2Colour);
        if (!canBeSwapped) return false;

        Set<Color> v2AdjacentColors = graph.getAdjacentColorsExcept(v2, v1);
        canBeSwapped = !v2AdjacentColors.contains(v1Colour);
        if (!canBeSwapped) return false;

        v1.setColour(v2Colour);
        v2.setColour(v1Colour);
        return true;
    }

    private void scout() {
        this.areas = colorGraphs.stream()
                .map(colorGraph -> new Area(colorGraph, calculateChromaticNumber(colorGraph), enqueueVertices(colorGraph)))
                .collect(Collectors.toList());
        areas.forEach(area -> System.out.printf("Chromatic number: %d%n", area.getChromaticNumber()));
    }

    private PriorityQueue<Vertex> enqueueVertices(ColorGraph colorGraph) {
        PriorityQueue<Vertex> vertices = new PriorityQueue<>(Comparator.comparing(colorGraph::getPower));
        colorGraph.nodes().;
        return null;
    }

    private static int calculateChromaticNumber(ColorGraph colorGraph) {
        return colorGraph.nodes().stream()
                .map(Vertex::getColour)
                .filter(colour -> !colour.equals(EMPTY))
                .collect(Collectors.toSet())
                .size();
    }

    private List<Area> getMostPerspectiveAreas() {
        Comparator<Area> comparator
                = Comparator.comparing(Area::getChromaticNumber);

        if (areas.size() == 1)
            return new ArrayList<>(areas);

        List<Area> mostPerspectiveAreas = new ArrayList<>();
        if (numberOfScoutBees == 1) {
            Area area = areas.stream().max(comparator)
                    .orElseThrow(() -> new IllegalStateException("Cannot find the most perspective areas"));
            mostPerspectiveAreas.add(area);
        } else {
            List<Area> sortedAreas = areas.stream()
                    .sorted(comparator.reversed()).toList();
            int number = Math.min(numberOfScoutBees, sortedAreas.size());
            for (int i = 0; i < number; i++) {
                mostPerspectiveAreas.add(sortedAreas.get(i));
            }
        }

        return mostPerspectiveAreas;
    }

    public List<Area> getAreas() {
        return areas;
    }
}
