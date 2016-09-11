package ca.qc.bdeb.p56.scrabble.model;

import java.util.List;
import java.util.Random;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Game {

    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayer;

    private Random randomGenerator;


    public Game()
    {

        boardManager = createBoard();
    }

    public String getContentSquare(int row, int column)
    {
        return boardManager.getContentSquare(row, column);
    }

    private BoardManager createBoard()
    {
        BoardManager newBoardManager = new BoardManager();
        newBoardManager.createBoard();

        return newBoardManager;
    }


    public void startGame() {
    }
}
