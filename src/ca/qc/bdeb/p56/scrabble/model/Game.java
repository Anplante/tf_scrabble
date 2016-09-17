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



    public Game(Element rootElement, List<Letter> alphabetBag)
    {

        boardManager = createBoard(rootElement);
        this.alphabetBag = alphabetBag;


        // tests
        players = new ArrayList<Player>();
        //players.add(new Player(this));
        //players.add(new Player(this));
    }

    public String getContentSquare(int row, int column)
    {
        return boardManager.getContentSquare(row, column);
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
        getActivePlayerIndex().nextState();

        while(!getActivePlayerIndex().isActivated())
        {
            activateNextPlayer();
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    private void activateNextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % players.size();
        getActivePlayerIndex().nextState();
    }

    private void initPlayerRack() {

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < players.size(); j++) {
                    Letter letter = alphabetBag.get(randomGenerator.nextInt(alphabetBag.size()));
                players.get(j % 2).addLetter(letter);
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

    public Player getActivePlayerIndex() {
        return players.get(activePlayerIndex);
    }

    public Square getSquare(int row, int column) {
        return boardManager.getSquare(row, column);
    }

    public void playTile(Square square) {
       getActivePlayerIndex().playTile(square);
    }

    public void selectLetter(Letter letter) {
        getActivePlayerIndex().selectLetter(letter);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
