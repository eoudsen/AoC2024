package util;

import java.util.List;

public class Direction {

    private int x;
    private int y;

    public static final Direction N = new Direction(-1, 0);
    public static final Direction E = new Direction(0, 1);
    public static final Direction S = new Direction(1, 0);
    public static final Direction W = new Direction(0, -1);

    public static final List<Direction> DIRECTIONS = List.of(N, E, S, W);

    public static Direction left90(Direction dir) {
        return new Direction(-1 * dir.y(), dir.x());
    }

    public static Direction right90(Direction dir) {
        return new Direction(dir.y(), -1 * dir.x());
    }

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return this.x; }
    public int y() { return this.y; }

}
