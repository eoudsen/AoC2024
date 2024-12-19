package days.day18;

import util.Coordinate;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Day18 {

    public static Integer part1(final List<String> input) {
        final Coordinate start = new Coordinate(0, 0);
        final Coordinate end = new Coordinate(70, 70);
        final Map<Coordinate, Integer> distances = dijkstra(start, getGrid(1024, input));
        return distances.get(end);
    }

    public static String part2(final List<String> input) {
        return findFirstBlockingByte(input);
    }

    private static int[][] getGrid(final int nrBytes, final List<String> input) {
        final int[][] grid = new int[71][71];
        for (int i = 0; i <= nrBytes; i++) {
            final String line = input.get(i);
            grid[Integer.parseInt(line.split(",")[1])][Integer.parseInt(line.split(",")[0])] = 1;
        }
        return grid;
    }

    private static Map<Coordinate, Integer> dijkstra(final Coordinate start, final int[][] grid) {
        final PriorityQueue<Coordinate> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Coordinate, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Coordinate current = queue.poll();
            final int dist = distances.get(current);
            final List<Coordinate> neighbours = getNeighbours(grid, current);
            for (final Coordinate n : neighbours) {
                int ndist = dist;
                ndist++;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }

        return distances;
    }

    private static String findFirstBlockingByte(final List<String> input) {
        final Coordinate start = new Coordinate(0, 0);
        final Coordinate end = new Coordinate(70, 70);
        int currentStart = 1025;
        int middle = currentStart + Math.round((float) (input.size() - 1025) / 2);
        while (true) {
            Map<Coordinate, Integer> distances = dijkstra(start, getGrid(middle, input));
            if (distances.get(end) == null) {
                distances = dijkstra(start, getGrid(middle - 1, input));
                if (distances.get(end) != null) {
                    break;
                }
                else {
                    middle = currentStart + Math.round((float) (middle - currentStart) / 2);
                }
            }
            else {
                currentStart = middle;
                middle += Math.round((float) (input.size() - middle) / 2);
            }
        }
        return input.get(middle);
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(18);
//        var fileContent = Util.getTestFileContent(18);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    private static boolean isInBounds(final int x, final int y, final int[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[0].length;
    }

    private static boolean isReachablePosition(final long x, final long y, final int[][] grid) {
        return isInBounds((int) x, (int) y, grid) && grid[(int) y][(int) x] != 1;
    }

    public static List<Coordinate> getNeighbours(final int[][] grid, final Coordinate coordinate) {
        final List<Coordinate> neighbours = new ArrayList<>();

        if (isReachablePosition(coordinate.x() - 1, coordinate.y(), grid)) {
            neighbours.add(new Coordinate(coordinate.x() - 1, coordinate.y()));
        }
        if (isReachablePosition(coordinate.x() + 1, coordinate.y(), grid)) {
            neighbours.add(new Coordinate(coordinate.x() + 1, coordinate.y()));
        }
        if (isReachablePosition(coordinate.x(), coordinate.y() - 1, grid)) {
            neighbours.add(new Coordinate(coordinate.x(), coordinate.y() - 1));
        }
        if (isReachablePosition(coordinate.x(), coordinate.y() + 1, grid)) {
            neighbours.add(new Coordinate(coordinate.x(), coordinate.y() + 1));
        }
        return neighbours;
    }
}
