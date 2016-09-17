package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.security.KeyPairGeneratorSpi;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Player implements Observable {

    private String name;
    private Game game;
    private List<Letter> letters;
    private State currentState;
    private int score;
    private boolean active;
    private transient LinkedList<Observateur> observateurs;  //transient = not be serialized

   // private final List<Letter> lettersOnHand;

    public Player(Game game, String name)
    {
        this.name  = name;
        this.game = game;
        letters = new ArrayList<Letter>();
        setState(new StatePending(this));
        active = false;
        observateurs = new LinkedList<>();
    }

    public void addLetter(Letter letter)
    {
        letters.add(letter);
    }

    @Override
    public void ajouterObservateur(Observateur ob) {

            observateurs.add(ob);
    }

    @Override
    public void retirerObservateur(Observateur ob) {
        observateurs.remove(ob);
    }

    @Override
    public void aviserObservateurs() {
        for(Observateur ob : observateurs)
        {
            ob.changementEtat();
        }
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

        currentState.selectMode(square);
    }

    public void selectLetter(Letter letter) {
        currentState.selectMode(letter);
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

    public void remove(Letter letterSelected) {
        letters.remove(letterSelected);
    }
}
