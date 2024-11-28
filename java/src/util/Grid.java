package util;

import java.util.List;

public class Grid {

    protected final char[][] grid;

    public Grid(final List<String> input) {
        this.grid = new char[input.get(0).length()][input.size()];
        for (int i = 0; i < input.size(); i++) {
            System.arraycopy(input.get(i).toCharArray(), 0, this.grid[i], 0, input.get(i).toCharArray().length);
        }
    }
}
