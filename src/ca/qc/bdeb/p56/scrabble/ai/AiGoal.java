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

        List<String> results = new ArrayList<>();

        for (int i = 0; i < allLetters.length(); i++) {

            int resultLength = results.size();

            for(int j = 0; j < resultLength; j++)
            {
                String wordFormed = allLetters.charAt(i) + results.get(j);
                results.add(wordFormed);

                if (game.isValidWord( wordFormed) && !allPossibleCombination.contains(wordFormed)) {
                    allPossibleCombination.add(wordFormed);
                }
            }
            results.add(Character.toString(allLetters.charAt(i)));
        }
    }
}