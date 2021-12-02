package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day02 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day02.txt").toURI());
        List<String> operations = Files.readAllLines(path);

        long position = 0l;
        long depth = 0l;

        for (String operation : operations) {
            String[] splitted = operation.split(" ");
            String action = splitted[0];
            Long amount = Long.valueOf(splitted[1]);

            switch (action) {
            case "forward":
                position += amount;
                break;
            case "down":
                depth += amount;
                break;
            case "up":
                depth -= amount;
                break;
            }
        }

        return String.valueOf(position * depth);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day02.txt").toURI());
        List<String> operations = Files.readAllLines(path);

        long position = 0l;
        long depth = 0l;
        long aim = 0l;

        for (String operation : operations) {
            String[] splitted = operation.split(" ");
            String action = splitted[0];
            Long amount = Long.valueOf(splitted[1]);

            switch (action) {
            case "forward":
                position += amount;
                depth += (aim * amount);
                break;
            case "down":
                aim += amount;
                break;
            case "up":
                aim -= amount;
                break;
            }
        }

        return String.valueOf(position * depth);
    }
}
