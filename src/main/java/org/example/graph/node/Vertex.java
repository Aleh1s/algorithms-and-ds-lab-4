package org.example.graph.node;

public class Vertex implements Comparable<Vertex> {
    private int id;
    private Color color;

    private Vertex(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    public Vertex(int id) {
        this(id, Color.EMPTY);
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id must be positive, but actually " + id);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Vertex copy() {
        return new Vertex(id, color);
    }

    public void setColour(Color color) {
        this.color = color;
    }

    public Color getColour() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex vertex)) return false;

        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(this.id, o.id);
    }
}
