package days.day15;

import util.Coordinate;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Day15 {

    public static Long part1(final List<String> input) {
        StringBuilder moves = new StringBuilder();
        boolean nextIsMoves = false;
        Set<Coordinate> boxes = new HashSet<>();
        Set<Coordinate> walls = new HashSet<>();
        Coordinate currentLocation = null;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (nextIsMoves) {
                moves.append(line);
            }
            if (line.isBlank()) {
                nextIsMoves = true;
                continue;
            }
            for (int j = 0; j < line.toCharArray().length; j++) {
                if (line.toCharArray()[j] == '#') {
                    walls.add(new Coordinate(i, j));
                }
                else if (line.toCharArray()[j] == 'O') {
                    boxes.add(new Coordinate(i, j));
                }
                else if (line.toCharArray()[j] == '@') {
                    currentLocation = new Coordinate(i, j);
                }
            }
        }

        MOVES: for (char move : moves.toString().toCharArray()) {
            if (move == 'v') {
                Coordinate targetLocation = new Coordinate(currentLocation.x() + 1, currentLocation.y());
                if (!walls.contains(targetLocation) && !boxes.contains(targetLocation)) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (walls.contains(targetLocation)) {
                    continue;
                }
                int x = 2;
                while (true) {
                    if (walls.contains(new Coordinate(currentLocation.x() + x, currentLocation.y()))) {
                        continue MOVES;
                    }
                    if (!boxes.contains(new Coordinate(currentLocation.x() + x, currentLocation.y()))) {
                        boxes.remove(targetLocation);
                        boxes.add(new Coordinate(currentLocation.x() + x, currentLocation.y()));
                        currentLocation = targetLocation;
                        continue MOVES;
                    }
                    x++;
                }
            }
            else if (move == '>') {
                Coordinate targetLocation = new Coordinate(currentLocation.x(), currentLocation.y() + 1);
                if (!walls.contains(targetLocation) && !boxes.contains(targetLocation)) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (walls.contains(targetLocation)) {
                    continue;
                }
                int x = 2;
                while (true) {
                    if (walls.contains(new Coordinate(currentLocation.x(), currentLocation.y() + x))) {
                        continue MOVES;
                    }
                    if (!boxes.contains(new Coordinate(currentLocation.x(), currentLocation.y() + x))) {
                        boxes.remove(targetLocation);
                        boxes.add(new Coordinate(currentLocation.x(), currentLocation.y() + x));
                        currentLocation = targetLocation;
                        continue MOVES;
                    }
                    x++;
                }
            }
            else if (move == '^') {
                Coordinate targetLocation = new Coordinate(currentLocation.x() - 1, currentLocation.y());
                if (!walls.contains(targetLocation) && !boxes.contains(targetLocation)) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (walls.contains(targetLocation)) {
                    continue;
                }
                int x = 2;
                while (true) {
                    if (walls.contains(new Coordinate(currentLocation.x() - x, currentLocation.y()))) {
                        continue MOVES;
                    }
                    if (!boxes.contains(new Coordinate(currentLocation.x() - x, currentLocation.y()))) {
                        boxes.remove(targetLocation);
                        boxes.add(new Coordinate(currentLocation.x() - x, currentLocation.y()));
                        currentLocation = targetLocation;
                        continue MOVES;
                    }
                    x++;
                }
            }
            else if (move == '<') {
                Coordinate targetLocation = new Coordinate(currentLocation.x(), currentLocation.y() - 1);
                if (!walls.contains(targetLocation) && !boxes.contains(targetLocation)) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (walls.contains(targetLocation)) {
                    continue;
                }
                int x = 2;
                while (true) {
                    if (walls.contains(new Coordinate(currentLocation.x(), currentLocation.y() - x))) {
                        continue MOVES;
                    }
                    if (!boxes.contains(new Coordinate(currentLocation.x(), currentLocation.y() - x))) {
                        boxes.remove(targetLocation);
                        boxes.add(new Coordinate(currentLocation.x(), currentLocation.y() - x));
                        currentLocation = targetLocation;
                        continue MOVES;
                    }
                    x++;
                }
            }
        }

        long result = 0L;
        for (Coordinate box : boxes) {
            result += (box.x() * 100) + box.y();
        }

        return result; // 1430439
    }

    public static Long part2(final List<String> input) {
        Set<Box> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        StringBuilder moves = new StringBuilder();
        boolean nextIsMoves = false;
        Coordinate currentLocation = null;
        int id = 0;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (nextIsMoves) {
                moves.append(line);
            }
            if (line.isBlank()) {
                nextIsMoves = true;
                continue;
            }
            for (int j = 0; j < line.toCharArray().length; j++) {
                if (line.toCharArray()[j] == '#') {
                    walls.add(new Box(new Coordinate(i, j * 2L), new Coordinate(i, (j * 2L) +1), id));
                    id++;
                }
                else if (line.toCharArray()[j] == 'O') {
                    boxes.add(new Box(new Coordinate(i, j * 2L), new Coordinate(i, (j * 2L) + 1), id));
                    id++;
                }
                else if (line.toCharArray()[j] == '@') {
                    currentLocation = new Coordinate(i, j * 2L);
                }
            }
        }

        for (char move : moves.toString().toCharArray()) {
            if (move == 'v') {
                Coordinate targetLocation = new Coordinate(currentLocation.x() + 1, currentLocation.y());
                boolean targetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                boolean targetIsBox = boxes.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                if (!targetIsWall && !targetIsBox) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (targetIsWall) {
                    continue;
                }

                Optional<Box> first = boxes.stream().filter(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation)).findFirst();
                Set<Integer> updatedBoxIds = potentiallyUpdateBoxes('v', walls, boxes, first.get(), new HashSet<>());
                if (updatedBoxIds == null) {
                    continue;
                }
                currentLocation = targetLocation;
                for (Integer updatedBoxId : updatedBoxIds) {
                    Box boxToRemove = null;
                    for (Box box : boxes) {
                        if (box.id == updatedBoxId) {
                            boxToRemove = box;
                            break;
                        }
                    }
                    boxes.remove(boxToRemove);
                    boxes.add(new Box(new Coordinate(boxToRemove.leftSide.x() + 1, boxToRemove.leftSide.y()), new Coordinate(boxToRemove.rightSide.x() + 1, boxToRemove.rightSide.y()), boxToRemove.id));
                }
            }
            else if (move == '>') {
                Coordinate targetLocation = new Coordinate(currentLocation.x(), currentLocation.y() + 1);
                boolean targetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                boolean targetIsBox = boxes.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                if (!targetIsWall && !targetIsBox) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (targetIsWall) {
                    continue;
                }

                Optional<Box> first = boxes.stream().filter(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation)).findFirst();
                Set<Integer> updatedBoxIds = potentiallyUpdateBoxes('>', walls, boxes, first.get(), new HashSet<>());
                if (updatedBoxIds == null) {
                    continue;
                }
                currentLocation = targetLocation;
                for (Integer updatedBoxId : updatedBoxIds) {
                    Box boxToRemove = null;
                    for (Box box : boxes) {
                        if (box.id == updatedBoxId) {
                            boxToRemove = box;
                            break;
                        }
                    }
                    boxes.remove(boxToRemove);
                    boxes.add(new Box(new Coordinate(boxToRemove.leftSide.x(), boxToRemove.leftSide.y() + 1), new Coordinate(boxToRemove.rightSide.x(), boxToRemove.rightSide.y() + 1), boxToRemove.id));
                }
            }
            else if (move == '^') {
                Coordinate targetLocation = new Coordinate(currentLocation.x() - 1, currentLocation.y());
                boolean targetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                boolean targetIsBox = boxes.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                if (!targetIsWall && !targetIsBox) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (targetIsWall) {
                    continue;
                }

                Optional<Box> first = boxes.stream().filter(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation)).findFirst();
                Set<Integer> updatedBoxIds = potentiallyUpdateBoxes('^', walls, boxes, first.get(), new HashSet<>());
                if (updatedBoxIds == null) {
                    continue;
                }
                currentLocation = targetLocation;
                for (Integer updatedBoxId : updatedBoxIds) {
                    Box boxToRemove = null;
                    for (Box box : boxes) {
                        if (box.id == updatedBoxId) {
                            boxToRemove = box;
                            break;
                        }
                    }
                    boxes.remove(boxToRemove);
                    boxes.add(new Box(new Coordinate(boxToRemove.leftSide.x() - 1, boxToRemove.leftSide.y()), new Coordinate(boxToRemove.rightSide.x() - 1, boxToRemove.rightSide.y()), boxToRemove.id));
                }
            }
            else if (move == '<') {
                Coordinate targetLocation = new Coordinate(currentLocation.x(), currentLocation.y() - 1);
                boolean targetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                boolean targetIsBox = boxes.stream().anyMatch(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation));
                if (!targetIsWall && !targetIsBox) {
                    currentLocation = targetLocation;
                    continue;
                }
                if (targetIsWall) {
                    continue;
                }

                Optional<Box> first = boxes.stream().filter(box -> box.leftSide.equals(targetLocation) || box.rightSide.equals(targetLocation)).findFirst();
                Set<Integer> updatedBoxIds = potentiallyUpdateBoxes('<', walls, boxes, first.get(), new HashSet<>());
                if (updatedBoxIds == null) {
                    continue;
                }
                currentLocation = targetLocation;
                for (Integer updatedBoxId : updatedBoxIds) {
                    Box boxToRemove = null;
                    for (Box box : boxes) {
                        if (box.id == updatedBoxId) {
                            boxToRemove = box;
                            break;
                        }
                    }
                    boxes.remove(boxToRemove);
                    boxes.add(new Box(new Coordinate(boxToRemove.leftSide.x(), boxToRemove.leftSide.y() - 1), new Coordinate(boxToRemove.rightSide.x(), boxToRemove.rightSide.y() - 1), boxToRemove.id));
                }
            }
        }

        long result = 0L;
        for (Box box : boxes) {
            result += box.gps();
        }

        return result; // 1458740
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(15);
//        var fileContent = Util.getTestFileContent(15);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    static Set<Integer> potentiallyUpdateBoxes(final char move, final Set<Box> walls, final Set<Box> boxes, final Box boxToMove, final Set<Integer> updatedIds) {
        if (move == 'v') {
            boolean newTargetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x() + 1, boxToMove.leftSide.y())) || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x() + 1, boxToMove.leftSide.y())) || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x() + 1, boxToMove.rightSide.y())) || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x() + 1, boxToMove.rightSide.y())));
            if (newTargetIsWall) {
                return null;
            }
            updatedIds.add(boxToMove.id);
            List<Box> boxesToMove = boxes.stream().filter(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x() + 1, boxToMove.leftSide.y()))
                    || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x() + 1, boxToMove.rightSide.y()))
                    || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x() + 1, boxToMove.leftSide.y()))
                    || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x() + 1, boxToMove.rightSide.y()))).toList();
            if (boxesToMove.isEmpty()) {
                return updatedIds;
            }
            for (Box box : boxesToMove) {
                Set<Integer> updatedBoxes = potentiallyUpdateBoxes(move, walls, boxes, box, updatedIds);
                if (updatedBoxes == null) {
                    return null;
                }
                updatedIds.addAll(updatedBoxes);
            }
        }
        else if (move == '>') {
            boolean newTargetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() + 1)) || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() + 1)) || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() + 1)) || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() + 1)));
            if (newTargetIsWall) {
                return null;
            }
            updatedIds.add(boxToMove.id);
            List<Box> boxesToMove = boxes.stream().filter(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() + 1))
                    || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() + 1))
                    || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() + 1))
                    || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() + 1))).filter(box -> box.id != boxToMove.id).toList();
            if (boxesToMove.isEmpty()) {
                return updatedIds;
            }
            for (Box box : boxesToMove) {
                Set<Integer> updatedBoxes = potentiallyUpdateBoxes(move, walls, boxes, box, updatedIds);
                if (updatedBoxes == null) {
                    return null;
                }
                updatedIds.addAll(updatedBoxes);
            }
        }
        else if (move == '^') {
            boolean newTargetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x() - 1, boxToMove.leftSide.y())) || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x() - 1, boxToMove.leftSide.y())) || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x() - 1, boxToMove.rightSide.y())) || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x() - 1, boxToMove.rightSide.y())));
            if (newTargetIsWall) {
                return null;
            }
            updatedIds.add(boxToMove.id);
            List<Box> boxesToMove = boxes.stream().filter(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x() - 1, boxToMove.leftSide.y()))
                    || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x() - 1, boxToMove.rightSide.y()))
                    || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x() - 1, boxToMove.leftSide.y()))
                    || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x() - 1, boxToMove.rightSide.y()))).toList();
            if (boxesToMove.isEmpty()) {
                return updatedIds;
            }
            for (Box box : boxesToMove) {
                Set<Integer> updatedBoxes = potentiallyUpdateBoxes(move, walls, boxes, box, updatedIds);
                if (updatedBoxes == null) {
                    return null;
                }
                updatedIds.addAll(updatedBoxes);
            }
        }
        else if (move == '<') {
            boolean newTargetIsWall = walls.stream().anyMatch(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() - 1)) || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() - 1)) || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() - 1)) || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() - 1)));
            if (newTargetIsWall) {
                return null;
            }
            updatedIds.add(boxToMove.id);
            List<Box> boxesToMove = boxes.stream().filter(box -> box.leftSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() - 1))
                    || box.leftSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() - 1))
                    || box.rightSide.equals(new Coordinate(boxToMove.leftSide.x(), boxToMove.leftSide.y() - 1))
                    || box.rightSide.equals(new Coordinate(boxToMove.rightSide.x(), boxToMove.rightSide.y() - 1))).filter(box -> box.id != boxToMove.id).toList();
            if (boxesToMove.isEmpty()) {
                return updatedIds;
            }
            for (Box box : boxesToMove) {
                Set<Integer> updatedBoxes = potentiallyUpdateBoxes(move, walls, boxes, box, updatedIds);
                if (updatedBoxes == null) {
                    return null;
                }
                updatedIds.addAll(updatedBoxes);
            }
        }
        return updatedIds;
    }

    static class Box {

        Coordinate leftSide;
        Coordinate rightSide;
        int id;

        Box(final Coordinate left, final Coordinate right, final int id) {
            this.leftSide = left;
            this.rightSide = right;
            this.id = id;
        }

        long gps() {
            return (leftSide.x() * 100) + leftSide.y();
        }
    }
}
