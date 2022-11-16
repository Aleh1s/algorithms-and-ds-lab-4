package org.example.algorithm.bee;

import org.example.algorithm.Area;
import org.example.graph.ColorGraph;
import org.example.graph.node.Color;
import org.example.graph.node.Vertex;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.example.graph.node.Color.EMPTY;

public class BeeColony {

    private List<ColorGraph> colorGraphs;
    private final PriorityQueue<Area> areasQueue;
    private final int numberOfEmployedBees;
    private final int numberOfScoutBees;
    private final int numberOfIterations;
    private final int precision;

    public BeeColony(int numberOfEmployedBees, int numberOfScoutBees, int numberOfIterations, int precision) {
        this.numberOfEmployedBees = numberOfEmployedBees;
        this.numberOfScoutBees = numberOfScoutBees;
        this.numberOfIterations = numberOfIterations;
        this.precision = precision;
        this.areasQueue = new PriorityQueue<>(Comparator.comparing(Area::getChromaticNumber));
    }

    public void setColorGraphs(List<ColorGraph> colorGraphs) {
        this.colorGraphs = requireNonNull(colorGraphs);
    }

    public List<Integer> search() {
        scoutAll();
        List<Integer> chromaticNumbers = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; i++) {
            if (i % precision == 0) {
                Area peek = areasQueue.peek();
                if (peek != null)
                    chromaticNumbers.add(peek.getChromaticNumber());
            }

            int numberOfFreeBees = numberOfEmployedBees;
            List<Area> perspectiveAreas = new ArrayList<>();
            for (int j = 0; j < numberOfScoutBees - 1; j++)
                perspectiveAreas.add(areasQueue.poll());

            if (areasQueue.size() != 0)
                perspectiveAreas.add(getRandomAreaFromAreasQueue());

            for (Area area : perspectiveAreas) {
                if (numberOfFreeBees <= 0)
                    break;

                if (nonNull(area)) {
                    ColorGraph graph = area.getGraph();
                    if (area.getVertices().size() == 0)
                        area.setVertices(enqueueVertices(graph));
                    Vertex vertex = area.getVertices().poll();
                    if (nonNull(vertex)) {
                        int power = graph.getPower(vertex);
                        int neededBees = Math.min(numberOfFreeBees, power);
                        numberOfFreeBees -= neededBees;
                        paint(graph, vertex, neededBees);
                        area.setChromaticNumber(calculateChromaticNumber(graph));
                    }
                }
            }

            for (Area area : perspectiveAreas)
                if (nonNull(area)) areasQueue.add(area);

        }

        Area peek = areasQueue.peek();
        if (peek != null)
            chromaticNumbers.add(peek.getChromaticNumber());

        return chromaticNumbers;
    }

    private Area getRandomAreaFromAreasQueue() {
        ArrayList<Area> areas = new ArrayList<>(areasQueue);
        int randomI = ThreadLocalRandom.current().nextInt(areas.size());
        Area area = areas.get(randomI);
        areasQueue.remove(area);
        return area;
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

    private void scoutAll() {
        colorGraphs.forEach(colorGraph -> areasQueue.add(
                new Area(colorGraph, calculateChromaticNumber(colorGraph), enqueueVertices(colorGraph))));
    }

    private PriorityQueue<Vertex> enqueueVertices(ColorGraph colorGraph) {
        PriorityQueue<Vertex> vertices = new PriorityQueue<>(Comparator.comparing(colorGraph::getPower).reversed());
        vertices.addAll(colorGraph.nodes());
        return vertices;
    }

    private static int calculateChromaticNumber(ColorGraph colorGraph) {
        return colorGraph.nodes().stream()
                .map(Vertex::getColour)
                .filter(colour -> !colour.equals(EMPTY))
                .collect(Collectors.toSet())
                .size();
    }

    public PriorityQueue<Area> getAreasQueue() {
        return areasQueue;
    }
}
