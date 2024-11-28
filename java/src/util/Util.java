package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Util {

    public static List<String> getFileContent(final int day) {
        URL resource = Util.class.getClassLoader().getResource("days/day" + day + "/input.txt");
        return getContent(resource);
    }

    public static List<Integer> getIntegerFileContent(final int day) {
        return getFileContent(day).stream().map(Integer::parseInt).toList();
    }

    public static List<String> getTestFileContent(final int day) {
        URL resource = Util.class.getClassLoader().getResource("days/day" + day + "/test.txt");
        return getContent(resource);
    }

    public static List<Integer> getTestIntegerFileContent(final int day) {
        return getTestFileContent(day).stream().map(Integer::parseInt).toList();
    }

    private static List<String> getContent(final URL resource) {
        assert resource != null;
        try (var bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            return bufferedReader.lines().toList();
        } catch (final IOException e) {
            System.out.format("I/O error: %s%n", e);
        }
        System.exit(1);
        return null;
    }
}
