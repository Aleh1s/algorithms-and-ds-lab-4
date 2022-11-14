package org.example.algorithm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.graph.ColourGraph;
import org.example.graph.node.Color;

@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Action action;
    private ColourGraph state;

    public Node copy() {
        Action copy = null;

        if (action != null)
            copy = action.copy();

        return new Node(copy, state.copy());
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setState(ColourGraph state) {
        this.state = state;
    }

    public boolean isSolution() {
        return state.nodes().stream()
                .noneMatch(node -> node.getColour().equals(Color.EMPTY));
    }

    public Action getAction() {
        return action;
    }

    public ColourGraph getState() {
        return state;
    }

    @Override
    public String toString() {
        if (action == null) return "INITIAL";
        return action.toString();
    }
}
