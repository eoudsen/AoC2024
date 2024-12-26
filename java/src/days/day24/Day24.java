package days.day24;

import util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24 {

    private static final Pattern GATE_PATTERN = Pattern.compile("([\\d\\w]+) (AND|OR|XOR) ([\\d\\w]+) -> ([\\d\\w]+)");
    private static final Pattern WIRE_PATTERN = Pattern.compile("([\\d\\w]+): (\\d)");

    public static Long part1(final List<String> input) {
        boolean parsingWires = true;
        HashMap<String, Long> initialValues = new HashMap<>();
        HashMap<String, Wire> wires = new HashMap<>();
        for (final String line : input) {
            if (line.isEmpty()) {
                parsingWires = false;
                continue;
            }
            if (parsingWires) {
                Matcher matcher = WIRE_PATTERN.matcher(line);
                if (matcher.matches()) {
                    initialValues.put(matcher.group(1), Long.valueOf(matcher.group(2)));
                }
            }
            else {
                Matcher matcher = GATE_PATTERN.matcher(line);
                if (matcher.matches()) {
                    Wire wireA = wires.computeIfAbsent(matcher.group(1), s -> new Wire(matcher.group(1)));
                    Wire wireB = wires.computeIfAbsent(matcher.group(3), s -> new Wire(matcher.group(3)));
                    Wire output = wires.computeIfAbsent(matcher.group(4), s -> new Wire(matcher.group(4)));
                    Gate gate = new Gate(GateType.valueOf(matcher.group(2)), wireA, wireB, output);
                    wireA.gates.add(gate);
                    wireB.gates.add(gate);
                }

            }
        }

        initialValues.forEach((k, v) -> wires.get(k).setValue(v));
        return getValue(wires); // 48063513640678
    }

    public static String part2(final List<String> input) {
        boolean parsingWires = true;
        HashMap<String, Long> initialValues = new HashMap<>();
        HashMap<String, Wire> wires = new HashMap<>();
        for (final String line : input) {
            if (line.isEmpty()) {
                parsingWires = false;
                continue;
            }
            if (parsingWires) {
                Matcher matcher = WIRE_PATTERN.matcher(line);
                if (matcher.matches()) {
                    initialValues.put(matcher.group(1), Long.valueOf(matcher.group(2)));
                }
            }
            else {
                Matcher matcher = GATE_PATTERN.matcher(line);
                if (matcher.matches()) {
                    Wire wireA = wires.computeIfAbsent(matcher.group(1), s -> new Wire(matcher.group(1)));
                    Wire wireB = wires.computeIfAbsent(matcher.group(3), s -> new Wire(matcher.group(3)));
                    Wire output = wires.computeIfAbsent(matcher.group(4), s -> new Wire(matcher.group(4)));
                    Gate gate = new Gate(GateType.valueOf(matcher.group(2)), wireA, wireB, output);
                    wireA.gates.add(gate);
                    wireB.gates.add(gate);
                }

            }
        }
        initialValues.forEach((k, v) -> wires.get(k).setValue(v));
        return traceAll(wires); // hqh,mmk,pvb,qdq,vkq,z11,z24,z38
    }

    static long getValue(final Map<String, Wire> wires) {
        long value = 0L;
        long bit = 1L;
        int i = 0;
        do {
            Wire wire = wires.get(("z" + "%02d").formatted(i));
            if (wire == null) {
                break;
            }
            if (wire.getValue() == 1) {
                value |= bit;
            }
            ++i;
            bit <<= 1;
        }
        while (true);
        return value;
    }

    static String traceAll(final Map<String, Wire> wires) {
        Set<String> swaps = new HashSet<>();
        int i = 0;
        do {
            i++;
        }
        while (cell(i, swaps, wires));
        return swaps.stream().sorted().collect(Collectors.joining(","));
    }

    static boolean cell(final int i, final Set<String> swaps, final Map<String, Wire> wires) {
        var a = wires.get(("x" + "%02d").formatted(i));
        if (a == null) {
            return false;
        }
        Gate xorGate = a.gates().stream().filter(g -> g.type() == GateType.XOR).findFirst().orElseThrow();
        Wire xorOutput = xorGate.output();
        Gate andGate = a.gates().stream().filter(g -> g.type() == GateType.AND).findFirst().orElseThrow();
        Wire andOutput = andGate.output();
        if (xorOutput.gates().stream().noneMatch(g -> g.type() == GateType.XOR) && andOutput.gates().stream().anyMatch(g -> g.type() == GateType.XOR)) {
            swaps.add(xorOutput.name());
            swaps.add(andOutput.name());
        }
        String outputName = xorOutput.gates().stream().filter(g -> g.type() == GateType.XOR).findFirst().map(g -> g.output().name()).orElse(null);
        if (outputName != null && !outputName.equals("z%02d".formatted(i))) {
            swaps.add(outputName);
            swaps.add("z%02d".formatted(i));
        }
        return true;
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(24);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    enum GateType {
        AND {
            @Override
            Long apply(Long a, Long b) {
                return a & b;
            }
        }, OR {
            @Override
            Long apply(Long a, Long b) {
                return a | b;
            }
        }, XOR {
            @Override
            Long apply(Long a, Long b) {
                return a ^ b;
            }
        };

        abstract Long apply(Long a, Long b);
    }

    static class State {
        Long value;

        void setValue(final long value) {
            if (this.value != null) {
                throw new IllegalStateException("Should not happen exception");
            }
            this.value = value;
        }
    }

    record Wire(String name, State state, Set<Gate> gates) {

        Wire(final String name) {
            this(name, new State(), new HashSet<>());
        }

        Long getValue() {
            return state.value;
        }

        void setValue(final long value) {
            state.setValue(value);
            for (var gate : gates) {
                gate.trigger();
            }
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wire wire = (Wire) o;
            return Objects.equals(name, wire.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
    }

    record Gate(GateType type, Wire inputA, Wire inputB, Wire output) {
        void trigger() {
            if (inputA.getValue() != null && inputB.getValue() != null) {
                output.setValue(type.apply(inputA.getValue(), inputB.getValue()));
            }
        }
    }

}
