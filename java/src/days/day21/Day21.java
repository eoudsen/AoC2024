package days.day21;

import util.Util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day21 {

    private static final KeyPadCoordinate KEY_PAD_START = new KeyPadCoordinate(2, 3);
    private static final KeyPadCoordinate DIR_PAD_START = new KeyPadCoordinate(2, 0);

    private static final char[][] KEY_PAD = new char[][]{
            new char[]{'7', '8', '9'},
            new char[]{'4', '5', '6'},
            new char[]{'1', '2', '3'},
            new char[]{'#', '0', 'A'}
    };

    private static final char[][] DIR_PAD = new char[][]{
            new char[]{'#', '^', 'A'},
            new char[]{'<', 'v', '>'}
    };

    private static final Map<Character, KeyPadCoordinate> COORD_OF = Map.of(
            '^', new KeyPadCoordinate(1, 0),
            'A', new KeyPadCoordinate(2, 0),
            '<', new KeyPadCoordinate(0, 1),
            'v', new KeyPadCoordinate(1, 1),
            '>', new KeyPadCoordinate(2, 1)
    );

    private static List<String> toType = new ArrayList<>();
    private static Map<RoadDepth, Long> roadDepthMap = new HashMap<>();

    public static Long part1(final List<String> input) {
        toType = input;
        return solve(2); // 206798
    }

    public static Long part2(final List<String> input) {
        toType = input;
        roadDepthMap = new HashMap<>();
        return solve(25); // 251508572750680
    }

    public static void main(final String... args) {
        var fileContent = Util.getFileContent(21);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    private static int codeToNumber(final String code) {
        return Integer.parseInt(code.substring(0, code.length() - 1));
    }

    private static List<Path> shortestToGoal(final KeyPadCoordinate start, final char goal, final char[][] pad) {
        if (isValidButton(start, pad) && getButton(start, pad) == goal) {
            return List.of(new Path(start, ""));
        }
        Queue<Path> queue = new ArrayDeque<>(List.of(new Path(start, "")));
        List<Path> result = new ArrayList<>();
        int best = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Path p = queue.poll();
            for (final KeyPadCoordinate n : p.end.next()) {
                if (isValidButton(n, pad)) {
                    Path nP = new Path(n, p.steps + p.end.asDir(n));
                    if (getButton(n, pad) == goal) {
                        if (nP.steps.length() < best) {
                            best = nP.steps.length();
                            result.clear();
                            result.add(nP);
                        }
                        else if (nP.steps.length() == best) {
                            result.add(nP);
                        }
                    }
                    else if (nP.steps.length() < best) {
                        queue.add(nP);
                    }
                }
            }
        }
        return result;
    }

    private static List<String> turnToDirections(final String code, final KeyPadCoordinate start, final String core, final char[][] pad) {
        if (code.isEmpty()) {
            return List.of(core);
        }
        char c = code.charAt(0);
        List<String> collect = new ArrayList<>();
        for (final Path p : shortestToGoal(start, c, pad)) {
            collect.addAll(turnToDirections(code.substring(1), p.end, core + p.steps + "A", pad));
        }
        int minLength = collect.stream().mapToInt(String::length).min().orElseThrow();
        return collect.stream().filter(s -> s.length() == minLength).distinct().collect(Collectors.toList());
    }

    private static char getButton(final KeyPadCoordinate c, final char[][] pad) {
        return pad[c.y][c.x];
    }

    private static boolean isValidButton(final KeyPadCoordinate c, final char[][] pad) {
        return 0 <= c.y && c.y < pad.length && 0 <= c.x && c.x < pad[0].length && pad[c.y][c.x] != '#';
    }

    private static long solve(final int depth) {
        long sum = 0;
        for (final String code : toType) {
            long myBest = Long.MAX_VALUE;
            List<String> robot1Moves = turnToDirections(code, KEY_PAD_START, "", KEY_PAD);
            for (final String robot1Move : robot1Moves) {
                long countP = countPushes(robot1Move, depth);
                myBest = Math.min(myBest, countP);
            }
            sum += myBest * codeToNumber(code);
        }
        return sum;
    }

    private static long countPushes(final String robot1Move, final int depth) {
        if (depth == 0) {
            return robot1Move.length();
        }
        RoadDepth key = new RoadDepth(robot1Move, depth);
        if (roadDepthMap.containsKey(key)) {
            return roadDepthMap.get(key);
        }
        KeyPadCoordinate position = DIR_PAD_START;
        long sum = 0L;
        for (final char c : robot1Move.toCharArray()) {
            long min = Long.MAX_VALUE;
            for (String road : roads(c, position)) {
                min = Long.min(min, countPushes(road, depth - 1));
            }
            sum += min;
            position = COORD_OF.get(c);
        }
        roadDepthMap.put(key, sum);
        return sum;
    }

    static List<String> roads(final char to, final KeyPadCoordinate at) {
        return shortestToGoal(at, to, DIR_PAD).stream().map(p -> p.steps() + "A").toList();
    }

    private record KeyPadCoordinate(int x, int y) {
        List<KeyPadCoordinate> next() {
            return List.of(
                    new KeyPadCoordinate(x + 1, y), new KeyPadCoordinate(x, y + 1),
                    new KeyPadCoordinate(x - 1, y), new KeyPadCoordinate(x, y - 1));
        }

        char asDir(KeyPadCoordinate other) {
            if (x < other.x()) {
                return '>';
            } else if (x > other.x()) {
                return '<';
            } else if (y < other.y()) {
                return 'v';
            } else if (y > other.y()) {
                return '^';
            }
            throw new RuntimeException("Should not happen exception");
        }
    }

    private record Path(KeyPadCoordinate end, String steps) {
    }

    private record RoadDepth(String road, int depth) {
    }
}
