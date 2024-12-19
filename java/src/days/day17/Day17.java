package days.day17;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public static String part1(final List<String> input) {
        int registerA = Integer.parseInt(input.get(0).split("Register A: ")[1]);
        int registerB = Integer.parseInt(input.get(1).split("Register B: ")[1]);
        int registerC = Integer.parseInt(input.get(2).split("Register C: ")[1]);
        List<Integer> instructions = Collections.unmodifiableList(readIntLines(input.get(4).split("Program: ")[1]).getFirst());

        Computer computer = new Computer(registerA, registerB, registerC, instructions);
        computer.execute();
        return computer.print(); // 1,7,6,5,1,0,5,0,7
    }

    public static Long part2(final List<String> input) {
        int registerB = Integer.parseInt(input.get(1).split("Register B: ")[1]);
        int registerC = Integer.parseInt(input.get(2).split("Register C: ")[1]);
        List<Integer> instructions = Collections.unmodifiableList(readIntLines(input.get(4).split("Program: ")[1]).getFirst());

        List<Integer> remainingProgram = new ArrayList<>(instructions);
        List<Integer> program = new ArrayList<>();
        long registerAValue = 0L;
        while (!remainingProgram.isEmpty()) {
            registerAValue--;
            program.addFirst(remainingProgram.removeLast());
            String programString = program.stream().map(Long::toString).collect(Collectors.joining(","));
            Computer computer;
            do {
                registerAValue++;
                computer = new Computer(registerAValue, registerB, registerC, instructions);
                computer.execute(true, program);
            }
            while (!computer.print().equals(programString));
            if (!remainingProgram.isEmpty()) {
                registerAValue = registerAValue << 3;
            }
        }
        return registerAValue; // 236555995274861
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(17);
//        var fileContent = Util.getTestFileContent(17);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    private static List<List<Integer>> readIntLines(final String data) {
        return data.lines().map(l -> l.split(",")).map(split -> {
            List<Integer> lst = new ArrayList<>(split.length);
            Arrays.stream(split).forEach(l -> lst.add(Integer.parseInt(l)));
            return lst;
        }).toList();
    }

    private static class Computer {
        private long registerA;
        private long registerB;
        private long registerC;
        private final List<Integer> opcodes;
        private final List<Integer> out = new ArrayList<>();
        private int instructionPointer;

        public Computer(final long registerA, final int registerB, final int registerC, final List<Integer> opcodes) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.opcodes = opcodes;
            this.instructionPointer = 0;
        }

        public void execute() {
            execute(false, List.of());
        }

        public void execute(final boolean earlyStop, final List<Integer> expected) {
            while (this.instructionPointer < this.opcodes.size()) {
                final int literalOperator = this.opcodes.get(this.instructionPointer + 1);
                final long comboOperator = getCombo(this.opcodes.get(this.instructionPointer + 1));
                boolean skipIncrease = false;
                switch (this.opcodes.get(this.instructionPointer)) {
                    case 0 -> {
                        final long den = (long) Math.pow(2, comboOperator);
                        this.registerA = this.registerA / den;
                    }
                    case 1 -> this.registerB = this.registerB ^ ((long) literalOperator);
                    case 2 -> this.registerB = comboOperator % 8;
                    case 3 -> {
                        if (this.registerA != 0) {
                            this.instructionPointer = literalOperator;
                            skipIncrease = true;
                            if (earlyStop && !outPutMatches(expected)) {
                                return;
                            }
                        }
                    }
                    case 4 -> this.registerB = this.registerB ^ this.registerC;
                    case 5 -> this.out.add((int) (comboOperator % 8L));
                    case 6 -> {
                        final long den = (long) Math.pow(2, comboOperator);
                        this.registerB = this.registerA / den;
                    }
                    case 7 -> {
                        final long den = (long) Math.pow(2, comboOperator);
                        this.registerC = this.registerA / den;
                    }
                }
                if (!skipIncrease) {
                    this.instructionPointer += 2;
                }
            }
        }

        public boolean outPutMatches(final List<Integer> expected) {
            if (this.out.size() > expected.size()) {
                return false;
            }
            for (int i = 0; i < this.out.size(); i++) {
                if (!this.out.get(i).equals(expected.get(i))) {
                    return false;
                }
            }
            return true;
        }

        public long getCombo(final int value) {
            return switch (value % 8) {
                case 0, 1, 2, 3 -> value;
                case 4 -> this.registerA;
                case 5 -> this.registerB;
                case 6 -> this.registerC;
                case 7 -> Long.MIN_VALUE;
                default -> throw new IllegalStateException("Impossible to reach");
            };
        }

        public String print() {
            return String.join(",", this.out.stream().map(Long::toString).toList());
        }
    }
}
