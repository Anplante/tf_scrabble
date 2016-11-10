package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Dictionary;
import ca.qc.bdeb.p56.scrabble.model.Game;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe qui va s'occuper de déterminer les éléments essentiels pour aller vers la victoire pour un tour.
 * <p>
 * Created by Antoine on 9/28/2016.
 */
public class AiGoal {

    private Game game;
    private List<String> listOfWord;
    private List<String> allPossibleCombination;
    private List<String> combinaisons;

    public AiGoal(Game game) {
        this.game = game;
        this.listOfWord = new ArrayList<>();
    }

    public List<String> getPossibleWord(String allLetters) {
        allPossibleCombination = new ArrayList<>();
        combinaisons = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        findAllCombinationsWords("", allLetters);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
        return allPossibleCombination;
    }


    private void findAllCombinationsWords(String root, String letters) {

        for (int i = 0; i < letters.length(); i++) {

            String wordFormed = root + letters.charAt(i);

            if (!combinaisons.contains(wordFormed))
                combinaisons.add(wordFormed);

            if (wordFormed.length() > 1 && game.isValidWord(wordFormed) && !allPossibleCombination.contains(wordFormed)) {
                allPossibleCombination.add(wordFormed);
            }

            StringBuilder temp = new StringBuilder(letters);
            temp.deleteCharAt(i);

            findAllCombinationsWords(wordFormed, temp.toString());

        }
    }
}