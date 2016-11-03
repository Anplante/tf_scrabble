package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchangeTest{

    private PanelLetterRackZone panelTested;
    private Game game;
    private JButton btnEchanger;
    private JButton btnCancel;
    private List<ButtonTile> btnTiles;
    private ScrabbleGUI scrabbleGame;
    private JPanel letterRack;
    private Player currentPlayer;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        lstPlayer.add(new HumanPlayer("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("simplistic.png");
        scrabbleGame.createScrabbleGame(game);

        panelTested = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.LETTER_RACK_NAME);
        btnEchanger = (JButton) TestUtils.getChildNamed(panelTested, ConstanteTestName.EXCHANGE_NAME);
        btnCancel = (JButton) TestUtils.getChildNamed(panelTested, ConstanteTestName.CANCEL_EXCHANGE_NAME);
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

    @Test
    public void testEchangeChangePlayer() {
        assertEquals(currentPlayer, game.getActivePlayer());
        btnEchanger.doClick();
        btnTiles.get(4).doClick();
        btnEchanger.doClick();
        assertNotEquals(currentPlayer, game.getActivePlayer());
    }


    @Test
    public void testExchangeOnlyClickOrdered() {

        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();
        btnEchanger.doClick();


        for (ButtonTile tileToSelect : btnTiles) {
            tileToSelect.doClick();
        }

        btnEchanger.doClick();


        assertNotEquals(playerTileBeforeExchange, currentPlayer.getTiles());

    }

    @Test
    public void testCancel() {

        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();

        btnEchanger.doClick();
        btnTiles.get(4).doClick();
        btnTiles.get(5).doClick();
        btnTiles.get(6).doClick();
        btnCancel.doClick();

        assertEquals(playerTileBeforeExchange, currentPlayer.getTiles());
    }
}

