package days.day10;

import util.Coordinate;
import util.NewGrid;
import util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {

    public static Integer part1(final List<String> input) {
        NewGrid newGrid = new NewGrid(input);
        char[][] grid = newGrid.getGrid();
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (Integer.parseInt(String.valueOf(grid[i][j])) == 0) {
                    Set<Coordinate> coords = new HashSet<>();
                    makeStep(grid, i, j, coords);
                    result += coords.size();
                }
            }
        }

        return result; // 709
    }

    private static void makeStep(final char[][] grid, final int i, final int j, final Set<Coordinate> coordinates) {
        if (Integer.parseInt(String.valueOf(grid[i][j])) == 9) {
            coordinates.add(new Coordinate(i, j));
            return;
        }
        Set<Coordinate> neighbours = new Coordinate(i, j).getNeighbours();
        for (Coordinate neighbour : neighbours) {
            if (neighbour.x() >= 0 && neighbour.x() < grid.length && neighbour.y() >= 0 && neighbour.y() < grid[0].length) {
                if (Integer.parseInt(String.valueOf(grid[(int) neighbour.x()][(int) neighbour.y()])) == Integer.parseInt(String.valueOf(grid[i][j])) + 1) {
                    makeStep(grid, (int) neighbour.x(), (int) neighbour.y(), coordinates);
                }
            }
        }
    }

    public static Integer part2(final List<String> input) {
        NewGrid newGrid = new NewGrid(input);
        char[][] grid = newGrid.getGrid();
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (Integer.parseInt(String.valueOf(grid[i][j])) == 0) {
                    result += makeStep2(grid, i, j);
                }
            }
        }

        return result; // 1326
    }

    private static int makeStep2(final char[][] grid, final int i, final int j) {
        if (Integer.parseInt(String.valueOf(grid[i][j])) == 9) {
            return 1;
        }
        Set<Coordinate> neighbours = new Coordinate(i, j).getNeighbours();
        int total = 0;
        for (Coordinate neighbour : neighbours) {
            if (neighbour.x() >= 0 && neighbour.x() < grid.length && neighbour.y() >= 0 && neighbour.y() < grid[0].length) {
                if (Integer.parseInt(String.valueOf(grid[(int) neighbour.x()][(int) neighbour.y()])) == Integer.parseInt(String.valueOf(grid[i][j])) + 1) {
                    total += makeStep2(grid, (int) neighbour.x(), (int) neighbour.y());
                }
            }
        }
        return total;
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(10);
//        var fileContent = Util.getTestFileContent(10);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
