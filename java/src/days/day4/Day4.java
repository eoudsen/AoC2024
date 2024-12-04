package days.day4;

import util.NewGrid;
import util.Util;

import java.util.List;

public class Day4 {

    public static Integer part1(final List<String> input) {
        int total = 0;
        char[][] grid = new NewGrid(input).getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 'X') {
                    continue;
                }
                total += checkXMas(grid, i, j);
            }
        }

        return total; // 2447
    }

    public static Integer part2(final List<String> input) {
        int total = 0;
        char[][] grid = new NewGrid(input).getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 'A') {
                    continue;
                }
                if (checkMas(grid, i, j) ) {
                    total++;
                }
            }
        }

        return total; // 1868
    }

    private static int checkXMas(final char[][] grid, final int i, final int j) {
        int xmasCount = 0;
        // Top left
        if (i >= 3 && j >= 3 && grid[i-1][j-1] == 'M' && grid[i-2][j-2] == 'A' && grid[i-3][j-3] == 'S') {
            xmasCount++;
        }
        // Top
        if (i >= 3 && grid[i-1][j] == 'M' && grid[i-2][j] == 'A' && grid[i-3][j] == 'S') {
            xmasCount++;
        }
        // Top right
        if (i >= 3 && j <= grid[0].length - 4 && grid[i-1][j+1] == 'M' && grid[i-2][j+2] == 'A' && grid[i-3][j+3] == 'S') {
            xmasCount++;
        }
        // Right
        if (j <= grid[0].length - 4 && grid[i][j+1] == 'M' && grid[i][j+2] == 'A' && grid[i][j+3] == 'S') {
            xmasCount++;
        }
        // Bottom right
        if (i <= grid.length - 4 && j <= grid[0].length - 4 && grid[i+1][j+1] == 'M' && grid[i+2][j+2] == 'A' && grid[i+3][j+3] == 'S') {
            xmasCount++;
        }
        // Bottom
        if (i <= grid.length - 4 && grid[i+1][j] == 'M' && grid[i+2][j] == 'A' && grid[i+3][j] == 'S') {
            xmasCount++;
        }
        // Bottom left
        if (i <= grid.length - 4 && j >= 3 && grid[i+1][j-1] == 'M' && grid[i+2][j-2] == 'A' && grid[i+3][j-3] == 'S') {
            xmasCount++;
        }
        // Left
        if (j >= 3 && grid[i][j-1] == 'M' && grid[i][j-2] == 'A' && grid[i][j-3] == 'S') {
            xmasCount++;
        }
        return xmasCount;
    }

    private static boolean checkMas(final char[][] grid, final int i, final int j) {
        if (i > 0 && i < grid.length - 1 && j > 0 && j < grid[0].length - 1) {
            // Top
            if (grid[i-1][j-1] == 'M' && grid[i-1][j+1] == 'M' && grid[i+1][j-1] == 'S' && grid[i+1][j+1] == 'S') {
                return true;
            }
            // Right
            if (grid[i-1][j-1] == 'S' && grid[i-1][j+1] == 'M' && grid[i+1][j-1] == 'S' && grid[i+1][j+1] == 'M') {
                return true;
            }
            // Bottom
            if (grid[i-1][j-1] == 'S' && grid[i-1][j+1] == 'S' && grid[i+1][j-1] == 'M' && grid[i+1][j+1] == 'M') {
                return true;
            }
            // Left
            if (grid[i-1][j-1] == 'M' && grid[i-1][j+1] == 'S' && grid[i+1][j-1] == 'M' && grid[i+1][j+1] == 'S') {
                return true;
            }
        }
        return false;
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(4);
//        var fileContent = Util.getTestFileContent(4);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }
}
