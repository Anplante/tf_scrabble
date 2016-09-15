package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.security.KeyPairGeneratorSpi;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Player implements Observable {

    private String name = "lol";
    private Game game;
    private List<Letter> letters;
    private State currentState;
    private int score;
    private boolean active;

   // private final List<Letter> lettersOnHand;

    public Player(Game game)
    {
        this.game = game;
        letters = new ArrayList<Letter>();
        setState(new StatePending(this));
        active = false;
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

    public Game getGame() {
        return game;
    }


    protected void setState(State newState)
    {
        currentState = newState;
    }


    public void playTile(Square square) {

        currentState.playTile(square);
    }

    public void selectLetter(Letter letter) {
        currentState.selectTile(letter);
    }

    public State getState() {
        return currentState;
    }

    public void nextState() {
        currentState.execute();
        State newState =  currentState.getNextState();
        currentState = newState;
        newState.initialize();
        // aviserobservateur
    }

    public boolean isActivated() {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getName()
    {
        return name;
    }
    public int getScore() {
        return score;
    }
}
