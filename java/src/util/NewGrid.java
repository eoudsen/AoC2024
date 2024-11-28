package util;

import java.util.List;

public class NewGrid {

    protected final char[][] grid;

    public NewGrid(final List<String> input) {
        this.grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            System.arraycopy(input.get(i).toCharArray(), 0, this.grid[i], 0, input.get(i).toCharArray().length);
        }
    }

    public NewGrid(final char[][] newGrid) {
        this.grid = newGrid;
    }

    public char[][] getGrid() {
        return this.grid;
    }

    public char[] getYColumn(final int y) {
        final char[] column = new char[grid.length];
        for (int i = 0; i < this.grid.length; i++) {
            column[i] = this.grid[i][y];
        }
        return column;
    }
}
