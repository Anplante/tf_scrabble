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
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Julien Brosseau on 10/5/2016.
 */
public class MnuOptionsTest {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ScrabbleGUI scrabbleGame;
    private Game game;
    private GameManager gameManager;
    private JButton abandon;
    private JButton retourner;
    private MnuOptions options;




    PanelLetterRackZone zoneJoueur;


    @Before
    public void setUp() throws Exception {

        gameManager = new GameManager();

        java.util.List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.createScrabbleGame(game);

        options = (MnuOptions) TestUtils.getChildNamed(scrabbleGame, "Options");
        abandon = (JButton) TestUtils.getChildNamed(options, "Abandon");
        retourner = (JButton) TestUtils.getChildNamed(options, "Return");
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void openMnuTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true,options.isVisible());


    }

    @Test
    public void returnMenuBtnTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true,options.isVisible());
        retourner.doClick();
        assertNotEquals(true,options.isVisible());
    }

    @Test
    public void abandonMenuBtnTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true,options.isVisible());
        retourner.doClick();
        assertNotEquals(true,options.isVisible());
    }

    @Test
    public void closeMnuTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true,options.isVisible());
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertNotEquals(true,options.isVisible());

    }
}


