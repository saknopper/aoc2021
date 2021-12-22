package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day22 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day22.txt").toURI());
        List<Instruction> instructions = Files.readAllLines(path).stream().map(Day22::parseInstruction).toList();

        final List<Cuboid> cuboids = new ArrayList<>();
        executeInstructions(instructions, cuboids);

        return String.valueOf(cuboids.stream()
                .filter(c -> c.xMin >= -50 && c.xMax <= 50 && c.yMin >= -50 && c.yMax <= 50 && c.zMin >= -50 && c.zMax <= 50)
                .map(c -> (long) Math.abs(c.xMin - c.xMax - 1) * Math.abs(c.yMin - c.yMax - 1) * Math.abs(c.zMin - c.zMax - 1))
                .mapToLong(Long::longValue).sum());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day22.txt").toURI());
        List<Instruction> instructions = Files.readAllLines(path).stream().map(Day22::parseInstruction).toList();

        final List<Cuboid> cuboids = new ArrayList<>();
        executeInstructions(instructions, cuboids);

        return String.valueOf(cuboids.stream()
                .map(c -> (long) Math.abs(c.xMin - c.xMax - 1) * Math.abs(c.yMin - c.yMax - 1) * Math.abs(c.zMin - c.zMax - 1))
                .mapToLong(Long::longValue).sum());
    }

    private void executeInstructions(List<Instruction> instructions, List<Cuboid> cuboids) {
        instructions.forEach(i -> {
            Cuboid instructionCuboid = new Cuboid(i.xMin, i.xMax, i.yMin, i.yMax, i.zMin, i.zMax);

            List<Cuboid> newCuboids = new ArrayList<>();
            if (i.enable)
                newCuboids.add(instructionCuboid);

            cuboids.forEach(c -> {
                if (areCuboidsNotTouching(c, instructionCuboid)) {
                    newCuboids.add(c);
                    return;
                }

                // Left
                if (c.xMin < instructionCuboid.xMin) {
                    newCuboids.add(new Cuboid(c.xMin, instructionCuboid.xMin - 1, c.yMin, c.yMax, c.zMin, c.zMax));
                    c.xMin = instructionCuboid.xMin;
                }

                // Right
                if (c.xMax > instructionCuboid.xMax) {
                    newCuboids.add(new Cuboid(instructionCuboid.xMax + 1, c.xMax, c.yMin, c.yMax, c.zMin, c.zMax));
                    c.xMax = instructionCuboid.xMax;
                }

                // Top
                if (c.yMin < instructionCuboid.yMin) {
                    newCuboids.add(new Cuboid(c.xMin, c.xMax, c.yMin, instructionCuboid.yMin - 1, c.zMin, c.zMax));
                    c.yMin = instructionCuboid.yMin;
                }

                // Bottom
                if (c.yMax > instructionCuboid.yMax) {
                    newCuboids.add(new Cuboid(c.xMin, c.xMax, instructionCuboid.yMax + 1, c.yMax, c.zMin, c.zMax));
                    c.yMax = instructionCuboid.yMax;
                }

                // Front
                if (c.zMin < instructionCuboid.zMin) {
                    newCuboids.add(new Cuboid(c.xMin, c.xMax, c.yMin, c.yMax, c.zMin, instructionCuboid.zMin - 1));
                    c.zMin = instructionCuboid.zMin;
                }

                // Back
                if (c.zMax > instructionCuboid.zMax) {
                    newCuboids.add(new Cuboid(c.xMin, c.xMax, c.yMin, c.yMax, instructionCuboid.zMax + 1, c.zMax));
                    c.zMax = instructionCuboid.zMax;
                }
            });

            cuboids.clear();
            cuboids.addAll(newCuboids);
        });
    }

    private static boolean areCuboidsNotTouching(Cuboid c1, Cuboid c2) {
        return Math.max(c1.xMin, c2.xMin) > Math.min(c1.xMax, c2.xMax) || Math.max(c1.yMin, c2.yMin) > Math.min(c1.yMax, c2.yMax)
                || Math.max(c1.zMin, c2.zMin) > Math.min(c1.zMax, c2.zMax);
    }

    private static Instruction parseInstruction(String line) {
        var splitted1 = line.split(" ");
        boolean enable = "on".equals(splitted1[0]);

        var splitted2 = splitted1[1].split(",");
        var x = splitted2[0].split("=")[1].split(Pattern.quote(".."));
        var y = splitted2[1].split("=")[1].split(Pattern.quote(".."));
        var z = splitted2[2].split("=")[1].split(Pattern.quote(".."));

        return new Instruction(enable, Integer.valueOf(x[0]), Integer.valueOf(x[1]), Integer.valueOf(y[0]), Integer.valueOf(y[1]),
                               Integer.valueOf(z[0]), Integer.valueOf(z[1]));
    }

    class Cuboid
    {
        public int xMin;
        public int xMax;
        public int yMin;
        public int yMax;
        public int zMin;
        public int zMax;

        public Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            super();
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.zMin = zMin;
            this.zMax = zMax;
        }

        @Override
        public String toString() {
            return "Cuboid [xMin=" + xMin + ", xMax=" + xMax + ", yMin=" + yMin + ", yMax=" + yMax + ", zMin=" + zMin + ", zMax=" + zMax
                    + "]";
        }
    }

    record Instruction(boolean enable, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {}
}
