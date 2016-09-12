package ca.qc.bdeb.p56.scrabble.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Game {

    private BoardManager boardManager;
    private List<Player> players;
    private int activePlayer;
    private ArrayList<Tile> tileBag;

    private Random randomGenerator;


    public Game()
    {

        boardManager = createBoard();
    }

    public void setTileBag(ArrayList<Tile> newBag){
        tileBag = newBag;
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

    public Tile drawCard(){
        randomGenerator = new Random();
        int intTile = randomGenerator.nextInt(tileBag.size());
        Tile tileRandomed = tileBag.get(intTile);
        tileBag.remove(intTile);
        return  tileRandomed;
    }


    public void startGame() {
    }
}
