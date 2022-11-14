package org.example.graph.node;


import lombok.AllArgsConstructor;

public class Node {
    private int id;
    private Colour colour;

    private Node(int id, Colour colour) {
        this.id = id;
        this.colour = colour;
    }

    public Node(int id) {
        this(id, null);
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id must be positive, but actually " + id);
        this.id = id;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;

        return id == node.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Node{");
        sb.append("id=").append(id);
        sb.append(", colour=").append(colour);
        sb.append('}');
        return sb.toString();
    }
}
