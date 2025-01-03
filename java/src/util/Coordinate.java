package util;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public record Coordinate(long x, long y) implements Comparable<Coordinate> {

    public Set<Coordinate> getNeighbours() {
        final Set<Coordinate> neighbours = new HashSet<>();
        neighbours.add(new Coordinate(x - 1, y));
        neighbours.add(new Coordinate(x + 1, y));
        neighbours.add(new Coordinate(x, y - 1));
        neighbours.add(new Coordinate(x, y + 1));
        return neighbours;
    }

    public Set<Coordinate> getNeighbours(int cheatDistance) {
        Set<Coordinate> neighbours = new TreeSet<>();
        for (int i = 0; i < cheatDistance; i++) {
            neighbours.add(new Coordinate(x + i, y - cheatDistance + i));
            neighbours.add(new Coordinate(x + cheatDistance - i, y + i));
            neighbours.add(new Coordinate(x - i, y + cheatDistance - i));
            neighbours.add(new Coordinate(x - cheatDistance + i, y - i));
        }
        return neighbours;
    }

    public Coordinate relative(final Direction dir) {
        return new Coordinate(this.x + dir.x(), this.y + dir.y());
    }

    @Override
    public int compareTo(final Coordinate other) {
        if (this.y != other.y()) {
            return Long.compare(this.y, other.y());
        } else {
            return Long.compare(this.x, other.x());
        }
    }
}
