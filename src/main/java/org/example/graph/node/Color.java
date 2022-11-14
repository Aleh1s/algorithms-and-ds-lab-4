package org.example.graph.node;

public enum Color {
    EMPTY(0),
    RED(1),
    BLUE(2),
    YELLOW(3),
    GREEN(4),
    BLACK(5),
    ORANGE(6),
    PURPLE(7),
    GREY(8),
    BROWN(9),
    PINK(10),
    WHITE(11);

    private final int number;

    Color(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
