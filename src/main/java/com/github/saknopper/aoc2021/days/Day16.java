package com.github.saknopper.aoc2021.days;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class Day16 extends Day
{
    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day16.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        if (lines.size() > 1)
            throw new IllegalStateException("Expecting only a single input line");

        String bin = Splitter.fixedLength(1).splitToStream(lines.get(0)).map(Day16::hexToBin).collect(Collectors.joining());
        System.out.println(bin);

        return "";
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day16.txt").toURI());
        List<String> lines = Files.readAllLines(path);

        return "";
    }

    private static String hexToBin(String s) {
        return Strings.padStart(new BigInteger(s, 16).toString(2), 4, '0');
    }
}
