package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import sun.misc.Launcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    private BufferedImage playerIcon;
    private transient LinkedList<Observateur> observateurs;

    private static final URL DEFAULT_PLAYER_ICON = Launcher.class.getResource("/images/default.png");

    public Player(String name) {
        this.name = name;
        tiles = new ArrayList<>();
        // TODO : recevoir une couleur
        playerColor = new Color(0, 0, 182, 155);
        active = false;
        observateurs = new LinkedList<>();
        setPlayerIcon(DEFAULT_PLAYER_ICON);
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

    public void changePlayer() {
        nextState();
    }

    public boolean isActivated() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPlayerIcon(URL playerIcon) {
        this.playerIcon = null;
        try {
            this.playerIcon = ImageIO.read(playerIcon);
        } catch (IOException ex) {
            Logger.getLogger("Impossible de trouver l'image situé à : " + playerIcon).log(Level.SEVERE, null, ex);
        }
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

    public void reducePoints(int value) {
        score -= value;
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

    public Tile getTile(String letterWanted) {

        int index = 0;
        boolean tileFound = false;
        Tile tileWanted = null;

        while(!tileFound && index < tiles.size())
        {
            if(tileFound = tiles.get(index).getLetter().equals(letterWanted))
            {
                tileWanted = tiles.get(index);
            }
            index++;
        }
        return tileWanted;
    }

    public void shuffleTiles() {
        Collections.shuffle(tiles);
        aviserObservateurs();
    }
}
