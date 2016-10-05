package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import ca.qc.bdeb.p56.scrabble.view.BtnTile;
import ca.qc.bdeb.p56.scrabble.view.PanelLetterRackZone;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
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
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchangeTest {

    private PanelLetterRackZone panelTested;
    private Game game;
    private JButton btnEchanger;
    private JButton btnCancel;
    private List<BtnTile> btnTiles;
    private ScrabbleGUI scrabbleGame;
    private JPanel letterRack;


    private Player currentPlayer;

    public StateExchangeTest() {
    }

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));

        game = gameManager.createNewGame(lstPlayer);

        scrabbleGame = new ScrabbleGUI(game, new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

        panelTested = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, "Player letter rack");
        btnEchanger = (JButton) TestUtils.getChildNamed(panelTested, "Exchange");
        btnCancel = (JButton) TestUtils.getChildNamed(panelTested, "Cancel_Exchange");
        letterRack = (JPanel) TestUtils.getChildNamed(panelTested, "Letter rack");
        currentPlayer = game.getActivePlayer();

        btnTiles = new ArrayList<>();

        for (int i = 0; i < currentPlayer.getLettersCount(); i++) {
            btnTiles.add((BtnTile) TestUtils.getChildNamed(letterRack, "Tile" + i));
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


        for (BtnTile tileToSelect : btnTiles) {
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

