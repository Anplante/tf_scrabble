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
    private ArrayList<String> listOfWord;
    private ArrayList<String> allPossibleCombination;

    public AiGoal(Game game) {
        this.game = game;
        this.listOfWord = new ArrayList<>();
    }

    public ArrayList<String> getPossibleWord(String allLetters) {
        allPossibleCombination = new ArrayList<>();
        combinations(allLetters);
        return allPossibleCombination;
    }

    private void combinations(String allLetters) {
        long startTime = System.currentTimeMillis();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < allLetters.length(); i++) {

            int resultLength = results.size();

            for (int j = 0; j < resultLength; j++) {
                String wordFormed = results.get(j) + allLetters.charAt(i);

                if (!results.contains(wordFormed))
                    results.add(wordFormed);

                if (game.isValidWord(wordFormed) && !allPossibleCombination.contains(wordFormed)) {
                    allPossibleCombination.add(wordFormed);
                }
            }
            if (!results.contains(Character.toString(allLetters.charAt(i))))
                results.add(Character.toString(allLetters.charAt(i)));
        }

        allLetters = new StringBuilder(allLetters).reverse().toString();

        for (int i = 0; i < allLetters.length(); i++) {

            int resultLength = results.size();

            for (int j = 0; j < resultLength; j++) {
                String wordFormed = results.get(j) + allLetters.charAt(i);
                if (!results.contains(wordFormed))
                    results.add(wordFormed);
                if (game.isValidWord(wordFormed) && !allPossibleCombination.contains(wordFormed)) {
                    allPossibleCombination.add(wordFormed);
                }
            }

        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }
}