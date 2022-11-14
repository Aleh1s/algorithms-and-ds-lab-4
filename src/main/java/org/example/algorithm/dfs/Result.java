package org.example.algorithm.dfs;

import org.example.graph.ColorGraph;

import java.util.Objects;

import static org.example.algorithm.dfs.Indicator.FAILURE;
import static org.example.algorithm.dfs.Indicator.SOLUTION;

public class Result {

    private final ColorGraph solution;
    private final Indicator indicator;

    private Result(ColorGraph solution, Indicator indicator) {
        this.solution = solution;
        this.indicator = indicator;
    }

    public static Result of(Indicator indicator) {
        return new Result(null, indicator);
    }

    public static Result of(ColorGraph solution, Indicator indicator) {
        return new Result(solution, indicator);
    }

    public ColorGraph getSolution() {
        return solution;
    }

    public boolean hasSolution() {
        return indicator.equals(SOLUTION);
    }

    public boolean failure() {
        return indicator.equals(FAILURE);
    }

}
