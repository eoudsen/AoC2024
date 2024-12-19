package days.day19;

import util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day19 {

    private final Map<String, Long> waysToReach = new HashMap<>();

    public static Long part1(final List<String> input) {
        final Set<String> patterns = new HashSet<>();
        final Set<String> designs = new HashSet<>();
        boolean foundEmptyLine = false;
        for (String string : input) {
            if (string.isBlank()) {
                foundEmptyLine = true;
                continue;
            }
            if (!foundEmptyLine) {
                patterns.addAll(Arrays.stream(string.split(", ")).toList());
            }
            else {
                designs.add(string);
            }
        }
        Day19 day19 = new Day19();
        return designs.stream()
                .filter(design -> day19.numberOfWaysToReach(design, patterns) > 0)
                .count();
    }

    public static Long part2(final List<String> input) {
        final Set<String> patterns = new HashSet<>();
        final Set<String> designs = new HashSet<>();
        boolean foundEmptyLine = false;
        for (String string : input) {
            if (string.isBlank()) {
                foundEmptyLine = true;
                continue;
            }
            if (!foundEmptyLine) {
                patterns.addAll(Arrays.stream(string.split(", ")).toList());
            }
            else {
                designs.add(string);
            }
        }
        Day19 day19 = new Day19();
        return designs.stream()
                .map(design -> day19.numberOfWaysToReach(design, patterns))
                .reduce(Long::sum)
                .orElse(-1L);
    }

    private long numberOfWaysToReach(final String design, final Set<String> availablePatterns) {
        if (design.isEmpty()) {
            return 1;
        }
        if (!waysToReach.containsKey(design)) {
            long count = availablePatterns.stream()
                    .filter(design::startsWith)
                    .map(e -> numberOfWaysToReach(design.substring(e.length()), availablePatterns))
                    .reduce(Long::sum)
                    .orElse(0L);
            waysToReach.put(design, count);
            return count;
        }
        return waysToReach.get(design);
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(19);
//        var fileContent = Util.getTestFileContent(19);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
