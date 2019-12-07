import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("./src/input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<Integer> masses = reader.lines().map(Integer::parseInt).collect(Collectors.toList());
        System.out.printf("Part One: %s\n",  calculateInitialFuel(masses));
        System.out.printf("Part Two: %s\n",  calculateTotalFuel(masses));
    }


    public static int initialFuelForOneModule(int mass) {
        return Math.max(0, mass/3 - 2);
    }

    public static int calculateInitialFuel(List<Integer> masses) {
        return masses.stream().reduce(0, (a, b) -> a + initialFuelForOneModule(b));
    }

    public static int totalFuelForOneModule(int mass) {
        int totalFuel = initialFuelForOneModule(mass);
        int addedFuel = totalFuel;
        while (addedFuel > 0) {
            addedFuel = initialFuelForOneModule(addedFuel);
            totalFuel += addedFuel;
        }

        return totalFuel;
    }

    public static int calculateTotalFuel(List<Integer> masses) {
        return masses.stream().reduce(0, (a, b) -> a + totalFuelForOneModule(b));
    }
}
