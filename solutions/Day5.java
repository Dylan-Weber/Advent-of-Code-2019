import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("./src/input.txt");
        Scanner reader = new Scanner(inputFile);
        String rawInput = reader.nextLine();
        List<Integer> program = Arrays.stream(rawInput.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> partOneInput = List.of(1);
        List<Integer> partOneOutput = runProgram(new ArrayList<>(program), partOneInput);
        System.out.println("Part One: " + partOneOutput);

        List<Integer> partTwoInput = List.of(5);
        List<Integer> partTwoOutput = runProgram(new ArrayList<>(program), partTwoInput);
        System.out.println("Part Two: " + partTwoOutput);
    }

    public static List<Integer> runProgram(List<Integer> program, List<Integer> input) {
        List<Integer> output = new ArrayList<>();
        Queue<Integer> remainingInput = new ArrayDeque<>(input);

        int currentPosition = 0;
        while (currentPosition < program.size() && program.get(currentPosition) != 99) {
            int newPosition = runInstructionAndGetNewIndex(currentPosition, program, remainingInput, output);
            if (program.get(currentPosition) == 99) {
                break;
            }
            currentPosition = newPosition;
        }

        return output;
    }

    public static int runInstructionAndGetNewIndex(int index, List<Integer> program, Queue<Integer> input, List<Integer> output) {
        int currentInstruction = program.get(index);
        int opcode = getOpcode(currentInstruction);
        int[] modes = getModes(currentInstruction);

        if (opcode == 1) {
            int value0 = getFinalValue(program.get(index + 1), modes[0], program);
            int value1 = getFinalValue(program.get(index + 2), modes[1], program);
            int indexToSet = program.get(index + 3);
            program.set(indexToSet, value0 + value1);
            return index + 4;
        } else if (opcode == 2) {
            int value0 = getFinalValue(program.get(index + 1), modes[0], program);
            int value1 = getFinalValue(program.get(index + 2), modes[1], program);
            int indexToSet = program.get(index + 3);
            program.set(indexToSet, value0 * value1);
            return index + 4;
        } else if (opcode == 3) {
            int indexToSet = program.get(index + 1);
            int inputValue = input.remove();
            program.set(indexToSet, inputValue);
            return index + 2;
        } else if (opcode == 4) {
            int valueToOutput = getFinalValue(program.get(index + 1), modes[0], program);
            output.add(valueToOutput);
            return index + 2;
        } else if (opcode == 5) {
            int value0 = getFinalValue(program.get(index + 1), modes[0], program);
            int value1 = getFinalValue(program.get(index + 2), modes[1], program);

            return (value0 == 0)? index + 3 : value1;
        } else if (opcode == 6) {
            int value0 = getFinalValue(program.get(index + 1), modes[0], program);
            int value1 = getFinalValue(program.get(index + 2), modes[1], program);

            return (value0 != 0) ? index + 3 : value1;
        } else if (opcode == 7) {
            int value0 = getFinalValue(program.get(index + 1), modes[0], program);
            int value1 = getFinalValue(program.get(index + 2), modes[1], program);
            int indexToSet = program.get(index + 3);
            program.set(indexToSet, (value0 < value1) ? 1 : 0);
            return index + 4;
        }  else if (opcode == 8) {
            int value0 = getFinalValue(program.get(index + 1), modes[0], program);
            int value1 = getFinalValue(program.get(index + 2), modes[1], program);
            int indexToSet = program.get(index + 3);
            program.set(indexToSet, (value0 == value1) ? 1 : 0);
            return index + 4;
        }
        return -1;
    }

    public static int getOpcode(int instruction) {
        int firstDigit = (instruction/10) % 10;
        int secondDigit = instruction % 10;
        return firstDigit * 10 + secondDigit;
    }

    public static int[] getModes(int instruction) {
        int processedInstruction = instruction / 100;
        int[] modes = new int[3];
        for (int i = 0; processedInstruction > 0; i++) {
            modes[i] = processedInstruction % 10;
            processedInstruction /= 10;
        }
        return modes;
    }

    public static int getFinalValue(int value, int mode, List<Integer> program) {
        switch (mode) {
            case 0:
                return program.get(value);
            case 1:
                return value;
            default:
                return 0;
        }
    }
}
