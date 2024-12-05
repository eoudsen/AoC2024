package days.day5;

import java.util.List;

public class Rule {

    private int left;
    private int right;

    public Rule(final String line) {
        this.left = Integer.parseInt(line.split("\\|")[0]);
        this.right = Integer.parseInt(line.split("\\|")[1]);
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public boolean validate(final List<Integer> intList) {
        if (intList.contains(left) && intList.contains(right)) {
            return intList.indexOf(left) < intList.indexOf(right);
        }
        return true;
    }
}