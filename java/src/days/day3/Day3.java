package days.day3;

import util.Util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static long part1(final List<String> input) {
        String line = input.getFirst();
        Pattern pattern = Pattern.compile("mul\\((?<one>\\d+),(?<two>\\d+)\\)");
        Matcher matcher = pattern.matcher(line);
        long total = 0;
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group("one")) * Integer.parseInt(matcher.group("two"));
        }
        return total; // 180233229
    }

    public static long part2(final List<String> input) {
        String line = input.getFirst();
        Pattern pattern = Pattern.compile("(?<name>(mul|do|don't))\\((?<one>\\d+)*,*(?<two>\\d+)*\\)");
        Matcher matcher = pattern.matcher(line);
        long total = 0;
        boolean enabled = true;
        while (matcher.find()) {
            if (matcher.group("name").equals("do")) {
                enabled = true;
                continue;
            }
            if (matcher.group("name").equals("don't")) {
                enabled = false;
                continue;
            }
            if (enabled) {
                total += Integer.parseInt(matcher.group("one")) * Integer.parseInt(matcher.group("two"));
            }
        }
        return total; // 95411583
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(3);
//        var fileContent = Util.getTestFileContent(3);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
