package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class Day07 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day07.txt").toURI());
        List<Integer> crabs = Arrays.stream(Files.readAllLines(path).get(0).split(",")).map(Integer::valueOf).toList();
        int minPosition = crabs.stream().mapToInt(Integer::intValue).min().orElseThrow();
        int maxPosition = crabs.stream().mapToInt(Integer::intValue).max().orElseThrow();

        Map<Integer, Integer> fuelCostPerPosition = new HashMap<>();
        IntStream.rangeClosed(minPosition, maxPosition).forEach(pos -> {
            int fuelCost = crabs.stream().map(curPos -> Math.abs(pos - curPos)).mapToInt(Integer::intValue).sum();
            fuelCostPerPosition.put(pos, fuelCost);
        });

        Entry<Integer, Integer> minCost = Collections.min(fuelCostPerPosition.entrySet(), Map.Entry.comparingByValue());

        return String.valueOf(minCost.getValue());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day07.txt").toURI());
        List<Integer> crabs = Arrays.stream(Files.readAllLines(path).get(0).split(",")).map(Integer::valueOf).toList();
        int minPosition = crabs.stream().mapToInt(Integer::intValue).min().orElseThrow();
        int maxPosition = crabs.stream().mapToInt(Integer::intValue).max().orElseThrow();

        Map<Integer, Integer> fuelCostPerPosition = new HashMap<>();
        IntStream.rangeClosed(minPosition, maxPosition).forEach(pos -> {
            int fuelCost = crabs.stream().map(curPos -> Math.abs(pos - curPos)).map(cost -> IntStream.rangeClosed(1, cost).sum()).mapToInt(Integer::intValue).sum();
            fuelCostPerPosition.put(pos, fuelCost);
        });

        Entry<Integer, Integer> minCost = Collections.min(fuelCostPerPosition.entrySet(), Map.Entry.comparingByValue());

        return String.valueOf(minCost.getValue());
    }
}
