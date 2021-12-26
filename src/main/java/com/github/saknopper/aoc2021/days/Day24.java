package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class Day24 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        List<List<Integer>> pairs = getPairs();
        Deque<List<Integer>> stack = new ArrayDeque<>();
        Map<Integer, List<Integer>> links = new HashMap<>();

        for (int i = 0; i < pairs.size(); i++) {
            var pair = pairs.get(i);
            var a = pair.get(0);
            var b = pair.get(1);

            if (a > 0) {
                stack.push(List.of(i, b));
            } else {
                var popped = stack.pop();
                links.put(i, List.of(popped.get(0), popped.get(1) + a));
            }
        }

        Map<Integer, Integer> assignments = new HashMap<>();
        generateModelNr(assignments, links, false);

        return Joiner.on("").join(assignments.values());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        List<List<Integer>> pairs = getPairs();
        Deque<List<Integer>> stack = new ArrayDeque<>();
        Map<Integer, List<Integer>> links = new HashMap<>();

        for (int i = 0; i < pairs.size(); i++) {
            var pair = pairs.get(i);
            var a = pair.get(0);
            var b = pair.get(1);

            if (a > 0) {
                stack.push(List.of(i, b));
            } else {
                var popped = stack.pop();
                links.put(i, List.of(popped.get(0), popped.get(1) + a));
            }
        }

        Map<Integer, Integer> assignments = new HashMap<>();
        generateModelNr(assignments, links, true);

        return Joiner.on("").join(assignments.values());
    }

    private void generateModelNr(Map<Integer, Integer> assignments, Map<Integer, List<Integer>> links, boolean minimize) {
        for (var item : links.entrySet()) {
            var i = item.getKey();
            var j = item.getValue().get(0);
            var delta = item.getValue().get(1);

            if (minimize) {
                assignments.put(i, Math.max(1, 1 + delta));
                assignments.put(j, Math.max(1, 1 - delta));
            } else {
                assignments.put(i, Math.min(9, 9 + delta));
                assignments.put(j, Math.min(9, 9 - delta));
            }
        }
    }

    private List<List<Integer>> getPairs() throws IOException, URISyntaxException {
        // lines per block
        final int blockSize = 18;
        Path path = Path.of(getClass().getClassLoader().getResource("day24.txt").toURI());
        List<List<String>> blocks = Lists.partition(Files.readAllLines(path), blockSize);
        // List of pairs of changing numbers within each input block
        List<List<Integer>> pairs = new ArrayList<>();
        for (var block : blocks)
            pairs.add(List.of(getBFromInputLine(block.get(5)), getBFromInputLine(block.get(15))));

        return pairs;
    }

    private Integer getBFromInputLine(String line) {
        String[] splitted = line.split(" ");

        return Integer.valueOf(splitted[2]);
    }
}
