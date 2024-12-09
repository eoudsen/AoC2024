package days.day9;

import util.Util;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    public static Long part1(final List<String> input) {
        List<Integer> fs = getIntegers(input);
        List<Integer> fsCopy = new ArrayList<>(fs);
        for (int i = fs.size() - 1; i > 0; i--) {
            if (fsCopy.get(i) == -1) {
                int _ = fsCopy.remove(i);
                continue;
            }
            int emptyIndex = fsCopy.indexOf(-1);
            if (emptyIndex > 0) {
                fsCopy.set(emptyIndex, fs.get(i));
                int _ = fsCopy.remove(i);
            }
            else {
                break;
            }
        }

        long total = 0;
        for (int i = 0; i < fsCopy.size(); i++) {
            total += (fsCopy.get(i) * i);
        }

        return total; // 6211348208140
    }

    private static List<Integer> getIntegers(final List<String> input) {
        List<Integer> fs = new ArrayList<>();
        boolean processFile = true;
        int index = 0;
        char[] charArray = input.getFirst().toCharArray();
        for (char c : charArray) {
            int length = Integer.parseInt(String.valueOf(c));
            if (processFile) {
                for (int j = 0; j < length; j++) {
                    fs.add(index);
                }
                index++;
                processFile = false;
            } else {
                for (int j = 0; j < length; j++) {
                    fs.add(-1);
                }
                processFile = true;
            }
        }
        return fs;
    }

    public static long part2(final List<String> input) {
        List<Block> blocks = getBlocks(input);
        List<Block> blocksCopy = new ArrayList<>(blocks);
        int lowestValueMoved = blocksCopy.size();
        for (int i = blocks.size() - 1; i > 0; i--) {
            if (!blocksCopy.get(i).filled()) {
                continue;
            }
            if (blocksCopy.get(i).value() > lowestValueMoved) {
                continue;
            }
            int sizeToFit = blocksCopy.get(i).size();
            for (int j = 0; j < i; j++) {
                if (blocksCopy.get(j).filled()) {
                    continue;
                }
                if (blocksCopy.get(j).size < sizeToFit) {
                    continue;
                }
                if (sizeToFit == blocksCopy.get(j).size) {
                    lowestValueMoved = blocksCopy.get(i).value();
                    blocksCopy.set(j, blocksCopy.get(i));
                    blocksCopy.set(i, new Block(sizeToFit, -1, false));
                    break;
                }
                if (sizeToFit < blocksCopy.get(j).size) {
                    lowestValueMoved = blocksCopy.get(i).value();
                    Block newEmptyBlock = new Block(blocksCopy.get(j).size - sizeToFit, -1, false);
                    blocksCopy.add(j, blocksCopy.get(i));
                    blocksCopy.set(j + 1, newEmptyBlock);
                    blocksCopy.set(i + 1, new Block(sizeToFit, -1, false));
                    break;
                }
            }
        }

        long total = 0;
        long indexResult = 0;
        for (Block block : blocksCopy) {
            if (!block.filled()) {
                indexResult += block.size();
                continue;
            }
            for (int i = 0; i < block.size(); i++) {
                total += ((indexResult + i) * block.value());
            }
            indexResult += block.size();
        }
        return total; // 6239783302560
    }

    private static List<Block> getBlocks(final List<String> input) {
        boolean processFile = true;
        int index = 0;
        List<Block> blocks = new ArrayList<>();
        char[] charArray = input.getFirst().toCharArray();
        for (char c : charArray) {
            int length = Integer.parseInt(String.valueOf(c));
            if (processFile) {
                blocks.add(new Block(length, index, true));
                processFile = false;
                index++;
            } else {
                blocks.add(new Block(length, -1, false));
                processFile = true;
            }
        }
        return blocks;
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(9);
//        var fileContent = Util.getTestFileContent(9);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    record Block(int size, int value, boolean filled) {
    }
}
