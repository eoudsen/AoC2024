package days.day2;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    public static Integer part1(final List<String> input) {
        int safe = 0;

        for (String report : input) {
            List<Integer> integerReport = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            if (check(integerReport)) {
                safe++;
            }
        }
        return safe; // 598
    }

    public static Integer part2(final List<String> input) {
        int safe = 0;

        for (String report : input) {
            List<Integer> integerReport = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            if (check(integerReport)) {
                safe++;
            }
            else {
                for (int i = 0; i < integerReport.size(); i++) {
                    ArrayList<Integer> newReport = new ArrayList<>();
                    for (int i1 = 0; i1 < integerReport.size(); i1++) {
                        if (i1 != i) {
                            newReport.add(integerReport.get(i1));
                        }
                    }
                    if (check(newReport)) {
                        safe++;
                        break;
                    }
                }
            }
        }

        return safe; // 634
    }

    private static boolean check(final List<Integer> report) {
        int diff = report.get(0) - report.get(1);
        if (diff == 0) {
            return false;
        }

        boolean negative = diff <= 0;
        for (int i = 0; i < report.size() - 1; i++) {
            int follow = report.get(i) - report.get(i + 1);
            int abs = Math.abs(follow);
            if (unsafeReport(negative, follow, abs)) {
                return false;
            }
        }
        return true;
    }

    private static boolean unsafeReport(final boolean negative, final int follow, final int abs) {
        return (negative && follow >= 0) || (!negative && follow <= 0) || abs > 3 || abs < 1;
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(2);
//        var fileContent = Util.getTestFileContent(2);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
