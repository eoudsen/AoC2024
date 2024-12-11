package days.day11;

import util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 {

    public static Long part1(final List<String> input) {
        Map<Long, Long> rocks = Arrays.stream(input.getFirst().split(" ")).map(Long::parseLong).collect(Collectors.toMap(Long::longValue, _ -> 1L));
        return countRocks(rocks, 25); // 193269
    }

    public static Long part2(final List<String> input) {
        Map<Long, Long> rocks = Arrays.stream(input.getFirst().split(" ")).map(Long::parseLong).collect(Collectors.toMap(Long::longValue, _ -> 1L));
        return countRocks(rocks, 75); // 228449040027793
    }

    private static Long countRocks(final Map<Long, Long> ogRocks, final int blinks) {
        HashMap<Long, Long> rocks = new HashMap<>(ogRocks);
        for (int i = 0; i < blinks; i++) {
            HashMap<Long, Long> newRocks = new HashMap<>();
            rocks.forEach((rockIndex, rockValue) -> {
                if (rockIndex == 0) {
                    newRocks.put(1L, newRocks.getOrDefault(1L, 0L) + rockValue);
                }
                else if (String.valueOf(rockIndex).toCharArray().length % 2 == 0) {
                    char[] indexChars = String.valueOf(rockIndex).toCharArray();
                    StringBuilder left = new StringBuilder();
                    for (int j = 0; j < indexChars.length / 2; j++) {
                        left.append(indexChars[j]);
                    }
                    StringBuilder right = new StringBuilder();
                    for (int j = indexChars.length / 2; j < indexChars.length; j++) {
                        right.append(indexChars[j]);
                    }
                    long leftLong = Long.parseLong(left.toString());
                    long rightLong = Long.parseLong(right.toString());
                    newRocks.put(leftLong, newRocks.getOrDefault(leftLong, 0L) + rockValue);
                    newRocks.put(rightLong, newRocks.getOrDefault(rightLong, 0L) + rockValue);
                }
                else {
                    newRocks.put(rockIndex * 2024L, newRocks.getOrDefault(rockIndex * 2024L, 0L) + rockValue);
                }
            });
            rocks = newRocks;
        }
        return rocks.values().stream().reduce(0L, Long::sum);
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(11);
//        var fileContent = Util.getTestFileContent(11);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
