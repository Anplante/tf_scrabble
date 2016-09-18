package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;

/**
 * Created by TheFrenchOne on 9/18/2016.
 */
public class BtnSquareTest {


    private BtnSquare square;


    @Before
    public void setUp()  throws Exception {

        GameManager gameManager = new GameManager();
        Game game = null;
        java.util.List<Player> players = new ArrayList<Player>();
        players.add(new Player(game, "Louis"));
        game = gameManager.createNewGame(players);

        square = new BtnSquare(game, 0, 0);

    }

    @After
    public void tearDown() throws Exception {


    }
    @Test
    public void testSelectSquare() throws AWTException {


    }

}