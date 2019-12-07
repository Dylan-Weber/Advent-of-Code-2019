import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args)
    {
        File inputFile = new File("./src/input.txt");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (String rawInput : lines)
            {
                String[] rawProgram = rawInput.split(",");

                List<Integer> program = Arrays.stream(rawProgram).map(Integer::parseInt).collect(Collectors.toList());
                List<Integer> fixedProgram = fixProgram(program, 12, 2);

                System.out.println("Part One: " + fixedProgram.get(0));
                System.out.println("Part Two: " + nounVerbValue(program, 19690720));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static List<Integer> fixProgram(List<Integer> program, int index1Value, int index2Value)
    {
        List<Integer> programCopy = new ArrayList<>(program);

        programCopy.set(1, index1Value);
        programCopy.set(2, index2Value);
        return programCopy;
    }

    public static List<Integer> runProgram(List<Integer> program)
    {
        List<Integer> runningProgram = new ArrayList<>(program);
        for (int i = 0; i < program.size(); i += 4)
        {
            int opcode = runningProgram.get(i);

            if (opcode == 99) {
                return runningProgram;
            }

            int index1 = runningProgram.get(i+1);
            int value1 = runningProgram.get(index1);

            int index2 = runningProgram.get(i+2);
            int value2 = runningProgram.get(index2);

            int index3 = runningProgram.get(i+3);
            int value3 = runningProgram.get(index3);

            if (opcode == 1)
            {
                runningProgram.set(index3, value1 + value2);
            }
            else if (opcode == 2)
            {
                runningProgram.set(index3, value1 * value2);
            }
        }

        return runningProgram;
    }

    public static int nounVerbValue(List<Integer> program, int targetOutput)
    {
        for (int noun = 0; noun <= 99; noun++)
        {
            for (int verb = 0; verb <= 99; verb++)
            {
                List<Integer> runningProgram = fixProgram(program, noun, verb);
                int outputValue = runProgram(runningProgram).get(0);
                if (outputValue == targetOutput) {
                    return 100 * noun + verb;
                }
            }
        }
        return 0;
    }
}
