package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Player implements Observable {

    private String name;
    private Game game;
    private List<Tile> tiles;
    private State currentState;
    private int score;
    private boolean active;
    private Color playerColor;
    private transient LinkedList<Observateur> observateurs;  //transient = not be serialized

   // private final List<Tile> lettersOnHand;

    public Player(String name)
    {
        this.name  = name;
        this.game = game;
        tiles = new ArrayList<Tile>();
        // TODO : recevoir une couleur
        playerColor = new Color(0,0,182,155); // light blue
        setState(new StatePending(this));
        active = false;
        observateurs = new LinkedList<>();
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
    public void addLetter(Tile tile)
    {
        tiles.add(tile);
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

    public List<Tile> getTiles() {
        return tiles;
    }

    public Game getGame() {
        return game;
    }


    protected void setState(State newState)
    {
        currentState = newState;
    }
    

    public void selectNextState(IDState state)
    {
        currentState.selectNextState(state);
    }

    
    public State getState() {
        return currentState;
    }

    public void nextState() {

        currentState.execute();
        State newState =  currentState.getNextState();
        currentState = newState;
        newState.initialize();
        aviserObservateurs();
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

    public void remove(Tile tileSelected) {
        tiles.remove(tileSelected);
    }

    public void selectSquare(Square square) {
        currentState.selectSquare(square);
    }

    public void selectTile(Tile tile) {
        currentState.selectTile(tile);

    }

    public void addPoints(int points) {
        score += points;
    }

    public boolean canDraw() {
        return tiles.size() < 7;
    }

    public Color getColor() { return this.playerColor; }
}
