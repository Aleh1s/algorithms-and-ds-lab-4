package org.example.algorithm;

import lombok.AllArgsConstructor;
import org.example.graph.node.Color;

@AllArgsConstructor
class Action {
    private int id;
    private Color color;

    public Action copy() {
        return new Action(id, color);
    }

    @Override
    public String toString() {
        return "%d - %s".formatted(id, color.name());
    }
}
