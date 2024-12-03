package aoc.dec_2015.day_19;

import aoc.dec_2015.helper.PuzzleInputLoader;
import aoc.dec_2015.helper.PuzzleInputLoaderImpl;

import java.util.*;
import java.util.stream.Collectors;

public class MedicineForRudolph {

    private final PuzzleInputLoader puzzleInputLoader;

    public MedicineForRudolph() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    // TODO Goudemond 2024/12/03 | Figure out how to solve this without ALL the combinatorics
    private void solvePuzzle1() {
//        List<String> rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_19/MedicineForRudolphDummy.txt");
//        List<String> rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_19/MedicineForRudolphTest.txt");
        List<String> rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_19/MedicineForRudolph001.txt");
        List<String> startingMolecule = splitOnCamelCase(rawPuzzleInput.get(rawPuzzleInput.size() - 1));
        rawPuzzleInput.remove(rawPuzzleInput.size() - 1);
        rawPuzzleInput.remove(rawPuzzleInput.size() - 1);
        System.out.println("startingMolecule = " + startingMolecule);
        Map<String, List<String>> replacementElements = getMapOfReplacementElements(rawPuzzleInput);
        replacementElements.forEach(
                (element, replacementCompound) ->
                        System.out.println(element + " => " + replacementCompound)
        );
        System.out.println("replacementElements.size() = " + replacementElements.size());
        System.out.println("startingMolecule.size() = " + startingMolecule.size());
//        int maxNumberOfPermutations = getMaxNumberOfPermutations(startingMolecule, replacementElements);
    }



    private List<String> splitOnCamelCase(String rawString) {
        char[] characters = rawString.toCharArray();
        List<String> camelCaseParts = new ArrayList<>();
        int trailingCursor = 0;
        int leadingCursor = 0;
        int stringLength = rawString.length() - 1;
        while (trailingCursor < stringLength) {
            leadingCursor = leadingCursor >= stringLength ? stringLength : ++leadingCursor;
            if (Character.isUpperCase(characters[leadingCursor])) {
                camelCaseParts.add(rawString.substring(trailingCursor, leadingCursor));
                trailingCursor = leadingCursor;
            }
            if (leadingCursor == stringLength) {
                break;
            }
        }
        camelCaseParts.add(rawString.substring(trailingCursor, leadingCursor + 1));
        return camelCaseParts;
    }

    private Map<String, List<String>> getMapOfReplacementElements(List<String> replacements) {
        Map<String, List<String>> replacementElements = new HashMap<>();
        for (String replacement : replacements) {
            String[] replacementInstruction = replacement.split(" => ");
            String element = replacementInstruction[0];
            String newCompound = replacementInstruction[1];
            if (replacementElements.containsKey(element)) {
                replacementElements.get(element).add(newCompound);
            } else {
                List<String> replacementInstructions = new ArrayList<>();
                replacementInstructions.add(newCompound);
                replacementElements.put(element, replacementInstructions);
            }
        }
        return replacementElements;
    }

    public static void main(String[] args) {
        MedicineForRudolph medicineForRudolph = new MedicineForRudolph();
        medicineForRudolph.solvePuzzle1();
    }
}
