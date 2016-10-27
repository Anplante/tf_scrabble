package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe qui va s'occuper de déterminer les éléments essentiels pour aller vers la victoire pour un tour.
 *
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
        permuteAllLetters("", allLetters);
        checkForWord();
        return listOfWord;
    }

    private void checkForWord() {
        int i = 0;
        for (String word : allPossibleCombination) {
            ++i;
            if (game.isValidWord(word)) {
                listOfWord.add(word);
            }
        }
    }

    private void permuteAllLetters(String prefix, String allLetters) {
        int n = allLetters.length();
        if (n == 0) allPossibleCombination.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permuteAllLetters(prefix + allLetters.charAt(i), allLetters.substring(0, i) + allLetters.substring(i+1, n));
        }
    }
}