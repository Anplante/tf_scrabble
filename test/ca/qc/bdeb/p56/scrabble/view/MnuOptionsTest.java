package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

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

/*        List<Player> lstPlayer = new ArrayList<>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));*/

        //game = gameManager.createNewGame(lstPlayer);

        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("simplistic.png");

        MainMenuGUI frame = scrabbleGame.getMenu();
        JButton btnAccept = (JButton) TestUtils.getChildNamed(frame, "Confirm");
        btnAccept.doClick();
        // scrabbleGame.createScrabbleGame(game);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void openMnuTest() throws AWTException {

        Robot robot = new Robot();
        scrabbleGame.setVisible(true);
        boolean success = scrabbleGame.hasFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);

        options = (MnuOptions) TestUtils.getChildNamed(scrabbleGame, "Options");
        assertEquals(true, options.isVisible());
    }

    @Test
    public void returnMenuBtnTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true, options.isVisible());
        retourner.doClick();
        assertNotEquals(true, options.isVisible());
    }

    @Test
    public void abandonMenuBtnTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true, options.isVisible());
        retourner.doClick();
        assertNotEquals(true, options.isVisible());
    }

    @Test
    public void closeMnuTest() throws AWTException {
        Robot robot = new Robot();
        scrabbleGame.requestFocus();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertEquals(true, options.isVisible());
        robot.keyPress(KeyEvent.VK_ESCAPE);
        assertNotEquals(true, options.isVisible());

    }
}


