package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day06 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day06.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        List<Integer> fish = Arrays.stream(lines.get(0).split(",")).map(Integer::valueOf).toList();
        Map<Integer, Long> fishCountPerTimer = new HashMap<>();
        for (var f : fish)
            if (fishCountPerTimer.computeIfPresent(f, (timer, count) -> count + 1) == null)
                fishCountPerTimer.put(f, 1l);

        fishCountPerTimer = fishSimulation(fishCountPerTimer, 80);

        return String.valueOf(fishCountPerTimer.values().stream().mapToLong(Long::longValue).sum());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day06.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        List<Integer> fish = Arrays.stream(lines.get(0).split(",")).map(Integer::valueOf).toList();
        Map<Integer, Long> fishCountPerTimer = new HashMap<>();
        for (var f : fish)
            if (fishCountPerTimer.computeIfPresent(f, (timer, count) -> count + 1) == null)
                fishCountPerTimer.put(f, 1l);

        fishCountPerTimer = fishSimulation(fishCountPerTimer, 256);

        return String.valueOf(fishCountPerTimer.values().stream().mapToLong(Long::longValue).sum());
    }

    private Map<Integer, Long> fishSimulation(Map<Integer, Long> fishCountPerTimer, int days) {
        for (int d = 0; d < days; d++) {
            Map<Integer, Long> newFishCountPerTimer = new HashMap<>();
            fishCountPerTimer.forEach((timer, count) -> newFishCountPerTimer.put(timer - 1, count));
            Long spawnThreshold = newFishCountPerTimer.remove(-1);
            if (spawnThreshold != null) {
                if (newFishCountPerTimer.computeIfPresent(8, (timer, count) -> count + spawnThreshold) == null)
                    newFishCountPerTimer.put(8, spawnThreshold);

                if (newFishCountPerTimer.computeIfPresent(6, (timer, count) -> count + spawnThreshold) == null)
                    newFishCountPerTimer.put(6, spawnThreshold);
            }

            fishCountPerTimer = newFishCountPerTimer;
       }

        return fishCountPerTimer;
    }
}
