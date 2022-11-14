package org.example.graph.node;

public enum Color {
    EMPTY(0),
    RED(1),
    BLUE(2),
    YELLOW(3),
    GREEN(4),
    CHARTREUSE(5),
    ORANGE(6),
    PURPLE(7),
    GREY(8),
    BROWN(9),
    PINK(10),
    CADETBLUE(11),
    DARKSLATEGRAY(12),
    DARKSLATEBLUE(13),
    GOLD4(14),
    AQUAMARINE3(15);

    private final int number;

    Color(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
