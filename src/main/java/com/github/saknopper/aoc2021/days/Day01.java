package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Day01 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day01.txt").toURI());
        List<Integer> list;
        try (Stream<String> lines = Files.lines(path)) {
            list = lines.map(Integer::valueOf).toList();
        }

        int largerThanPreviousCount = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > list.get(i - 1))
                largerThanPreviousCount++;
        }

        return String.valueOf(largerThanPreviousCount);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day01.txt").toURI());
        List<Integer> list;
        try (Stream<String> lines = Files.lines(path)) {
            list = lines.map(Integer::valueOf).toList();
        }

        int largerThanPreviousCount = 0;
        for (int i = 3; i < list.size(); i++) {
            int lastSlidingWindowSum = list.get(i - 1) + list.get(i - 2) + list.get(i - 3);
            int currentSlidingWindowSum = list.get(i) + list.get(i - 1) + list.get(i - 2);
            if (currentSlidingWindowSum > lastSlidingWindowSum)
                largerThanPreviousCount++;
        }

        return String.valueOf(largerThanPreviousCount);
    }
}
