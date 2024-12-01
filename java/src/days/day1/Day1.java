package days.day1;

import util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class Day1 {

    public static Integer part1(final List<String> input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        input.forEach(line -> {
            left.add(Integer.parseInt(line.split(" {3}")[0]));
            right.add(Integer.parseInt(line.split(" {3}")[1]));
        });
        left.sort(Comparator.naturalOrder());
        right.sort(Comparator.naturalOrder());

        int diff = 0;
        for (int i = 0; i < left.size(); i++) {
            diff += Math.abs(left.get(i) - right.get(i));
        }

        return diff; // 2815556
    }

    public static Long part2(final List<String> input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        input.forEach(line -> {
            left.add(Integer.parseInt(line.split(" {3}")[0]));
            right.add(Integer.parseInt(line.split(" {3}")[1]));

        });

        Map<Integer, Long> collect = right.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        AtomicLong similar = new AtomicLong();
        left.forEach(value -> {
            if (collect.containsKey(value)) {
                similar.addAndGet(value * collect.get(value));
            }
        });

        return similar.get(); // 23927637
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(1);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
