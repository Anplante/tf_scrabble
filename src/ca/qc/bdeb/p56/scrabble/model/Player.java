package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Classe qui contient les informations sur un joueur.
 * <p>
 * Created by Louis Luu Lim on 9/10/2016.
 */
public abstract class Player implements Observable {

    private String name;
    private Game game;
    private List<Tile> tiles;
    private State currentState;
    private int score;
    private boolean active;
    private Color playerColor;
    private transient LinkedList<Observateur> observateurs;

    public Player(String name) {
        this.name = name;
        tiles = new ArrayList<>();
        // TODO : recevoir une couleur
        playerColor = new Color(0, 0, 182, 155);
        active = false;
        observateurs = new LinkedList<>();
        setState(new StatePending(this));
    }

    public List<Tile> getTiles() {

        List<Tile> playerTiles = new LinkedList<>();

        for (Tile tile : tiles) {
            playerTiles.add(tile);
        }
        return playerTiles;
    }

    public int getLettersCount() {
        return tiles.size();
    }

    public Game getGame() {
        return game;
    }

    protected void setState(State newState) {
        currentState = newState;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addLetter(Tile tile) {
        tiles.add(tile);
    }

    public void remove(Tile tileSelected) {
        tiles.remove(tileSelected);
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

        for (Observateur ob : observateurs) {
            ob.changementEtat();
        }
    }

    @Override
    public void aviserObservateurs(Enum<?> e, Object o) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void selectNextState(IDState state) {
        currentState.selectNextState(state);
    }

    public State getState() {
        return currentState;
    }

    public void nextState() {

        currentState.execute();
        State newState = currentState.getNextState();
        currentState = newState;
        newState.initialize();
        aviserObservateurs();
    }

    public boolean isActivated() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract boolean isHumanPlayer();

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
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

        return tiles.size() < Game.MAX_TILES_IN_HAND;
    }

    public Color getColor() {
        return this.playerColor;
    }

    public void swapTile(Tile backupTile, Tile tileSelected) {

        Collections.swap(tiles, tiles.indexOf(backupTile), tiles.indexOf(tileSelected));
        aviserObservateurs();
    }



    /**
     * Pour l'utilisation des tests
     */
    public void emptyHand() {
        tiles.clear();
    }
}
