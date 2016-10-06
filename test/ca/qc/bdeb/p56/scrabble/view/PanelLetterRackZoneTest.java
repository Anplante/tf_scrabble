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

import static org.junit.Assert.*;

/**
 * Created by TheFrenchOne on 9/21/2016.
 */
public class PanelLetterRackZoneTest {

    PanelLetterRackZone zoneJoueur;



    JButton btnPlayWord;

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
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));
        scrabbleGame = new ScrabbleGUI();
        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame.createScrabbleGame(game);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testChangePlayer() {
        btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, "Pass turn");
        Player firstPlayer = game.getActivePlayer();
        btnFinish.doClick();
        assertNotEquals(firstPlayer, game.getActivePlayer());
        btnFinish.doClick();
        btnFinish.doClick();
        assertEquals(firstPlayer, game.getActivePlayer());
    }
}