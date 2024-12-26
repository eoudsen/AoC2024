package days.day22;

import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {

    private static final long MODULO = 16777216L;
    private static final long MULTIPLIER1 = 64L;
    private static final long MULTIPLIER2 = 2048L;
    private static final long DIVISOR = 32L;
    private static final long STEP_COUNT = 2000L;

    public static Long part1(final List<String> input) {
        long[] secretNumbers = input.stream().mapToLong(Long::parseLong).toArray();
        long sum = 0L;
        for (long sn : secretNumbers) {
            sum += generateNextNumber(sn);
        }
        return sum; // 14691757043
    }

    public static Long part2(final List<String> input) {
        long[] secretNumbers = input.stream().mapToLong(Long::parseLong).toArray();
        Map<List<Long>, Long> summingMap = new HashMap<>();
        for (long sn : secretNumbers) {
            generateSequences(sn).forEach((key, value) -> summingMap.compute(key, (_, v) -> v == null ? value : v + value));
        }
        long max = summingMap.values().stream().mapToLong(Long::longValue).max().orElseThrow();
        return max; // 1831
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(22);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    private static long nextNumber(final long seed) {
        long secretNumber = seed;
        secretNumber = ((secretNumber * MULTIPLIER1) ^ secretNumber) % MODULO;
        secretNumber = ((secretNumber / DIVISOR) ^ secretNumber) % MODULO;
        secretNumber = ((secretNumber * MULTIPLIER2) ^ secretNumber) % MODULO;
        return secretNumber;
    }

    private static long generateNextNumber(final long seed) {
        long result = seed;
        for (int i = 0; i < STEP_COUNT; i++) {
            result = nextNumber(result);
        }
        return result;
    }

    private static Map<List<Long>, Long> generateSequences(final long seed) {
        Map<List<Long>, Long> result = new HashMap<>();
        long prevSeed = seed;
        long prevPrice = seed % 10L;
        List<Long> collector = new ArrayList<>();
        for (int i = 0; i < STEP_COUNT; i++) {
            long nextSeed = nextNumber(prevSeed);
            long price = nextSeed % 10L;
            long diff = price - prevPrice;
            collector.add(diff);
            if (collector.size() == 4) {
                result.putIfAbsent(new ArrayList<>(collector), price);
                collector.removeFirst();
            }
            prevPrice = price;
            prevSeed = nextSeed;
        }
        return result;
    }
}
