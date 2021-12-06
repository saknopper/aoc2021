package com.github.saknopper.aoc2021.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Map<Byte, Long> fishCountPerTimer = parseInput();
        fishCountPerTimer = fishSimulation(fishCountPerTimer, 80);

        return String.valueOf(fishCountPerTimer.values().stream().mapToLong(Long::longValue).sum());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Map<Byte, Long> fishCountPerTimer = parseInput();
        fishCountPerTimer = fishSimulation(fishCountPerTimer, 256);

        return String.valueOf(fishCountPerTimer.values().stream().mapToLong(Long::longValue).sum());
    }

    private Map<Byte, Long> parseInput() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("day06.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        List<Byte> fish = Arrays.stream(lines.get(0).split(",")).map(Byte::valueOf).toList();
        Map<Byte, Long> fishCountPerTimer = new HashMap<>();
        for (var f : fish)
            if (fishCountPerTimer.computeIfPresent(f, (timer, count) -> count + 1) == null)
                fishCountPerTimer.put(f, 1l);

        return fishCountPerTimer;
    }

    private Map<Byte, Long> fishSimulation(Map<Byte, Long> fishCountPerTimer, int days) {
        for (int d = 0; d < days; d++) {
            Map<Byte, Long> newFishCountPerTimer = new HashMap<>();
            fishCountPerTimer.forEach((timer, count) -> newFishCountPerTimer.put((byte)(timer - 1), count));
            Long spawnThreshold = newFishCountPerTimer.remove((byte)-1);
            if (spawnThreshold != null) {
                if (newFishCountPerTimer.computeIfPresent((byte)8, (timer, count) -> count + spawnThreshold) == null)
                    newFishCountPerTimer.put((byte)8, spawnThreshold);

                if (newFishCountPerTimer.computeIfPresent((byte)6, (timer, count) -> count + spawnThreshold) == null)
                    newFishCountPerTimer.put((byte)6, spawnThreshold);
            }

            fishCountPerTimer = newFishCountPerTimer;
       }

        return fishCountPerTimer;
    }
}
