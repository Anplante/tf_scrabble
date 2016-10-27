package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Louis Luu Lim on 9/18/2016.
 */
public class BtnSquareTest {


    private BtnSquare btnSquare;
    private ButtonTile btnTile;
    private Game gameModel;
    private PanelLetterRackZone zoneJoueur;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        java.util.List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        gameModel = gameManager.createNewGame(players);

        btnSquare = new BtnSquare(gameModel, 7, 7);
        btnSquare.setSize(WIDTH,HEIGHT);
        gameModel.startGame();

    }

    @After
    public void tearDown() throws Exception {
        btnSquare = null;
        gameModel = null;
    }

    @Test
    public void testSelectSquareInPlayTileState() throws AWTException {

        Tile tile = new Tile("a", 2);
        btnTile = new ButtonTile(gameModel, tile, new Dimension(50,50));

        assertFalse(gameModel.getSquare(7,7).containLetter());
        btnTile.doClick();
        btnSquare.doClick();
        assertEquals(tile.getLetter(), gameModel.getContentSquare(7,7));

    }


    @Test
    public void testSelectSquareInSelectStateAction() throws AWTException {

        assertEquals(IDState.SELECT_ACTION.getName(), gameModel.getState());
        btnSquare.doClick();
        assertEquals(IDState.SELECT_ACTION.getName(), gameModel.getState());
    }
}