package days.day8;

import util.Coordinate;
import util.NewGrid;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {

    public static Integer part1(final List<String> input) {
        Set<Coordinate> antiNodeLocations = new HashSet<>();
        NewGrid newGrid = new NewGrid(input);
        Map<Character, List<Coordinate>> characterCoordinateHashMap = new HashMap<>();
        for (int i = 0; i < newGrid.getGrid().length; i++) {
            for (int j = 0; j < newGrid.getGrid()[0].length; j++) {
                if (newGrid.getGrid()[i][j] == '.') {
                    continue;
                }
                characterCoordinateHashMap.computeIfAbsent(newGrid.getGrid()[i][j], ArrayList::new).add(new Coordinate(i, j));
            }
        }

        characterCoordinateHashMap.forEach((character, coordinates) -> {
            for (int i = 0; i < coordinates.size() - 1; i++) {
                for (int j = i + 1; j < coordinates.size(); j++) {
                    long xdiff = Math.abs(coordinates.get(i).x() - coordinates.get(j).x());
                    long ydiff = Math.abs(coordinates.get(i).y() - coordinates.get(j).y());
                    if (coordinates.get(i).x() <= coordinates.get(j).x() && coordinates.get(i).y() <= coordinates.get(j).y()) {
                        Coordinate newCoord1 = new Coordinate(coordinates.get(i).x() - xdiff, coordinates.get(i).y() - ydiff);
                        Coordinate newCoord2 = new Coordinate(coordinates.get(j).x() + xdiff, coordinates.get(j).y() + ydiff);
                        if (newCoord1.x() >= 0 && newCoord1.x() < newGrid.getGrid().length && newCoord1.y() >= 0 && newCoord1.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord1);
                        }
                        if (newCoord2.x() >= 0 && newCoord2.x() < newGrid.getGrid().length && newCoord2.y() >= 0 && newCoord2.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord2);
                        }
                    }
                    else if (coordinates.get(i).x() <= coordinates.get(j).x() && coordinates.get(i).y() >= coordinates.get(j).y()) {
                        Coordinate newCoord1 = new Coordinate(coordinates.get(i).x() - xdiff, coordinates.get(i).y() + ydiff);
                        Coordinate newCoord2 = new Coordinate(coordinates.get(j).x() + xdiff, coordinates.get(j).y() - ydiff);
                        if (newCoord1.x() >= 0 && newCoord1.x() < newGrid.getGrid().length && newCoord1.y() >= 0 && newCoord1.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord1);
                        }
                        if (newCoord2.x() >= 0 && newCoord2.x() < newGrid.getGrid().length && newCoord2.y() >= 0 && newCoord2.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord2);
                        }
                    }
                    else if (coordinates.get(i).x() >= coordinates.get(j).x() && coordinates.get(i).y() <= coordinates.get(j).y()) {
                        Coordinate newCoord1 = new Coordinate(coordinates.get(i).x() + xdiff, coordinates.get(i).y() - ydiff);
                        Coordinate newCoord2 = new Coordinate(coordinates.get(j).x() - xdiff, coordinates.get(j).y() + ydiff);
                        if (newCoord1.x() >= 0 && newCoord1.x() < newGrid.getGrid().length && newCoord1.y() >= 0 && newCoord1.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord1);
                        }
                        if (newCoord2.x() >= 0 && newCoord2.x() < newGrid.getGrid().length && newCoord2.y() >= 0 && newCoord2.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord2);
                        }
                    }
                    else if (coordinates.get(i).x() >= coordinates.get(j).x() && coordinates.get(i).y() >= coordinates.get(j).y()) {
                        Coordinate newCoord1 = new Coordinate(coordinates.get(i).x() + xdiff, coordinates.get(i).y() + ydiff);
                        Coordinate newCoord2 = new Coordinate(coordinates.get(j).x() - xdiff, coordinates.get(j).y() - ydiff);
                        if (newCoord1.x() >= 0 && newCoord1.x() < newGrid.getGrid().length && newCoord1.y() >= 0 && newCoord1.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord1);
                        }
                        if (newCoord2.x() >= 0 && newCoord2.x() < newGrid.getGrid().length && newCoord2.y() >= 0 && newCoord2.y() < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(newCoord2);
                        }
                    }
                }
            }
        });

        return antiNodeLocations.size(); // 240
    }

    public static Integer part2(final List<String> input) {
        Set<Coordinate> antiNodeLocations = new HashSet<>();
        NewGrid newGrid = new NewGrid(input);
        Map<Character, List<Coordinate>> characterCoordinateHashMap = new HashMap<>();
        for (int i = 0; i < newGrid.getGrid().length; i++) {
            for (int j = 0; j < newGrid.getGrid()[0].length; j++) {
                if (newGrid.getGrid()[i][j] == '.') {
                    continue;
                }
                characterCoordinateHashMap.computeIfAbsent(newGrid.getGrid()[i][j], ArrayList::new).add(new Coordinate(i, j));
            }
        }

        characterCoordinateHashMap.forEach((character, coordinates) -> {
            for (int i = 0; i < coordinates.size() - 1; i++) {
                for (int j = i + 1; j < coordinates.size(); j++) {
                    long xdiff = Math.abs(coordinates.get(i).x() - coordinates.get(j).x());
                    long ydiff = Math.abs(coordinates.get(i).y() - coordinates.get(j).y());
                    if (coordinates.get(i).x() <= coordinates.get(j).x() && coordinates.get(i).y() <= coordinates.get(j).y()) {
                        long currentx1 = coordinates.get(i).x();
                        long currenty1 = coordinates.get(i).y();
                        while (currentx1 >= 0 && currentx1 < newGrid.getGrid().length && currenty1 >= 0 && currenty1 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx1, currenty1));
                            currentx1 -= xdiff;
                            currenty1 -= ydiff;
                        }
                        long currentx2 = coordinates.get(j).x();
                        long currenty2 = coordinates.get(j).y();
                        while (currentx2 >= 0 && currentx2 < newGrid.getGrid().length && currenty2 >= 0 && currenty2 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx2, currenty2));
                            currentx2 += xdiff;
                            currenty2 += ydiff;
                        }
                    }
                    else if (coordinates.get(i).x() <= coordinates.get(j).x() && coordinates.get(i).y() >= coordinates.get(j).y()) {
                        long currentx1 = coordinates.get(i).x();
                        long currenty1 = coordinates.get(i).y();
                        while (currentx1 >= 0 && currentx1 < newGrid.getGrid().length && currenty1 >= 0 && currenty1 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx1, currenty1));
                            currentx1 -= xdiff;
                            currenty1 += ydiff;
                        }
                        long currentx2 = coordinates.get(j).x();
                        long currenty2 = coordinates.get(j).y();
                        while (currentx2 >= 0 && currentx2 < newGrid.getGrid().length && currenty2 >= 0 && currenty2 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx2, currenty2));
                            currentx2 += xdiff;
                            currenty2 -= ydiff;
                        }
                    }
                    else if (coordinates.get(i).x() >= coordinates.get(j).x() && coordinates.get(i).y() <= coordinates.get(j).y()) {
                        long currentx1 = coordinates.get(i).x();
                        long currenty1 = coordinates.get(i).y();
                        while (currentx1 >= 0 && currentx1 < newGrid.getGrid().length && currenty1 >= 0 && currenty1 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx1, currenty1));
                            currentx1 += xdiff;
                            currenty1 -= ydiff;
                        }
                        long currentx2 = coordinates.get(j).x();
                        long currenty2 = coordinates.get(j).y();
                        while (currentx2 >= 0 && currentx2 < newGrid.getGrid().length && currenty2 >= 0 && currenty2 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx2, currenty2));
                            currentx2 -= xdiff;
                            currenty2 += ydiff;
                        }
                    }
                    else if (coordinates.get(i).x() >= coordinates.get(j).x() && coordinates.get(i).y() >= coordinates.get(j).y()) {
                        long currentx1 = coordinates.get(i).x();
                        long currenty1 = coordinates.get(i).y();
                        while (currentx1 >= 0 && currentx1 < newGrid.getGrid().length && currenty1 >= 0 && currenty1 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx1, currenty1));
                            currentx1 += xdiff;
                            currenty1 += ydiff;
                        }
                        long currentx2 = coordinates.get(j).x();
                        long currenty2 = coordinates.get(j).y();
                        while (currentx2 >= 0 && currentx2 < newGrid.getGrid().length && currenty2 >= 0 && currenty2 < newGrid.getGrid()[0].length) {
                            antiNodeLocations.add(new Coordinate(currentx2, currenty2));
                            currentx2 -= xdiff;
                            currenty2 -= ydiff;
                        }
                    }
                }
            }
        });

        return antiNodeLocations.size(); // 955
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(8);
//        var fileContent = Util.getTestFileContent(8);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
