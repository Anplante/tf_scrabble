package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;

    public GameManager()
    {
        game = null;
    }


    public Game createGame()
    {
        game = new Game();
        return game;
    }
}
