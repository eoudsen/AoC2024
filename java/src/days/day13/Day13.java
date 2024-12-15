package days.day13;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {

    static class Machine {

        private long aX;
        private long aY;
        private long bX;
        private long bY;
        private long pX;
        private long pY;

        void addAButton(final long aX, final long aY) {
            this.aX = aX;
            this.aY = aY;
        }

        void addBButton(final long bX, final long bY) {
            this.bX = bX;
            this.bY = bY;
        }

        void addPrize(final long pX, final long pY) {
            this.pX = pX;
            this.pY = pY;
        }

        long numberOfTokens() {
            // Cramer
            final long denominator = aX * bY - bX * aY;
            final long aPushes = bY * pX - bX * pY;
            final long bPushes = aX * pY - aY * pX;
            if (aPushes % denominator == 0 && bPushes % denominator == 0) {
                return (3 * aPushes + bPushes) / denominator;
            }
            return 0;
        }

    }

    public static Long part1(final List<String> input) {
        long result = 0;
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 4) {
            Machine newMachine = new Machine();
            List<Long> aButton = Arrays.stream(input.get(i).split(",")).map(part -> Long.parseLong(part.split("\\+")[1])).toList();
            List<Long> bButton = Arrays.stream(input.get(i + 1).split(",")).map(part -> Long.parseLong(part.split("\\+")[1])).toList();
            List<Long> prize = Arrays.stream(input.get(i + 2).split(",")).map(part -> Long.parseLong(part.split("=")[1])).toList();
            newMachine.addAButton(aButton.get(0), aButton.get(1));
            newMachine.addBButton(bButton.get(0), bButton.get(1));
            newMachine.addPrize(prize.get(0), prize.get(1));
            machines.add(newMachine);
        }

        for (Machine machine : machines) {
            result += machine.numberOfTokens();
        }

        return result; // 28059
    }

    public static Long part2(final List<String> input) {
        long result = 0;
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 4) {
            Machine newMachine = new Machine();
            List<Long> aButton = Arrays.stream(input.get(i).split(",")).map(part -> Long.parseLong(part.split("\\+")[1])).toList();
            List<Long> bButton = Arrays.stream(input.get(i + 1).split(",")).map(part -> Long.parseLong(part.split("\\+")[1])).toList();
            List<Long> prize = Arrays.stream(input.get(i + 2).split(",")).map(part -> Long.parseLong(part.split("=")[1])).toList();
            newMachine.addAButton(aButton.get(0), aButton.get(1));
            newMachine.addBButton(bButton.get(0), bButton.get(1));
            newMachine.addPrize(prize.get(0) + 10000000000000L, prize.get(1) + 10000000000000L);
            machines.add(newMachine);
        }

        for (Machine machine : machines) {
            result += machine.numberOfTokens();
        }

        return result; // 102255878088512
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(13);
//        var fileContent = Util.getTestFileContent(13);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
