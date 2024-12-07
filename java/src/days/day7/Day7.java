package days.day7;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {

    public static Long part1(final List<String> input) {
        long result = 0;
        EQUATION: for (String equation : input) {
            long eqResult = Long.parseLong(equation.split(":")[0]);
            List<Integer> intList = Arrays.stream(equation.split(": ")[1].split(" ")).map(Integer::parseInt).toList();
            HashSet<Integer> originalPosSet = new HashSet<>();
            for (int i = 0; i < intList.size() - 1; i++) {
                originalPosSet.add(i);
            }
            Set<Set<Integer>> possibleSets = powerSet(originalPosSet);
            for (Set<Integer> possibleSet : possibleSets) {
                long currentValue = intList.getFirst();
                for (int i = 1; i < intList.size(); i++) {
                    if (possibleSet.contains(i - 1)) {
                        currentValue *= intList.get(i);
                    }
                    else {
                        currentValue += intList.get(i);
                    }
                }
                if (currentValue == eqResult) {
                    result += eqResult;
                    continue EQUATION;
                }
            }
        }
        return result; // 6392012777720
    }

    public static Long part2(final List<String> input) {
        long result = 0;
        EQUATION: for (String equation : input) {
            long eqResult = Long.parseLong(equation.split(":")[0]);
            List<Integer> intList = Arrays.stream(equation.split(": ")[1].split(" ")).map(Integer::parseInt).toList();
            HashSet<Integer> originalPosSet = new HashSet<>();
            for (int i = 0; i < intList.size() - 1; i++) {
                originalPosSet.add(i);
            }
            Set<Set<Integer>> possibleSets = powerSet(originalPosSet);
            for (Set<Integer> possibleSet : possibleSets) {

                HashSet<Integer> mulSet = new HashSet<>(originalPosSet);
                mulSet.removeAll(possibleSet);
                Set<Set<Integer>> mulSets = powerSet(mulSet);
                for (Set<Integer> possibleMulSet : mulSets) {
                    long currentValue = intList.getFirst();
                    for (int i = 1; i < intList.size(); i++) {
                        if (possibleSet.contains(i - 1)) {
                            currentValue = Long.parseLong(currentValue + String.valueOf(intList.get(i)));
                        }
                        else if (possibleMulSet.contains(i - 1)) {
                            currentValue *= intList.get(i);
                        }
                        else {
                            currentValue += intList.get(i);
                        }
                    }
                    if (currentValue == eqResult) {
                        result += eqResult;
                        continue EQUATION;
                    }
                }
            }

        }
        return result; // 61561126043536
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(7);
//        var fileContent = Util.getTestFileContent(7);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
}
