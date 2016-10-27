package ca.qc.bdeb.p56.scrabble.model;

import java.io.File;
import java.util.List;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;

    private final String DEFAULT_GAME_PARAMS = "resources/files/scrabbleParameters.xml";

    public GameManager() {
        game = null;
    }

    public Game createNewGame(List<Player> players) {
        game = new Game(DEFAULT_GAME_PARAMS, players);
        return game;
    }
}
