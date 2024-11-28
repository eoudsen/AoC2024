package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListGrid {

    private List<List<Character>> listGrid = new ArrayList<>();

    public ListGrid(final List<String> input) {
        for (final String line : input) {
            char[] charArray = line.toCharArray();
            final List<Character> charList = new ArrayList<>();
            for (char c : charArray) {
                charList.add(c);
            }
            this.listGrid.add(charList);
        }
    }

    public void expandX() {
        final List<Integer> intList = getXList();
        for (int i = intList.size() - 1; i >= 0; i--) {
            this.listGrid.add(intList.get(i), new ArrayList<>(Collections.nCopies(this.listGrid.get(0).size(), '.')));
        }
    }

    public void expandY() {
        final List<Integer> intList = getYList();

        for (int i = intList.size() - 1; i >= 0; i--) {
            for (final List<Character> x : listGrid) {
                x.add(intList.get(i), '.');
            }
        }
    }

    public List<Integer> getXList() {
        final List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < listGrid.size(); i++) {
            if (listGrid.get(i).stream().allMatch(c -> c == '.')) {
                intList.add(i);
            }
        }
        return intList;
    }

    public List<Integer> getYList() {
        final List<Integer> intList = new ArrayList<>();
        OUTER: for (int i = 0; i < listGrid.get(0).size(); i++) {
            for (List<Character> characters : listGrid) {
                if (characters.get(i) == '#') {
                    continue OUTER;
                }
            }
            intList.add(i);
        }
        return intList;
    }

    public List<Coordinate> getGalaxies() {
        final List<Coordinate> galaxyList = new ArrayList<>();
        for (int x = 0; x < listGrid.size(); x++) {
            for (int y = 0; y < listGrid.get(x).size(); y++) {
                if (listGrid.get(x).get(y) == '#') {
                    galaxyList.add(new Coordinate(x, y));
                }
            }
        }
        return galaxyList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final List<Character> x : listGrid) {
            for (final Character y : x) {
                builder.append(y);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
