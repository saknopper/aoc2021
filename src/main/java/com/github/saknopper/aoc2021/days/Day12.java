package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Day12 extends Day
{
    private static final String START_VERTEX = "start";
    private static final String END_VERTEX = "end";

    @Override
    public String getAnswerPartOne() throws Exception {
        final Multimap<String, String> edges = parseInputToMultimap();
        List<List<String>> paths = createValidPaths(edges, false);

        return String.valueOf(paths.size());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        final Multimap<String, String> edges = parseInputToMultimap();
        List<List<String>> paths = createValidPaths(edges, true);

        return String.valueOf(paths.size());
    }

    private Multimap<String, String> parseInputToMultimap() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day12.txt").toURI());
        Multimap<String, String> edges = ArrayListMultimap.create();

        Files.readAllLines(path).stream().forEach(line -> {
            String[] splitted = line.split("-");
            edges.put(splitted[0], splitted[1]);
            edges.put(splitted[1], splitted[0]);
        });

        return edges;
    }

    private static List<List<String>> createValidPaths(final Multimap<String, String> edges,
            final boolean allowedToVisitOneSmallCaveTwice) {
        List<List<String>> paths = new ArrayList<>();
        edges.get(START_VERTEX).forEach(step -> {
            List<String> path = new ArrayList<>(List.of(START_VERTEX));
            paths.addAll(nextStepInPath(path, step, edges, allowedToVisitOneSmallCaveTwice));
        });

        return paths;
    }

    private static List<List<String>> nextStepInPath(List<String> path, final String currentStep, final Multimap<String, String> edges,
            final boolean allowedToVisitOneSmallCaveTwice) {
        path = new ArrayList<>(path);
        path.add(currentStep);
        if (END_VERTEX.equals(currentStep))
            return List.of(path);

        List<List<String>> paths = new ArrayList<>();
        List<String> nextSteps = edges.get(currentStep).stream().filter(step -> !START_VERTEX.equals(step)).toList();
        for (var nextStep : nextSteps) {
            if (nextStep.toLowerCase().equals(nextStep) && path.contains(nextStep)) {
                if (allowedToVisitOneSmallCaveTwice) {
                    if (containsSmallCaveVisitedMultipleTimes(path))
                        continue;
                } else {
                    continue;
                }
            }

            paths.addAll(nextStepInPath(path, nextStep, edges, allowedToVisitOneSmallCaveTwice));
        }

        return paths;
    }

    private static boolean containsSmallCaveVisitedMultipleTimes(final List<String> path) {
        for (var pos : path)
            if (pos.toLowerCase().equals(pos) && path.indexOf(pos) != path.lastIndexOf(pos))
                return true;

        return false;
    }
}
