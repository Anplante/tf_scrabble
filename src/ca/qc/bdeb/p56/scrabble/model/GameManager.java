package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Language;

import java.io.File;
import java.util.List;

/**
 * Classe qui s'occupe de gérer une partie.
 *
 * Created by Louis Luu Lim on 9/10/2016.
 */
public class GameManager {

    private final String DEFAULT_GAME_PARAMS = "resources/files/scrabbleParameters.xml";

    private Game game;

    public GameManager() {
        game = null;
    }

    public Game createNewGame(List<Player> players, Language languageChoice) {
        game = new Game(players);
        game.setLanguage(languageChoice);
        game.setParameters(DEFAULT_GAME_PARAMS);
        
        return game;
    }


}
