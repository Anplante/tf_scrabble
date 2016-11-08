package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Antoine on 11/8/2016.
 */
public class AiStateTest {

    private Game game;
    private ArrayList<Player> players;

    @Before
    public void setUp()  throws Exception {
        ArrayList aiNames = new ArrayList<>();
        aiNames.add("Bot");
        GameManager gameManager = new GameManager();
        players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        players.add(new AiPlayer(aiNames));
        game = gameManager.createNewGame(players);
        game.startGame();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testStateExchange()
    {
        assertTrue(game.getActivePlayer().isHumanPlayer());
    }
}
