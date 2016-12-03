package ca.qc.bdeb.p56.scrabble.utility;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheFrenchOne on 10/24/2016.
 */
public class LocalizationTest {

    private Game game;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        players.add(new HumanPlayer("Antoine"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }


    @Test
    public void testGameInFrench() {

    }
}
