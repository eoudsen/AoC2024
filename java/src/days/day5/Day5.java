package days.day5;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {

    public static Integer part1(final List<String> input) {
        boolean currentParsingRules = true;
        int result = 0;
        List<Rule> rules = new ArrayList<>();
        RULES: for (final String line : input) {
            if (line.isEmpty()) {
                currentParsingRules = false;
                continue;
            }

            if (currentParsingRules) {
                rules.add(new Rule(line));
                continue;
            }
            // Process booklets
            List<Integer> intList = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
            for (Rule rule : rules) {
                if (!rule.validate(intList)) {
                    continue RULES;
                }
            }
            result += intList.get(intList.size() / 2);

        }
        return result; // 6041
    }

    public static Integer part2(final List<String> input) {
        boolean currentParsingRules = true;
        int result = 0;
        List<Rule> rules = new ArrayList<>();
        RULES: for (final String line : input) {
            if (line.isEmpty()) {
                currentParsingRules = false;
                continue;
            }

            if (currentParsingRules) {
                rules.add(new Rule(line));
                continue;
            }
            // Process booklets
            boolean incorrect = false;
            List<Integer> intList = new ArrayList<>(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            LOOP: while (true) {
                for (Rule rule : rules) {
                    if (!rule.validate(intList)) {
                        int rightIndex = intList.indexOf(rule.getRight());
                        int leftIndex = intList.indexOf(rule.getLeft());
                        int ret = intList.remove(rightIndex);
                        if (leftIndex + 1 < intList.size()) {
                            intList.add(leftIndex + 1, rule.getRight());
                        }
                        else {
                            intList.add(rule.getRight());
                        }
                        incorrect = true;
                        continue LOOP;
                    }
                }
                break;
            }
            if (incorrect) {
                result += intList.get(intList.size() / 2);
            }
        }
        return result; // 4884
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(5);
//        var fileContent = Util.getTestFileContent(5);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
