package ca.qc.bdeb.p56.scrabble.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class GameManager {

    private Game game;

    private final String GAME_FILE = "resources/scrabbleParameters.xml";


    private List<Letter> alphabetBag;

    public GameManager() {
        game = null;
    }


    public Game createNewGame(List<Player> players) {
        game = new Game(GAME_FILE, players);
        return game;
    }


    public void saveGame(File file)
    {

    }

    public Game loadGame(File file)
    {
        Game game = null;

        return game;
    }

}
