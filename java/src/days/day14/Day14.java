package days.day14;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day14 {

    static final int MAX_X = 101;
    static final int MAX_Y = 103;
//    static final int MAX_X = 11;
//    static final int MAX_Y = 7;

    public static Integer part1(final List<String> input) {
        Set<Robot> robots = new HashSet<>();
        for (String line : input) {
            List<Integer> robotStart = Arrays.stream(line.split(" ")[0].split("=")[1].split(",")).map(Integer::parseInt).toList();
            List<Integer> robotVelocity = Arrays.stream(line.split(" ")[1].split("=")[1].split(",")).map(Integer::parseInt).toList();
            Robot newRobot = new Robot(robotStart.get(0), robotStart.get(1), robotVelocity.get(0), robotVelocity.get(1));
            robots.add(newRobot);
        }

        for (int i = 0; i < 100; i++) {
            for (Robot robot : robots) {
                robot.blink();
            }
        }

        List<Integer> quadrantCounts = new ArrayList<>();
        quadrantCounts.add(0);
        quadrantCounts.add(0);
        quadrantCounts.add(0);
        quadrantCounts.add(0);
        // Top left 0; Top right 1; Bottom left 2; Bottom right 3;
        for (Robot robot : robots) {
            if (robot.locX < MAX_X / 2 && robot.locY < MAX_Y / 2) {
                quadrantCounts.set(0, quadrantCounts.get(0) + 1);
            }
            else if (robot.locX < MAX_X / 2 && robot.locY > MAX_Y / 2) {
                quadrantCounts.set(2, quadrantCounts.get(2) + 1);
            }
            else if (robot.locX > MAX_X / 2 && robot.locY < MAX_Y / 2) {
                quadrantCounts.set(1, quadrantCounts.get(1) + 1);
            }
            else if (robot.locX > MAX_X / 2 && robot.locY > MAX_Y / 2) {
                quadrantCounts.set(3, quadrantCounts.get(3) + 1);
            }
        }
        // 217328832
        return quadrantCounts.get(0) * quadrantCounts.get(1) * quadrantCounts.get(2) * quadrantCounts.get(3);
    }

    public static Integer part2(final List<String> input) throws PythonExecutionException, IOException {
        Set<Robot> robots = new HashSet<>();
        for (String line : input) {
            List<Integer> robotStart = Arrays.stream(line.split(" ")[0].split("=")[1].split(",")).map(Integer::parseInt).toList();
            List<Integer> robotVelocity = Arrays.stream(line.split(" ")[1].split("=")[1].split(",")).map(Integer::parseInt).toList();
            Robot newRobot = new Robot(robotStart.get(0), robotStart.get(1), robotVelocity.get(0), robotVelocity.get(1));
            robots.add(newRobot);
        }
        int blinkCounter = 0;
        while (blinkCounter < 10000) {
            for (Robot robot : robots) {
                robot.blink();
            }
            blinkCounter++;
            boolean plot = false;
            if (blinkCounter > 7411) {
                plot = true;
            }
            if (plot) {
                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();
                for (Robot robot : robots) {
                    x.add(robot.locX);
                    y.add(robot.locY);
                }

                Plot plt = Plot.create();
                plt.plot().add(x, y, "o");
                plt.legend().loc("upper right");
                plt.title("Tree? Blinks: " + blinkCounter);
                plt.show();
            }

        }

        return 7412; // 7412
    }

    public static void main(String... args) throws PythonExecutionException, IOException {
        var fileContent = Util.getFileContent(14);
//        var fileContent = Util.getTestFileContent(14);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    static class Robot {

        private int locX;
        private int locY;
        private int velX;
        private int velY;

        Robot(final int locX, final int locY, final int velX, final int velY) {
            this.locX = locX;
            this.locY = locY;
            this.velX = velX;
            this.velY = velY;
        }

        void blink() {
            if (this.velX >= 0) {
                this.locX += this.velX;
                if (this.locX > MAX_X - 1) {
                    this.locX = this.locX - MAX_X;
                }
            }
            else {
                this.locX += this.velX;
                if (this.locX < 0) {
                    this.locX = this.locX + MAX_X;
                }
            }
            if (this.velY >= 0) {
                this.locY += this.velY;
                if (this.locY > MAX_Y - 1) {
                    this.locY = this.locY - MAX_Y;
                }
            }
            else {
                this.locY += this.velY;
                if (this.locY < 0) {
                    this.locY = this.locY + MAX_Y;
                }
            }
        }
    }
}
