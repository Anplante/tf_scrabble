package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Antoine on 9/18/2016.
 */
public class ScrabbleGUITest {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ScrabbleGUI scrabbleGame;
    private Game game;
    private GameManager gameManager;
    private JButton btnFinish;
    private Player player;

    @Before
    public void setUp()  throws Exception {
        gameManager = new GameManager();
        List lstPlayer = new ArrayList<Player>();
        game = gameManager.createNewGame(lstPlayer);
        game.saveHumanPlayers("Antoine");
        game.saveHumanPlayers("Louis");
        game.saveHumanPlayers("Julien");
        scrabbleGame = new ScrabbleGUI(game, new Rectangle(screenSize));
        game.startGame();
        btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, "finish");
    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void testChangePlayer() {
        Player firstPlayer = game.getActivePlayer();
        btnFinish.doClick();
        assertNotEquals(firstPlayer, game.getActivePlayer());
        btnFinish.doClick();
        btnFinish.doClick();
        assertEquals(firstPlayer, game.getActivePlayer());
    }
}
