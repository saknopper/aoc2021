package com.github.saknopper.aoc2021.days;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class Day16 extends Day
{
    enum PacketType {
        SUM, PRODUCT, MINIMUM, MAXIMUM, LITERAL, GREATER_THAN, LESS_THAN, EQUAL_TO
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day16.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        if (lines.size() > 1)
            throw new IllegalStateException("Expecting only a single input line");

        List<Packet> packets = new ArrayList<>();

        String bin = Splitter.fixedLength(1).splitToStream(lines.get(0)).map(Day16::hexToBin).collect(Collectors.joining());
        while (!bin.isEmpty())
            bin = parsePacket(bin, packets, false);

        return String.valueOf(sumOfAllVersionNumbers(packets));
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("day16.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        if (lines.size() > 1)
            throw new IllegalStateException("Expecting only a single input line");

        List<Packet> packets = new ArrayList<>();

        String bin = Splitter.fixedLength(1).splitToStream(lines.get(0)).map(Day16::hexToBin).collect(Collectors.joining());
        while (!bin.isEmpty())
            bin = parsePacket(bin, packets, false);

        return String.valueOf(evaluatePacket(packets.get(0)));
    }

    private String parsePacket(String bin, List<Packet> packets, boolean skipOffsetPadding) {
        if (bin.length() < 11)
            return "";

        int stringOffset = 0;
        int version = Integer.valueOf(bin.substring(stringOffset, 3), 2);
        stringOffset += 3;
        PacketType type = PacketType.values()[Integer.valueOf(bin.substring(stringOffset, stringOffset + 3), 2)];
        stringOffset += 3;

        List<Packet> subpackets = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (type == PacketType.LITERAL) {
            boolean lastGroup = false;
            while (!lastGroup) {
                String group = bin.substring(stringOffset, stringOffset + 5);
                sb.append(group.substring(1));
                stringOffset += 5;
                if (group.startsWith("0"))
                    lastGroup = true;
            }
        } else {
            String lengthType = bin.substring(stringOffset, stringOffset + 1);
            stringOffset += 1;

            if ("0".equals(lengthType)) {
                int subpacketsLength = Integer.valueOf(bin.substring(stringOffset, stringOffset + 15), 2);
                stringOffset += 15;

                String substring = bin.substring(stringOffset, stringOffset + subpacketsLength);
                while (!substring.isEmpty())
                    substring = parsePacket(substring, subpackets, true);

                stringOffset += subpacketsLength;
            } else {
                int amountOfSubpackets = Integer.valueOf(bin.substring(stringOffset, stringOffset + 11), 2);
                stringOffset += 11;

                String substring = bin.substring(stringOffset);
                for (int i = 0; i < amountOfSubpackets; i++) {
                    int oldLength = substring.length();

                    substring = parsePacket(substring, subpackets, true);

                    stringOffset += oldLength - substring.length();
                }
            }
        }

        if (!skipOffsetPadding && stringOffset % 4 != 0)
            stringOffset += (4 - (stringOffset % 4));

        packets.add(new Packet(version, type, sb.length() > 0 ? Long.valueOf(sb.toString(), 2) : 0l, subpackets));

        return bin.substring(stringOffset);
    }

    private static int sumOfAllVersionNumbers(List<Packet> packets) {
        int sum = 0;
        for (var p : packets) {
            sum += p.version;
            sum += sumOfAllVersionNumbers(p.subpackets);
        }

        return sum;
    }

    private static long evaluatePacket(Packet p) {
        switch (p.type) {
            case EQUAL_TO: {
                return evaluatePacket(p.subpackets.get(0)) == evaluatePacket(p.subpackets.get(1)) ? 1l : 0l;
            }
            case GREATER_THAN: {
                return evaluatePacket(p.subpackets.get(0)) > evaluatePacket(p.subpackets.get(1)) ? 1l : 0l;
            }
            case LESS_THAN: {
                return evaluatePacket(p.subpackets.get(0)) < evaluatePacket(p.subpackets.get(1)) ? 1l : 0l;
            }
            case LITERAL:
                return p.literal;
            case MAXIMUM: {
                long max = Long.MIN_VALUE;
                for (var sub : p.subpackets)
                    max = Math.max(max, evaluatePacket(sub));
                return max;
            }
            case MINIMUM: {
                long min = Long.MAX_VALUE;
                for (var sub : p.subpackets)
                    min = Math.min(min, evaluatePacket(sub));
                return min;
            }
            case PRODUCT: {
                long product = 1l;
                for (var sub : p.subpackets)
                    product *= evaluatePacket(sub);
                return product;
            }
            case SUM: {
                long sum = 0l;
                for (var sub : p.subpackets)
                    sum += evaluatePacket(sub);
                return sum;
            }
        }

        return 0l;
    }

    private static String hexToBin(String s) {
        return Strings.padStart(new BigInteger(s, 16).toString(2), 4, '0');
    }

    record Packet(int version, PacketType type, long literal, List<Packet> subpackets) {}
}
