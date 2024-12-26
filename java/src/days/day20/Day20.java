package days.day20;

import util.Coordinate;
import util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day20 {

    public static Long part1(final List<String> input) {
        RaceTrack raceTrack = RaceTrack.from(input.stream());
        Map<Coordinate, Integer> map = raceTrack.distanceMap();
        return raceTrack.countGoodCheats(map, 2); // 1286
    }

    public static Long part2(final List<String> input) {
        RaceTrack raceTrack = RaceTrack.from(input.stream());
        Map<Coordinate, Integer> map = raceTrack.distanceMap();
        return raceTrack.countGoodCheats(map, 20); // 989316
    }

    public static void main(final String... args) {
        var fileContent = Util.getFileContent(20);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    record RaceTrack(int width, int height, Set<Coordinate> walls, Coordinate start, Coordinate finish) {

        public static RaceTrack from(final Stream<String> lines) {
            final Set<Coordinate> walls = new HashSet<>();
            Coordinate start = null;
            Coordinate end = null;
            int y = 0;
            int x = 0;
            for (final String line : lines.toList()) {
                x = 0;
                for (final char c : line.toCharArray()) {
                    switch (c) {
                        case 'S' -> start = new Coordinate(x, y);
                        case 'E' -> end = new Coordinate(x, y);
                        case '#' -> walls.add(new Coordinate(x, y));
                        case '.' -> {
                        }
                        default -> throw new IllegalArgumentException();
                    }
                    x++;
                }
                y++;
            }
            return new RaceTrack(x, y, walls, start, end);
        }

        public Map<Coordinate, Integer> distanceMap() {
            Map<Coordinate, Integer> distanceMap = new HashMap<>();
            Coordinate pos = start;
            int distance = 0;
            do {
                distanceMap.put(pos, distance++);
                pos = pos.getNeighbours().stream().filter(Predicate.not(walls::contains)).filter(Predicate.not(distanceMap::containsKey)).findFirst().orElse(null);
            }
            while (pos != null);
            return distanceMap;
        }

        public long countGoodCheats(final Map<Coordinate, Integer> distances, final int maxCheatDuration) {
            Map<List<Coordinate>, Integer> cheats = new HashMap<>();
            for (final Map.Entry<Coordinate, Integer> e : distances.entrySet()) {
                Coordinate cheatStart = e.getKey();
                int distance = e.getValue();
                for (int cheatDuration = 2; cheatDuration <= maxCheatDuration; cheatDuration++) {
                    for (final Coordinate cheatEnd : cheatStart.getNeighbours(cheatDuration)) {
                        Integer newDistance = distances.get(cheatEnd);
                        if (newDistance != null) {
                            cheats.put(List.of(cheatStart, cheatEnd), newDistance - distance - cheatDuration);
                        }
                    }
                }
            }
            return cheats.entrySet().stream().filter(e -> e.getValue() >= 100).count();
        }
    }
}
