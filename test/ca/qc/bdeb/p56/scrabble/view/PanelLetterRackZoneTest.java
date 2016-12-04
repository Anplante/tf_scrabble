package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Louis Luu Lim on 9/21/2016.
 */
public class PanelLetterRackZoneTest {

    private ScrabbleGUI scrabbleGame;
    private Game game;
    private Player currentPlayer;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();
        List<Player> lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        lstPlayer.add(new HumanPlayer("Julien"));
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("simplistic.png");
        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame.setGameTheme(Theme.BASIC);
        scrabbleGame.createScrabbleGame(game);
        currentPlayer = game.getActivePlayer();
    }

    @After
    public void tearDown() throws Exception {

        game = null;
        currentPlayer = null;
    }


    @Test
    public void testChangePlayer() {

        JButton btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.PASS_TURN_NAME);

        btnFinish.doClick();
        assertNotEquals(currentPlayer, game.getActivePlayer());

        for (int i = 0; i < game.getPlayersLeft() - 1; i++) {
            btnFinish.doClick();
        }

        assertEquals(currentPlayer, game.getActivePlayer());
    }

}