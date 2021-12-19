package com.github.saknopper.aoc2021.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joml.Matrix3d;
import org.joml.Vector3d;

public class Day19 extends Day
{
    private static final double[] ROTATIONS_ALONG_AXIS = { Math.toRadians(0), Math.toRadians(90), Math.toRadians(180),
            Math.toRadians(270) };

    private static final List<Matrix3d> ROTATIONS = new ArrayList<>();

    static {
        // https://www.euclideanspace.com/maths/algebra/matrix/transforms/examples/index.htm
        for (var yRot : ROTATIONS_ALONG_AXIS) {
            Matrix3d yRotation = new Matrix3d().rotateY(yRot);
            ROTATIONS.add(yRotation);

            Matrix3d xRotation1 = new Matrix3d(yRotation);
            xRotation1.rotateX(ROTATIONS_ALONG_AXIS[1]);
            ROTATIONS.add(xRotation1);

            Matrix3d xRotation2 = new Matrix3d(yRotation);
            xRotation2.rotateX(-ROTATIONS_ALONG_AXIS[1]);
            ROTATIONS.add(xRotation2);

            Matrix3d zRotation1 = new Matrix3d(yRotation);
            zRotation1.rotateZ(ROTATIONS_ALONG_AXIS[1]);
            ROTATIONS.add(zRotation1);

            Matrix3d zRotation2 = new Matrix3d(yRotation);
            zRotation2.rotateZ(ROTATIONS_ALONG_AXIS[2]);
            ROTATIONS.add(zRotation2);

            Matrix3d zRotation3 = new Matrix3d(yRotation);
            zRotation3.rotateZ(-ROTATIONS_ALONG_AXIS[1]);
            ROTATIONS.add(zRotation3);
        }
    }

    @Override
    public String getAnswerPartOne() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day19.txt").toURI());
        var scanners = parseInput(Files.readAllLines(path).stream().filter(l -> !l.isBlank()).toList());

        List<Scanner> processedScanners = new ArrayList<>();
        while (!scanners.isEmpty())
            findNextMatchingScanner(scanners, processedScanners);

        return String.valueOf(processedScanners.get(0).beacons.size());
    }

    @Override
    public String getAnswerPartTwo() throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource("day19.txt").toURI());
        var scanners = parseInput(Files.readAllLines(path).stream().filter(l -> !l.isBlank()).toList());

        List<Scanner> processedScanners = new ArrayList<>();
        while (!scanners.isEmpty())
            findNextMatchingScanner(scanners, processedScanners);

        double largestDistance = Double.MIN_VALUE;
        for (int i = 0; i < processedScanners.size(); i++) {
            for (int j = 0; j < processedScanners.size(); j++) {
                if (i == j)
                    continue;

                Vector3d result = new Vector3d();
                processedScanners.get(i).offset.sub(processedScanners.get(j).offset, result);
                result.absolute();
                double manhattan = result.x + result.y + result.z;
                if (manhattan > largestDistance)
                    largestDistance = manhattan;
            }
        }

        return String.valueOf(Math.round(largestDistance));
    }

    private void findNextMatchingScanner(List<Scanner> remaining, List<Scanner> matched) {
        System.out.println(String.format("findNextMatchingScanner - remaining size %d", remaining.size()));
        if (matched.isEmpty()) {
            matched.add(remaining.remove(0));
            return;
        }

        Scanner scannerZeroPlusOtherBeacons = matched.get(0);

        Iterator<Scanner> remIt = remaining.iterator();
        while (remIt.hasNext()) {
            Scanner candidateScanner = remIt.next();
            List<Vector3d> beaconsToAdd = isMatch(scannerZeroPlusOtherBeacons, candidateScanner);
            if (!beaconsToAdd.isEmpty()) {
                matched.add(candidateScanner);
                scannerZeroPlusOtherBeacons.beacons.addAll(beaconsToAdd);
                remIt.remove();

                return;
            }
        }
    }

    private List<Vector3d> isMatch(Scanner reference, Scanner candidate) {
        System.out.println(String.format("isMatch - %s, %s", reference.id, candidate.id));

        for (var rot : ROTATIONS) {
            candidate.rotation = rot;

            for (var rp : reference.beacons) {
                for (var cp : candidate.getBeaconsWithRotation()) {
                    var offset = new Vector3d();
                    rp.sub(cp, offset);

                    candidate.offset = offset;

                    var difference = candidate.getBeaconsWithRotationAndOffset().stream()
                            .filter(b -> reference.beacons.stream().noneMatch(b2 -> b2.equals(b, 0.03125))).toList();
                    if (candidate.beacons.size() - difference.size() >= 12l) {
                        System.out.println(String.format("match for reference %s, candidate: %s, offset: %s", reference.id, candidate.id,
                                                         offset.toString(NumberFormat.getIntegerInstance())));

                        return difference;
                    }
                }
            }
        }

        return List.of();
    }

    private List<Scanner> parseInput(List<String> input) {
        List<Scanner> scanners = new ArrayList<>();

        Scanner current = null;
        for (var line : input) {
            var scannerStart = line.indexOf("scanner");
            if (scannerStart != -1) {
                if (current != null)
                    scanners.add(current);

                var idStr = line.substring(scannerStart + 8, scannerStart + 8 + 2);
                current = new Scanner(Integer.valueOf(idStr.trim()));

                continue;
            }

            String[] coords = line.split(",");
            current.beacons.add(new Vector3d(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), Integer.valueOf(coords[2])));
        }

        if (current != null)
            scanners.add(current);

        return scanners;
    }

    class Scanner
    {
        public final int id;
        public final List<Vector3d> beacons;
        public Matrix3d rotation;
        public Vector3d offset;

        public Scanner(int id) {
            super();
            this.id = id;
            this.beacons = new ArrayList<>();
            this.rotation = new Matrix3d();
            this.offset = new Vector3d();
        }

        public List<Vector3d> getBeaconsWithRotation() {
            return beacons.stream().map(b -> new Vector3d(b)).map(b -> b.mul(rotation)).toList();
        }

        public List<Vector3d> getBeaconsWithRotationAndOffset() {
            return beacons.stream().map(b -> new Vector3d(b)).map(b -> b.mul(rotation)).map(b -> b.add(offset)).toList();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Scanner [id=" + id + ", points=\n");
            for (var b : beacons) {
                var tmpVec = new Vector3d();
                b.mul(rotation, tmpVec);
                sb.append(String.format("  ( %f, %f, %f )%n", tmpVec.x, tmpVec.y, tmpVec.z));
            }

            sb.append("]");

            return sb.toString();
        }
    }
}
