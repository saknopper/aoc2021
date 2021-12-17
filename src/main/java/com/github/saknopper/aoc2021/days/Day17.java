package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Day17 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day17.txt").toURI());
        var target = parseTarget(Files.readAllLines(path).get(0));

        return String.valueOf(calculateValidVelocitiesAndHighestPoint(target)[0]);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day17.txt").toURI());
        var target = parseTarget(Files.readAllLines(path).get(0));

        return String.valueOf(calculateValidVelocitiesAndHighestPoint(target)[1]);
    }

    private static int[] calculateValidVelocitiesAndHighestPoint(Target target) {
        int globalHighestY = Integer.MIN_VALUE;
        int validCounter = 0;

        for (int startXv = 1; startXv <= target.maxX; startXv++) {
            for (int startYv = target.minY; startYv <= 1000; startYv++) {
                int highestPoint = determineHighestPoint(target, startXv, startYv);
                if (highestPoint != -1) {
                    validCounter++;
                    if (highestPoint > globalHighestY)
                        globalHighestY = highestPoint;
                }
            }
        }

        return new int[] { globalHighestY, validCounter };
    }

    private static int determineHighestPoint(Target target, int startXv, int startYv) {
        int xPos = 0;
        int yPos = 0;
        int highestY = 0;

        for (int xv = startXv, yv = startYv;; yv--) {
            xPos += xv;
            yPos += yv;

            if (yPos > highestY)
                highestY = yPos;

            if (xPos > target.maxX || yPos < target.minY)
                break;
            else if (xPos >= target.minX && xPos <= target.maxX && yPos >= target.minY && yPos <= target.maxY)
                return highestY;

            if (xv != 0)
                xv--;
        }

        return -1;
    }

    private static Target parseTarget(String input) {
        String[] splitted = input.split(": ")[1].split(", ");
        String[] xValues = splitted[0].split("=")[1].split(Pattern.quote(".."));
        String[] yValues = splitted[1].split("=")[1].split(Pattern.quote(".."));

        return new Target(Integer.valueOf(xValues[0]), Integer.valueOf(xValues[1]), Integer.valueOf(yValues[0]),
                          Integer.valueOf(yValues[1]));
    }

    record Target(int minX, int maxX, int minY, int maxY) {}

    record Velocity(int x, int y) {}
}
