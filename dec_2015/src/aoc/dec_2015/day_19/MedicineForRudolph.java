package aoc.dec_2015.day_19;

import aoc.dec_2015.helper.PuzzleInputLoader;
import aoc.dec_2015.helper.PuzzleInputLoaderImpl;

import java.util.*;

public class MedicineForRudolph {

    private final PuzzleInputLoader puzzleInputLoader;

    public MedicineForRudolph() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    private void solvePuzzle1() {
        // confirm startingMolecule when mix of elements
//        List<String> rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_19/MedicineForRudolphDummy.txt");
        List<String> rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_19/MedicineForRudolphTest.txt");
        List<String> startingMolecule = getStartingMolecule(rawPuzzleInput);
        rawPuzzleInput.remove(rawPuzzleInput.size() - 1);
        rawPuzzleInput.remove(rawPuzzleInput.size() - 1);
        System.out.println("startingMolecule = " + startingMolecule);
        Map<String, List<String>> replacementElements = getMapOfReplacementElements(rawPuzzleInput);
        replacementElements.forEach(
                (element, replacementCompound) ->
                        System.out.println(element + " => " + replacementCompound)
        );
        // TODO Goudemond 2024/12/02 | Carry on from here
        //        int maxNumberOfPermutations = getMaxNumberOfPermutations(startingMolecule, replacementElements);
    }

    private List<String> getStartingMolecule(List<String> rawPuzzleInput) {
        String rawMolecule = rawPuzzleInput.get(rawPuzzleInput.size() - 1);
        char[] startingMolecule = rawMolecule.toCharArray();
        List<String> startingMoleculeParts = new ArrayList<>();
        int trailingCursor = 0;
        int leadingCursor = 0;
        int rawMoleculeLength = rawMolecule.length() - 1;
        while (true) {
            if (!(trailingCursor < rawMoleculeLength)) break;
            leadingCursor = leadingCursor >= rawMoleculeLength ? rawMoleculeLength : ++leadingCursor;
            if (Character.isUpperCase(startingMolecule[leadingCursor])) {
                startingMoleculeParts.add(rawMolecule.substring(trailingCursor, leadingCursor));
                trailingCursor = leadingCursor;
            }
            if (leadingCursor == rawMoleculeLength){
                break;
            }
        }
        startingMoleculeParts.add(rawMolecule.substring(trailingCursor, leadingCursor + 1));
        return startingMoleculeParts;
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
