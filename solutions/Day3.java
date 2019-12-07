import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("./src/input.txt");
        Scanner scan = new Scanner(inputFile);
        String rawInput1 = scan.nextLine();
        String[] instructions1 = rawInput1.split(",");
        String rawInput2 = scan.nextLine();
        String[] instructions2 = rawInput2.split(",");

        System.out.println("Part One: " + findClosestIntersection(instructions1, instructions2));
        System.out.println("Part Two: " + findSumOfStepsRequired(instructions1, instructions2));
    }

    public static Map<String, Integer> getPointsTraversed(String[] instructions) {
        int x = 0, y = 0, totalSteps = 0;
        Map<String, Integer> points = new HashMap<>();
        for (String instruction : instructions) {
            String direction = instruction.substring(0, 1);
            int stepsLeft = Integer.parseInt(instruction.substring(1));
            for (; stepsLeft > 0; stepsLeft--) {
                totalSteps += 1;
                String newPoint = getNewPoint(x, y, direction);
                if (!newPoint.equals("0,0")) {
                    points.put(newPoint, totalSteps);
                }
                String[] values = newPoint.split(",");
                x = Integer.parseInt(values[0]);
                y = Integer.parseInt(values[1]);
            }
        }

        return points;
    }

    public static String getNewPoint(int currentX, int currentY, String direction) {
        int newX = currentX, newY = currentY;
        if (direction.equals("U")) {
            newX += 1;
        } else if (direction.equals("D")) {
            newX -= 1;
        } else if (direction.equals("R")) {
            newY += 1;
        } else if (direction.equals("L")) {
            newY -= 1;
        }

        return String.format("%s,%s", newX, newY);
    }

    public static Set<String> findIntersectionPoints(Set<String> points1, Set<String> points2) {
        Set<String> intersections = new HashSet<>();
        for (String point : points1) {
            if (points2.contains(point)) {
                intersections.add(point);
            }
        }
        return intersections;
    }

    public static int findClosestIntersection(String[] instructions1, String[] instructions2) {
        Map<String, Integer> points1 = getPointsTraversed(instructions1);
        Map<String, Integer> points2 = getPointsTraversed(instructions2);

        Set<String> intersections = findIntersectionPoints(points1.keySet(), points2.keySet());

        int minDistance = Integer.MAX_VALUE;
        for (String point : intersections) {
            String[] rawValues = point.split(",");
            int x = Integer.parseInt(rawValues[0]);
            int y = Integer.parseInt(rawValues[1]);

            int currentDistance = Math.abs(x) + Math.abs(y);
            minDistance = Math.min(minDistance, currentDistance);
        }

        return minDistance;
    }

    public static int findSumOfStepsRequired(String[] instructions1, String[] instructions2) {
        Map<String, Integer> points1 = getPointsTraversed(instructions1);
        Map<String, Integer> points2 = getPointsTraversed(instructions2);

        Set<String> intersections = findIntersectionPoints(points1.keySet(), points2.keySet());

        int minSum = Integer.MAX_VALUE;
        for (String point : intersections) {
            int currentSum = points1.get(point) + points2.get(point);
            minSum = Math.min(minSum, currentSum);
        }
        return minSum;
    }
}
