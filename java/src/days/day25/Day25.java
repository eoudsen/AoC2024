package days.day25;

import util.Util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day25 {

    public static Long part1(final List<String> input) throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(Day25.class.getClassLoader().getResource("days/day25/input.txt")).toURI());
        String file = Files.readString(path);
        String[] split = file.split("\r\n\r\n");
        List<Piece> pieces = Arrays.stream(split).map(Piece::from).toList();
        // 3663
        return IntStream.range(0, pieces.size()).mapToLong(i -> IntStream.range(i + 1, pieces.size()).filter(j -> pieces.get(i).fits(pieces.get(j))).count()).sum();
    }

    public static Integer part2(final List<String> input) {
        return 0;
    }

    public static void main(String... args) throws URISyntaxException, IOException {
        var fileContent = Util.getFileContent(25);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    enum Type {LOCK, KEY}

    record Piece(Type type, int[] heights) {
        static Piece from(String blob) {
            var lines = blob.split("\n");
            var type = lines[0].startsWith("#") ? Type.LOCK : Type.KEY;
            var heights = new int[5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (lines[i + 1].charAt(j) == '#') {
                        heights[j]++;
                    }
                }
            }
            return new Piece(type, heights);
        }

        boolean fits(Piece other) {
            if (type == other.type) {
                return false;
            }
            return IntStream.range(0, 5).allMatch(i -> heights[i] + other.heights[i] <= 5);
        }
    }
}
