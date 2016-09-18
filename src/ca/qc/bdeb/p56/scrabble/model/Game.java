package ca.qc.bdeb.p56.scrabble.model;

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



    public Game(Element rootElement, List<Letter> alphabetBag, List<Player> players)
    {

        boardManager = createBoard(rootElement);
        this.alphabetBag = alphabetBag;


        // tests
        this.players = players;

    }



    private BoardManager createBoard(Element rootElement)
    {
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


    public void goToNextState()
    {
        if(isReadyForNextPhase()){
            getActivePlayer().nextState();

            while(!getActivePlayer().isActivated())
            {
                activateNextPlayer();
            }
        }
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

    public int lettersLeft()
    {
        return alphabetBag.size();
    }

    public int getPlayersLeft()
    {
        return players.size();
    }

    public Player getActivePlayer() {
        return players.get(activePlayerIndex);
    }



    public Square getSquare(int row, int column) {
        return boardManager.getSquare(row, column);
    }

    public String getContentSquare(int row, int column)
    {
        return boardManager.getContentSquare(row, column);
    }

    public void playTile(Square square) {
       getActivePlayer().playTile(square);
    }

    public void selectLetter(Letter letter) {
        getActivePlayer().selectLetter(letter);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isReadyForNextPhase() {
        return getActivePlayer().getState().readyForNextState();
    }
}
