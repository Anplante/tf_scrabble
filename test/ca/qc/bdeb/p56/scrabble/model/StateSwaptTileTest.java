package ca.qc.bdeb.p56.scrabble.model;

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

public class StateSwaptTileTest {

    private PanelLetterRackZone panelTested;
    private Game game;
    private JButton btnEchanger;
    private JButton btnCancel;
    private List<ButtonTile> btnTiles;
    private ScrabbleGUI scrabbleGame;
    private JPanel letterRack;


    private Player currentPlayer;

    public StateSwaptTileTest() {
    }

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));

        game = gameManager.createNewGame(lstPlayer);

        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("simplistic.png");
        scrabbleGame.createScrabbleGame(game);

        panelTested = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, "Player letter rack");
       // btnEchanger = (JButton) TestUtils.getChildNamed(panelTested, "Exchange");
       // btnCancel = (JButton) TestUtils.getChildNamed(panelTested, "Cancel_Exchange");
        letterRack = (JPanel) TestUtils.getChildNamed(panelTested, "Letter rack");
        currentPlayer = game.getActivePlayer();

        btnTiles = new ArrayList<>();

        for (int i = 0; i < currentPlayer.getLettersCount(); i++) {
            btnTiles.add((ButtonTile) TestUtils.getChildNamed(letterRack, "Tile" + i));
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void exchangeOnlySwapStateTest() {

        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();

        JButton firstButton = (JButton) TestUtils.getChildNamed(letterRack, "Tile" + 1); //btnTiles.get(2).doClick();
        firstButton.doClick();
        JButton secondButton = (JButton) TestUtils.getChildNamed(letterRack, "Tile" + 2); //btnTiles.get(2).doClick();
        secondButton.doClick();

        assertEquals(playerTileBeforeExchange.get(1), currentPlayer.getTiles().get(2));
        assertEquals(playerTileBeforeExchange.get(2), currentPlayer.getTiles().get(1));

    }


    @Test
    public void swapToPlaceTestt() {

        BtnSquare btnSquare = new BtnSquare(game,5,5);

        btnTiles.get(2).doClick();
        btnSquare.doClick();

        assertEquals(6, currentPlayer.getTiles().size());

    }


    @Test
    public void testDoubleClick() {

        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();

        btnTiles.get(2).doClick();
        btnTiles.get(2).doClick();

        assertEquals(playerTileBeforeExchange, currentPlayer.getTiles());
    }
}

