package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Language;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by TheFrenchOne on 11/6/2016.
 */
public class BoardManagerTest {

    private Game game;
    private BoardManager boardManager;


    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        game = gameManager.createNewGame(players, Language.FRENCH);
        boardManager = game.getBoardManager();

    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }
}
