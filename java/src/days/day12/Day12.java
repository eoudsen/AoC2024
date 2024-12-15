package days.day12;

import util.Coordinate;
import util.Direction;
import util.NewGrid;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {

    public static Integer part1(final List<String> input) {
        NewGrid newGrid = new NewGrid(input);
        char[][] grid = newGrid.getGrid();
        Set<Coordinate> seenCoordinates = new HashSet<>();
        int totalCost = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (seenCoordinates.contains(new Coordinate(i, j))) {
                    continue;
                }
                char currentChar = grid[i][j];
                Set<Coordinate> currentPlots = new HashSet<>();
                currentPlots.add(new Coordinate(i, j));
                int fence = searchPlots(grid, currentChar, new Coordinate(i, j), currentPlots);
                totalCost += fence * currentPlots.size();
                seenCoordinates.addAll(currentPlots);
            }
        }
        return totalCost; // 1518548
    }

    private static int searchPlots(final char[][] grid, final char currentChar, final Coordinate searchCoordinate, final Set<Coordinate> currentPlots) {
        int fence = 0;
        for (Coordinate neighbour : searchCoordinate.getNeighbours()) {
            if (neighbour.x() >= 0 && neighbour.x() < grid.length && neighbour.y() >= 0 && neighbour.y() < grid[0].length && grid[(int) neighbour.x()][(int) neighbour.y()] == currentChar) {
                if (currentPlots.contains(neighbour)) {
                    continue;
                }
                currentPlots.add(neighbour);
                fence += searchPlots(grid, currentChar, neighbour, currentPlots);
            }
            else {
                fence ++;
            }
        }
        return fence;
    }

    public static Integer part2(final List<String> input) {
        NewGrid newGrid = new NewGrid(input);
        char[][] grid = newGrid.getGrid();
        Set<Coordinate> seenCoordinates = new HashSet<>();
        int totalCost = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (seenCoordinates.contains(new Coordinate(i, j))) {
                    continue;
                }
                char currentChar = grid[i][j];
                Set<Coordinate> currentPlots = new HashSet<>();
                currentPlots.add(new Coordinate(i, j));
                searchPlots(grid, currentChar, new Coordinate(i, j), currentPlots);

                totalCost += calculateSides(currentPlots, grid) * currentPlots.size();
                seenCoordinates.addAll(currentPlots);
            }
        }
        return totalCost; // 909564
    }


    private static int calculateSides(final Set<Coordinate> coordinates, final char[][] grid) {
        List<Edge> sides = new ArrayList<>();

        for (Coordinate coordinate : coordinates) {
            for (Direction d : Direction.DIRECTIONS) {
                Coordinate edge = coordinate.relative(d);
                if (edge.x() >= 0 && edge.x() < grid.length && edge.y() >= 0 && edge.y() < grid[0].length && grid[(int) edge.x()][(int) edge.y()] == grid[(int) coordinate.x()][(int) coordinate.y()]) {
                    continue;
                }

                boolean found = false;
                List<Edge> matched = new ArrayList<>();
                for (Edge e : sides) {
                    if (e.adjacent(coordinate, d)) {
                        matched.add(e);
                        if (!found) {
                            e.cells.add(coordinate);
                            found = true;
                        }
                    }
                }

                if (!found) {
                    Edge e = new Edge(d);
                    e.cells.add(coordinate);
                    sides.add(e);
                }
                else if (matched.size() > 1) {
                    Edge e = matched.getFirst();
                    for (int i = 1; i < matched.size(); i++) {
                        e.cells.addAll(matched.get(i).cells);
                        sides.remove(matched.get(i));
                    }
                }
            }
        }

        return sides.size();
    }

    static class Edge {
        Set<Coordinate> cells = new HashSet<>();
        Direction d;

        public Edge(Direction d) { this.d = d; }

        public boolean adjacent(Coordinate c, Direction d) {
            if (!this.d.equals(d)) { return false; }

            for (Coordinate cell : cells) {
                if (cell.relative(Direction.left90(d)).equals(c) ||
                        cell.relative(Direction.right90(d)).equals(c)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(12);
//        var fileContent = Util.getTestFileContent(12);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
