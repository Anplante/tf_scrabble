package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import ca.qc.bdeb.p56.scrabble.view.BtnSquare;
import ca.qc.bdeb.p56.scrabble.view.ButtonTile;
import ca.qc.bdeb.p56.scrabble.view.PanelLetterRackZone;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Julien Brosseau on 10/6/2016.
 */

public class StateSwaptTileTest{

    private PanelLetterRackZone panelTested;
    private Game game;
    private List<ButtonTile> btnTiles;
    private ScrabbleGUI scrabbleGame;
    private JPanel letterRack;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;


    private Player currentPlayer;

    public StateSwaptTileTest() {
    }

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
        scrabbleGame.setImgPath(ConstanteComponentMessage.RES_IMAGES_FR_BASIC);
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

    @Test
    public void exchangeOnlySwapStateTest() {

        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();

        JButton firstButton = (JButton) TestUtils.getChildNamed(letterRack, ConstanteTestName.TILE_NAME + 1); //btnTiles.get(2).doClick();
        firstButton.doClick();
        JButton secondButton = (JButton) TestUtils.getChildNamed(letterRack, ConstanteTestName.TILE_NAME + 2); //btnTiles.get(2).doClick();
        secondButton.doClick();

        assertEquals(playerTileBeforeExchange.get(1), currentPlayer.getTiles().get(2));
        assertEquals(playerTileBeforeExchange.get(2), currentPlayer.getTiles().get(1));

    }


    @Test
    public void testDoubleClick() {

        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();

        btnTiles.get(2).doClick();
        btnTiles.get(2).doClick();

        assertEquals(playerTileBeforeExchange, currentPlayer.getTiles());
    }
}

