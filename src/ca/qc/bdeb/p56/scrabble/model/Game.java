package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Game {

    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayerIndex;
    private static List<Letter> alphabetBag;

    private static final Random randomGenerator = new Random();


    public Game(Element rootElement, List<Letter> alphabetBag, List<Player> players) {

        boardManager = createBoard(rootElement);
        this.alphabetBag = alphabetBag;

        // tests
        this.players = players;

        for(Player player : players)
        {
            player.setGame(this);
        }

    }


    private BoardManager createBoard(Element rootElement) {
        BoardManager newBoardManager = new BoardManager();
        newBoardManager.createBoard(rootElement);

        return newBoardManager;
    }


    public void startGame() {

        activePlayerIndex = randomGenerator.nextInt(players.size());
        initPlayerRack();

        for (Player player : players) {
            player.getState().initialize(); // rien pour le moment
        }
        goToNextState();
    }


    public void goToNextState() {

        getActivePlayer().nextState();

        if (!getActivePlayer().isActivated()) {

            activateNextPlayer();
        }
    }

    public void passTurn() {
        getActivePlayer().selectMode(IDState.PENDING);
        goToNextState();
    }

    private void activateNextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % players.size();
        getActivePlayer().nextState();
    }

    private void initPlayerRack() {

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < players.size(); j++) {
                Letter letter = alphabetBag.get(randomGenerator.nextInt(alphabetBag.size()));
                players.get(j).addLetter(letter);
                alphabetBag.remove(letter);
            }
        }
    }

    public int lettersLeft() {
        return alphabetBag.size();
    }

    public int getPlayersLeft() {
        return players.size();
    }

    public Player getActivePlayer() {
        return players.get(activePlayerIndex);
    }


    public Square getSquare(int row, int column) {
        return boardManager.getSquare(row, column);
    }

    public char getContentSquare(int row, int column) {
        return boardManager.getContentSquare(row, column);
    }

    public String getPremiumSquare(int row, int column) {
        return boardManager.getPremiumSquare(row, column);
    }

    public void playTile(Square square) {
        getActivePlayer().selectMode(square);
    }

    public void selectLetter(Letter letter) {
        getActivePlayer().selectMode(letter);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isReadyForNextPhase() {
        return getActivePlayer().getState().readyForNextState();
    }

    public String getState() {
        return getActivePlayer().getState().getName();
    }
}
