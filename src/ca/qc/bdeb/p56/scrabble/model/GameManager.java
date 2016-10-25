package ca.qc.bdeb.p56.scrabble.model;

import java.io.File;
import java.util.List;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;

    private final String GAME_FILE = "resources/files/scrabbleParameters.xml";


    private List<Tile> alphabetBag;

    public GameManager() {
        game = null;
    }


    public Game createNewGame(List<Player> players) {
        game = new Game(GAME_FILE, players);
        return game;
    }


    // TODO Louis : Éventuellement?
    public void saveGame(File file)
    {

    }

    public Game loadGame(File file)
    {
        Game game = null;

        return game;
    }

}
