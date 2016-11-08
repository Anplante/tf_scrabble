package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import ca.qc.bdeb.p56.scrabble.view.ButtonTile;
import ca.qc.bdeb.p56.scrabble.view.PanelLetterRackZone;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Antoine on 11/8/2016.
 */
public class AiStateTest {

    private Game game;
    private PanelLetterRackZone panelTested;
    private ScrabbleGUI scrabbleGame;
    private List<ButtonTile> btnTiles;
    private JPanel letterRack;
    private Player currentPlayer;

    @Before
    public void setUp()  throws Exception {

        ArrayList aiNames = new ArrayList<>();
        aiNames.add("Bot");
        aiNames.add("Bot2");
        aiNames.add("Bot3");

        GameManager gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new AiPlayer(aiNames));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("boardScrabble.jpeg");
        scrabbleGame.createScrabbleGame(game);

        panelTested = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.LETTER_RACK_NAME);
        letterRack = (JPanel) TestUtils.getChildNamed(panelTested, ConstanteTestName.LETTER_RACK_NAME);

        currentPlayer = game.getActivePlayer();
        btnTiles = new ArrayList<>();

        for (int i = 0; i < currentPlayer.getLettersCount(); i++) {
            btnTiles.add((ButtonTile) TestUtils.getChildNamed(letterRack, ConstanteTestName.TILE_NAME + i));
        }

    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void testStateHumanPlayer()  {
        State state = currentPlayer.getState();
        assertTrue(state instanceof StateSelectAction);
        state = currentPlayer.getState();
        game.passTurn();
        assertTrue(state instanceof StateSelectAction);

        btnTiles.get(4).doClick();
        state = currentPlayer.getState();
        assertTrue(state instanceof StatePlayTile);
    }

    @Ignore
    @Test
    public void testEndTurnAi()
    {
        Player aPlayer = game.getActivePlayer();
        assertTrue(aPlayer.isHumanPlayer());
        game.passTurn();
        assertTrue(aPlayer.isHumanPlayer());
    }
}
