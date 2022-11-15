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
    AQUAMARINE3(15),
    ANTIQUEWHITE4(16),
    BROWN3(17),
    MAROON(18),
    MEDIUMPURPLE(19),
    SKYBLUE(20),
    SPRINGGREEN(21),
    YELLOWGREEN(22),
    TOMATO(23),
    NAVY(24),
    MISTYROSE(25),
    OLIVE(26),
    DARKORANGE(27),
    CHOCOLATE(28),
    DARKRED(29),
    KHAKI(30);

    private final int number;

    Color(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
