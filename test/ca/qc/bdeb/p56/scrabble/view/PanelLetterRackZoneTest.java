package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
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
 * Created by Louis Luu Lim on 9/21/2016.
 */
public class PanelLetterRackZoneTest implements ConstanteTestName {

    private ScrabbleGUI scrabbleGame;
    private Game game;

    @Before
    public void setUp()  throws Exception {

        GameManager gameManager = new GameManager();
        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("simplistic.png");
        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame.createScrabbleGame(game);
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testChangePlayer() {

        JButton btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, PASS_TURN_NAME);
        Player firstPlayer = game.getActivePlayer();
        btnFinish.doClick();
        assertNotEquals(firstPlayer, game.getActivePlayer());
        btnFinish.doClick();
        btnFinish.doClick();
        assertEquals(firstPlayer, game.getActivePlayer());
    }
}