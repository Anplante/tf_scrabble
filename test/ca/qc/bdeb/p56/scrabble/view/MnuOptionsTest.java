package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Julien Brosseau on 10/5/2016.
 */
public class MnuOptionsTest {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ScrabbleGUI scrabbleGame;
    private Game game;
    private GameManager gameManager;
    private JButton btnFinish;
    private Player player;


    PanelLetterRackZone zoneJoueur;


    @Before
    public void setUp() throws Exception {

        gameManager = new GameManager();

        java.util.List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI(game, new Rectangle(screenSize));

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void openMnuTest() throws AWTException {
        Robot robot = new Robot();

        // Simulate a mouse click
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);


    }
}

