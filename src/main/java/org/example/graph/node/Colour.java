package org.example.graph.node;

public enum Colour {
    RED(1),
    BLUE(2),
    YELLOW(3),
    GREEN(4),
    BLACK(5),
    ORANGE(6),
    PURPLE(7),
    GREY(8);

    private final int number;

    Colour(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
