package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day18.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        List<Node> sumResult = new ArrayList<>();
        while (!lines.isEmpty()) {
            sumResult = performSum(sumResult, parseLine(lines.remove(0)));
            performReduce(sumResult);
        }

        return String.valueOf(magnitude(sumResult));
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day18.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        var maxMagnitude = Integer.MIN_VALUE;

        for (var i = 0; i < lines.size(); i++) {
            for (var j = 0; j < lines.size(); j++) {
                if (i == j)
                    continue;

                List<Node> sumResult = performSum(parseLine(lines.get(i)), parseLine(lines.get(j)));
                performReduce(sumResult);

                var magnitude = magnitude(sumResult);
                if (magnitude > maxMagnitude)
                    maxMagnitude = magnitude;
            }
        }

        return String.valueOf(maxMagnitude);
    }

    private void performReduce(List<Node> list) {
        var reduced = false;
        do {
            reduced = explode(list);
            if (reduced)
                continue;
            reduced = split(list);
        } while (reduced);
    }

    private boolean explode(List<Node> list) {
        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).depth != 4)
                continue;

            var left = list.get(i);
            var newDepth = left.depth - 1;
            if (i != 0)
                list.set(i - 1, new Node(list.get(i - 1).depth, list.get(i - 1).value + left.value));

            var right = list.get(i + 1);
            if (i < list.size() - 2)
                list.set(i + 2, new Node(list.get(i + 2).depth, list.get(i + 2).value + right.value));

            list.set(i, new Node(newDepth, 0));
            list.remove(i + 1);

            return true;
        }

        return false;
    }

    private boolean split(List<Node> list) {
        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).value > 9) {
                var nodeToSplit = list.get(i);
                var splitDepth = nodeToSplit.depth + 1;
                var splitValue = (float) nodeToSplit.value;

                list.set(i, new Node(splitDepth, (int) Math.floor(splitValue / 2)));
                list.add(i + 1, new Node(splitDepth, (int) Math.ceil(splitValue / 2)));

                return true;
            }
        }

        return false;
    }

    private List<Node> performSum(List<Node> lh, List<Node> rh) {
        if (lh.isEmpty())
            return rh;

        return Stream.concat(lh.stream(), rh.stream()).map(e -> new Node(e.depth + 1, e.value)).collect(Collectors.toList());
    }

    private int magnitude(List<Node> list) {
        for (var depth = 3; depth >= 0; depth--) {
            var reduced = false;
            do {
                reduced = false;
                for (var j = 0; j < list.size() - 1; j++) {
                    var left = list.get(j);
                    var right = list.get(j + 1);
                    if (left.depth != depth)
                        continue;

                    list.set(j, new Node(depth - 1, 3 * left.value + 2 * right.value));
                    list.remove(j + 1);
                    reduced = true;
                    break;
                }
            } while (reduced);
        }

        return list.get(0).value;
    }

    private List<Node> parseLine(String line) {
        var list = new ArrayList<Node>();
        var depth = -1;
        for (var cc : line.toCharArray()) {
            switch (cc) {
            case '[':
                depth++;
                break;
            case ']':
                depth--;
                break;
            case ',':
                break;
            default:
                var value = Integer.parseInt(String.valueOf(cc));
                list.add(new Node(depth, value));
                break;
            }
        }

        return list;
    }

    record Node(int depth, int value) {}
}
