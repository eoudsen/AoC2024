package days.day23;

import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day23 {

    public static Integer part1(final List<String> input) {
        HashMap<String, Computer> all = new HashMap<>();
        for (final String line : input) {
            String[] tokens = line.split("-");
            Computer computer1 = all.computeIfAbsent(tokens[0], Computer::new);
            Computer computer2 = all.computeIfAbsent(tokens[1], Computer::new);
            computer1.addNeighbour(computer2);
        }
        TreeSet<Computer> computers = new TreeSet<>(all.values());
        computers.forEach(computer -> Collections.sort(computer.neighbours));
        Set<Cluster> found = new HashSet<>();
        for (Computer computer : computers) {
            if (computer.neighbours.size() > 2) {
                for (int i = 0; i < computer.neighbours.size(); i++) {
                    for (int j = i + 1; j < computer.neighbours.size(); j++) {
                        Computer neighbour1 = computer.neighbours.get(i);
                        Computer neighbour2 = computer.neighbours.get(j);
                        if (neighbour1.neighbours.contains(neighbour2) && neighbour2.neighbours.contains(neighbour1)) {
                            found.add(new Cluster(computer, neighbour1, neighbour2));
                        }
                    }
                }
            }
        }
        found.removeIf(Cluster::wrongOrder);
        found.removeIf(Cluster::doesNotStartWithT);
        return found.size(); // 1368
    }

    public static String part2(final List<String> input) {
        HashMap<String, Computer> all = new HashMap<>();
        for (final String line : input) {
            String[] tokens = line.split("-");
            Computer computer1 = all.computeIfAbsent(tokens[0], Computer::new);
            Computer computer2 = all.computeIfAbsent(tokens[1], Computer::new);
            computer1.addNeighbour(computer2);
        }
        TreeSet<Computer> computers = new TreeSet<>(all.values());
        computers.forEach(computer -> Collections.sort(computer.neighbours));
        Set<Cluster> found = new HashSet<>();
        for (Computer computer : computers) {
            if (computer.neighbours.size() > 2) {
                for (int i = 0; i < computer.neighbours.size(); i++) {
                    for (int j = i + 1; j < computer.neighbours.size(); j++) {
                        Computer neighbour1 = computer.neighbours.get(i);
                        Computer neighbour2 = computer.neighbours.get(j);
                        if (neighbour1.neighbours.contains(neighbour2) && neighbour2.neighbours.contains(neighbour1)) {
                            found.add(new Cluster(computer, neighbour1, neighbour2));
                        }
                    }
                }
            }
        }
        found.removeIf(Cluster::wrongOrder);
        Set<ArrayList<Computer>> nextClusters = new HashSet<>();
        for (Cluster cluster : found) {
            nextClusters.add(new ArrayList<>(List.of(cluster.c1, cluster.c2, cluster.c3)));
        }
        found.removeIf(Cluster::doesNotStartWithT);
        Set<ArrayList<Computer>> currentClusters = null;
        while (!nextClusters.isEmpty()) {
            currentClusters = nextClusters;
            nextClusters = new HashSet<>();
            for (ArrayList<Computer> cluster : currentClusters) {
                Set<Computer> candidates = new HashSet<>();
                cluster.forEach(c -> candidates.addAll(c.neighbours));
                cluster.forEach(candidates::remove);
                candidate: for (Computer candidate : candidates) {
                    for (Computer c : cluster) {
                        if (Collections.binarySearch(c.neighbours, candidate) < 0) {
                            continue candidate;
                        }
                        if (Collections.binarySearch(candidate.neighbours, c) < 0) {
                            continue candidate;
                        }
                    }
                    ArrayList<Computer> nextCluster = new ArrayList<>(cluster);
                    nextCluster.add(candidate);
                    Collections.sort(nextCluster);
                    nextClusters.add(nextCluster);
                }
            }
        }
        return currentClusters.iterator().next().stream()
                .map(computer -> computer.name)
                .collect(Collectors.joining(",")); // dd,ig,il,im,kb,kr,pe,ti,tv,vr,we,xu,zi
    }

    public static void main(String... args) {
        var fileContent = Util.getFileContent(23);
        System.out.println(part1(fileContent));
        System.out.println(part2(fileContent));
    }

    record Cluster(Computer c1, Computer c2, Computer c3) {

        public boolean wrongOrder() {
            return c1.compareTo(c2) >= 0 || c2.compareTo(c3) >= 0;
        }

        public boolean doesNotStartWithT() {
            return !c1.name.startsWith("t") && !c2.name.startsWith("t") && !c3.name.startsWith("t");
        }
    }

    static class Computer implements Comparable<Computer> {

        private String name;
        private List<Computer> neighbours;

        public Computer(String name) {
            this.name = name;
            neighbours = new ArrayList<>();
        }

        public void addNeighbour(Computer neighbour) {
            neighbours.add(neighbour);
            neighbour.neighbours.add(this);
        }

        @Override
        public int compareTo(Computer o) {
            return name.compareTo(o.name);
        }
    }
}
