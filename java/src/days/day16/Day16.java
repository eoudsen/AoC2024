package days.day16;

import util.Coordinate;
import util.NewGrid;
import util.Util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day16 {

    public static Integer part1(final List<String> input) {
        char[][] grid = new NewGrid(input).getGrid();
        Coordinate start = null;
        Coordinate end = null;
        for (int y = 0; y < grid.length & (start == null || end == null); y++) {
            for (int x = 0; x < grid[0].length & (start == null || end == null); x++) {
                if (grid[y][x] == 'S') {
                    start = new Coordinate(x, y);
                }
                else if (grid[y][x] == 'E') {
                    end = new Coordinate(x, y);
                }
            }
        }
        Queue<State> priorityQueue = new PriorityQueue<>(State.PRICE_COMPARATOR);
        Raindeer startDeer = new Raindeer(start, new Coordinate(1, 0));
        Map<Raindeer, Integer> seen = new HashMap<>();
        seen.put(startDeer, 0);
        priorityQueue.add(new State(startDeer, 0, new HashSet<>(List.of(start))));
        boolean weAreOverBestPaths = false;
        int minPrice = Integer.MAX_VALUE;
        while (!priorityQueue.isEmpty() && !weAreOverBestPaths) {
            State state = priorityQueue.poll();
            for (State next : state.next()) {
                if (isFree(next.raindeer.position, grid) && next.price <= seen.getOrDefault(next.raindeer, Integer.MAX_VALUE)) {
                    priorityQueue.add(next);
                    seen.put(next.raindeer, next.price);
                    if (end.equals(next.raindeer.position)) {
                        if (minPrice < next.price) {
                            weAreOverBestPaths = true;
                        }
                        minPrice = Math.min(minPrice, next.price);
                    }
                }
            }
        }
        return minPrice; // 143580
    }

    public static Integer part2(final List<String> input) {
        char[][] grid = new NewGrid(input).getGrid();
        Coordinate start = null;
        Coordinate end = null;
        for (int y = 0; y < grid.length & (start == null || end == null); y++) {
            for (int x = 0; x < grid[0].length & (start == null || end == null); x++) {
                if (grid[y][x] == 'S') {
                    start = new Coordinate(x, y);
                }
                else if (grid[y][x] == 'E') {
                    end = new Coordinate(x, y);
                }
            }
        }
        Map<Raindeer, Integer> seen = new HashMap<>();
        Set<Coordinate> bestPathPoints = new HashSet<>();
        Queue<State> priorityQueue = new PriorityQueue<>(State.PRICE_COMPARATOR);
        Map<Coordinate, Set<Coordinate>> bests = new HashMap<>();
        Raindeer startDeer = new Raindeer(start, new Coordinate(1, 0));
        seen.put(startDeer, 0);
        priorityQueue.add(new State(startDeer, 0, new HashSet<>(List.of(start))));
        boolean weAreOverBestPaths = false;
        int minPrice = Integer.MAX_VALUE;
        while (!priorityQueue.isEmpty() && !weAreOverBestPaths) {
            State state = priorityQueue.poll();
            for (State next : state.next()) {
                if (isFree(next.raindeer.position, grid) && next.price <= seen.getOrDefault(next.raindeer, Integer.MAX_VALUE)) {
                    if (next.price < seen.getOrDefault(next.raindeer, Integer.MAX_VALUE)) {
                        priorityQueue.add(next);
                        seen.put(next.raindeer, next.price);
                        if (end.equals(next.raindeer.position)) {
                            if (minPrice < next.price) {
                                weAreOverBestPaths = true;
                            }
                            minPrice = Math.min(minPrice, next.price);
                            bestPathPoints.addAll(next.visited);
                        }
                        bests.put(next.raindeer.position, next.visited);
                    }
                    else {
                        bests.get(next.raindeer.position).addAll(next.visited);
                    }
                }
            }
        }
        return bestPathPoints.size(); // 645
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(16);
//        var fileContent = Util.getTestFileContent(16);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    private static boolean isFree(final Coordinate position, final char[][] grid) {
        return position.x() >= 0 && position.x() < grid[0].length && position.y() >= 0 && position.y() < grid.length && grid[(int) position.y()][(int) position.x()] != '#';
    }

    private record Raindeer(Coordinate position, Coordinate orientation) {

        public Raindeer turnLeft() {
            return new Raindeer(position, new Coordinate(-orientation.y(), orientation.x()));
        }

        public Raindeer turnRight() {
            return new Raindeer(position, new Coordinate(orientation.y(), -orientation.x()));
        }

        public Raindeer step() {
            Coordinate newCoordinate = new Coordinate(position.x() + orientation.x(), position.y() + orientation.y());
            return new Raindeer(newCoordinate, orientation);
        }
    }

    private record State(Raindeer raindeer, int price, Set<Coordinate> visited) {
        public static final Comparator<State> PRICE_COMPARATOR = Comparator.comparing(State::price);

        public State turnLeft() {
            return new State(raindeer.turnLeft(), price + 1000, visited);
        }

        public State turnRight() {
            return new State(raindeer.turnRight(), price + 1000, visited);
        }

        public State step() {
            Raindeer step = raindeer.step();
            Set<Coordinate> v = new HashSet<>(visited);
            v.add(step.position);
            return new State(step, price + 1, v);
        }

        public List<State> next() {
            return List.of(turnLeft(), turnRight(), step());
        }
    }
}
