package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

public class Day12 extends Day
{
    private static final String START_VERTEX = "start";
    private static final String END_VERTEX = "end";

    private static final String VISITED_TWICE_MARKER = "__2X__";

    @Override
    public String getAnswerPartOne() throws Exception {
        final Multimap<String, String> edges = parseInputToMultimap();
        long validPaths = createValidPaths(edges, false);

        return String.valueOf(validPaths);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        final Multimap<String, String> edges = parseInputToMultimap();
        long validPaths = createValidPaths(edges, true);

        return String.valueOf(validPaths);
    }

    private Multimap<String, String> parseInputToMultimap() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day12.txt").toURI());
        Multimap<String, String> edges = MultimapBuilder.treeKeys().hashSetValues().build();

        Files.readAllLines(path).stream().forEach(line -> {
            String[] splitted = line.split("-");
            if (!START_VERTEX.equals(splitted[1]) && !END_VERTEX.equals(splitted[0]))
                edges.put(splitted[0], splitted[1]);
            if (!START_VERTEX.equals(splitted[0]) && !END_VERTEX.equals(splitted[1]))
                edges.put(splitted[1], splitted[0]);
        });

        return edges;
    }

    private static long createValidPaths(final Multimap<String, String> edges,
            final boolean allowedToVisitOneSmallCaveTwice) {
        long pathCounter = 0l;

        Collection<String> starts = edges.removeAll(START_VERTEX);
        Set<String> smallCaves = edges.keySet().stream().filter(key -> key.toLowerCase().equals(key)).filter(key -> !END_VERTEX.equals(key))
                .collect(Collectors.toUnmodifiableSet());
        for (var step : starts) {
            List<String> path = new ArrayList<>(List.of(START_VERTEX));
            pathCounter += nextStepInPath(path, step, edges, smallCaves, allowedToVisitOneSmallCaveTwice);
        }

        return pathCounter;
    }

    private static long nextStepInPath(final List<String> path, final String currentStep,
            final Multimap<String, String> edges, final Set<String> smallCaves, final boolean allowedToVisitOneSmallCaveTwice) {
        final var newPath = new ArrayList<>(path);

        if (smallCaves.contains(currentStep) && newPath.contains(currentStep))
            newPath.add(0, VISITED_TWICE_MARKER);
        newPath.add(currentStep);

        if (END_VERTEX.equals(currentStep))
            return 1l;

        long pathCounter = 0l;
        var nextSteps = edges.get(currentStep);
        for (var nextStep : nextSteps) {
            if (smallCaves.contains(nextStep) && newPath.contains(nextStep)) {
                if (allowedToVisitOneSmallCaveTwice) {
                    if (VISITED_TWICE_MARKER.equals(newPath.get(0)))
                        continue;
                } else {
                    continue;
                }
            }

            pathCounter += nextStepInPath(newPath, nextStep, edges, smallCaves, allowedToVisitOneSmallCaveTwice);
        }

        return pathCounter;
    }
}
