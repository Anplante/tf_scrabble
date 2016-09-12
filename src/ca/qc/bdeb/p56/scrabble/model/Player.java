package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Player implements Observable {


    private Game game;
    private List<Letter> letters;

   // private final List<Letter> lettersOnHand;

    public Player(Game game)
    {
        this.game = game;
        letters = new ArrayList<Letter>();
    }


    public void addLetter(Letter letter)
    {
        letters.add(letter);
    }

    @Override
    public void ajouterObservateur(Observateur o) {

    }

    @Override
    public void retirerObservateur(Observateur o) {

    }

    @Override
    public void aviserObservateurs() {

    }

    @Override
    public void aviserObservateurs(Enum<?> e, Object o) {

    }

    public List<Letter> getLetters() {
        return letters;
    }
}
