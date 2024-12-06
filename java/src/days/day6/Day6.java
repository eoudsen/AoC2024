package days.day6;

import util.Coordinate;
import util.NewGrid;
import util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 {

    public static Integer part1(final List<String> input) {
        final NewGrid grid = new NewGrid(input);
        Set<Coordinate> visitedCoordinates = new HashSet<>();
        Coordinate startLocation = null;
        int direction = 0; // 0: north, 1: east, 2: south, 3: west
        OUTER_INIT: for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[0].length; j++) {
                if (grid.getGrid()[i][j] == '^') {
                    startLocation = new Coordinate(i, j);
                    visitedCoordinates.add(startLocation);
                    break OUTER_INIT;
                }
            }
        }

        Coordinate currentCoordinate = startLocation;

        while (true) {
            Coordinate nextCoordinate;
            if (direction == 0) {
                nextCoordinate = new Coordinate(currentCoordinate.x() - 1, currentCoordinate.y());
            }
            else if (direction == 1) {
                nextCoordinate = new Coordinate(currentCoordinate.x(), currentCoordinate.y() + 1);
            }
            else if (direction == 2) {
                nextCoordinate = new Coordinate(currentCoordinate.x() + 1, currentCoordinate.y());
            }
            else {
                nextCoordinate = new Coordinate(currentCoordinate.x(), currentCoordinate.y() - 1);
            }


            if (nextCoordinate.x() < 0 || nextCoordinate.x() >= grid.getGrid().length || nextCoordinate.y() < 0 || nextCoordinate.y() >= grid.getGrid()[0].length) {
                break;
            }
            if (grid.getGrid()[(int) nextCoordinate.x()][(int) nextCoordinate.y()] == '#') {
                direction = (direction + 1) % 4;
                continue;
            }

            currentCoordinate = nextCoordinate;
            visitedCoordinates.add(currentCoordinate);

        }
        return visitedCoordinates.size(); // 5312
    }

    public static Long part2(final List<String> input) {
        final NewGrid grid = new NewGrid(input);
        CoordinateDirection startLocation = null;
        OUTER_INIT: for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[0].length; j++) {
                if (grid.getGrid()[i][j] == '^') {
                    startLocation = new CoordinateDirection(i, j, 0);
                    break OUTER_INIT;
                }
            }
        }

        long count = 0;

        for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[0].length; j++) {
                if (grid.getGrid()[i][j] == '#' || grid.getGrid()[i][j] == '^') {
                    continue;
                }
                final Set<CoordinateDirection> visitedCoordinates = new HashSet<>();
                visitedCoordinates.add(startLocation);
                CoordinateDirection currentCoordinate = startLocation;
                int direction = 0; // 0: north, 1: east, 2: south, 3: west

                while (true) {
                    CoordinateDirection nextCoordinate;
                    if (direction == 0) {
                        nextCoordinate = new CoordinateDirection(currentCoordinate.i - 1, currentCoordinate.j, 0);
                    }
                    else if (direction == 1) {
                        nextCoordinate = new CoordinateDirection(currentCoordinate.i, currentCoordinate.j + 1, 1);
                    }
                    else if (direction == 2) {
                        nextCoordinate = new CoordinateDirection(currentCoordinate.i + 1, currentCoordinate.j, 2);
                    }
                    else {
                        nextCoordinate = new CoordinateDirection(currentCoordinate.i, currentCoordinate.j - 1, 3);
                    }


                    if (nextCoordinate.i < 0 || nextCoordinate.i >= grid.getGrid().length || nextCoordinate.j < 0 || nextCoordinate.j >= grid.getGrid()[0].length) {
                        break;
                    }
                    if ((nextCoordinate.i == i && nextCoordinate.j == j ) || grid.getGrid()[nextCoordinate.i][nextCoordinate.j] == '#') {
                        direction = (direction + 1) % 4;
                        continue;
                    }

                    currentCoordinate = nextCoordinate;
                    if (visitedCoordinates.contains(currentCoordinate)) {
                        count++;
                        break;
                    }
                    visitedCoordinates.add(currentCoordinate);
                }

            }
        }
        return count; // 1748
    }


    public static void main(String... args) {
        var fileContent = Util.getFileContent(6);
//        var fileContent = Util.getTestFileContent(6);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    public record CoordinateDirection(int i, int j, int dir) {

    }
}
