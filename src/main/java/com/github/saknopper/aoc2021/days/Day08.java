package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day08 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day08.txt").toURI());
        List<Entry> entries = Files.readAllLines(path).stream().map(Day08::lineToEntry).toList();

        return String.valueOf(entries.stream().flatMap(e -> e.output.stream())
                .filter(s -> List.of(2, 3, 4, 7).stream().anyMatch(l -> s.length() == l)).count());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day08.txt").toURI());
        List<Entry> entries = Files.readAllLines(path).stream().map(Day08::lineToEntry).toList();

        /*
         * Based on given example:
         *   acedgfb: 8, cdfbe: 5, gcdfa: 2, fbcad: 3, dab: 7, cefabd: 9, cdfgeb: 6, eafb: 4, cagedb: 0, ab: 1
         */
        List<Integer> signatures = List.of(42, 17, 34, 39, 30, 37, 41, 25, 49, 45);

        long total = 0;
        for (var entry : entries) {
            String signals = String.join(" ", entry.signals);
            String output = String.join(" ", entry.output);

            for (char c : "abcdefg".toCharArray()) {
                output = output.replace(Character.toString(c), String.valueOf(signals.chars().filter(sc -> sc == c).count()));
            }

            String number = Arrays.stream(output.split(" "))
                    .map(d -> d.chars().map(Character::getNumericValue).reduce(0, Integer::sum))
                    .map(signatures::indexOf)
                    .map(String::valueOf)
                    .collect(Collectors.joining());

            total += Integer.parseInt(number);
        }

        return String.valueOf(total);
    }

    private static Entry lineToEntry(String line) {
        String[] splitted = line.split(Pattern.quote(" | "));

        return new Entry(Arrays.asList(splitted[0].split(" ")), Arrays.asList(splitted[1].split(" ")));
    }

    record Entry(List<String> signals, List<String> output) {}
}
