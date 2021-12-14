package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;

public class Day14 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day14.txt").toURI());
        List<String> lines = Files.readAllLines(path).stream().filter(l -> !l.isBlank()).collect(Collectors.toList());
        final String polymer = lines.remove(0);
        final Map<String, String> rules = parseRules(lines);

        return applyPolymerRules(polymer, rules, 10);
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day14.txt").toURI());
        List<String> lines = Files.readAllLines(path).stream().filter(l -> !l.isBlank()).collect(Collectors.toList());
        final String polymer = lines.remove(0);
        final Map<String, String> rules = parseRules(lines);

        return applyPolymerRules(polymer, rules, 40);
    }

    private Map<String, String> parseRules(List<String> lines) {
        return lines.stream().map(l -> {
            var splitted = l.split(" -> ");
            return new SimpleImmutableEntry<>(splitted[0], splitted[1]);
        }).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String applyPolymerRules(final String polymer, final Map<String, String> rules, int loops) {
        Map<String, Long> pairCounter = new TreeMap<>();
        for (int i = 0; i < polymer.length() - 1; i++)
            pairCounter.compute(polymer.substring(i, i + 2), (key, value) -> value == null ? 1 : value + 1);

        Map<String, Long> elementCounter = new TreeMap<>();
        Splitter.fixedLength(1).splitToStream(polymer)
                .forEach(elm -> elementCounter.compute(elm, (key, value) -> value == null ? 1 : value + 1));
        for (int i = 0; i < loops; i++)
            pairCounter = applyPolymerRules(pairCounter, rules, elementCounter);

        return String.valueOf(elementCounter.values().stream().mapToLong(Long::longValue).max().orElseThrow()
                - elementCounter.values().stream().mapToLong(Long::longValue).min().orElseThrow());
    }

    private Map<String, Long> applyPolymerRules(Map<String, Long> pairCounter, Map<String, String> rules,
            Map<String, Long> elementCounter) {
        Map<String, Long> updatedPairCounter = new TreeMap<>();
        for (var entry : pairCounter.entrySet()) {
            final long count = entry.getValue();
            final String toAdd = rules.get(entry.getKey());

            List<String> splittedSourcePolymer = Splitter.fixedLength(1).splitToList(entry.getKey());
            String newPolymer1 = splittedSourcePolymer.get(0) + toAdd;
            String newPolymer2 = toAdd + splittedSourcePolymer.get(1);

            updatedPairCounter.compute(newPolymer1, (key, value) -> value == null ? count : value + count);
            updatedPairCounter.compute(newPolymer2, (key, value) -> value == null ? count : value + count);

            elementCounter.compute(toAdd, (key, value) -> value == null ? count : value + count);
        }

        return updatedPairCounter;
    }
}
