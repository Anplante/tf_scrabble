package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by TheFrenchOne on 9/18/2016.
 */
public class BtnSquareTest {


    private BtnSquare btnSquare;
    private BtnTile btnTile;
    private Game gameModel;
    private PanelLetterRackZone zoneJoueur;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        java.util.List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        gameModel = gameManager.createNewGame(players);

        btnSquare = new BtnSquare(gameModel, 7, 7);
        gameModel.startGame();

    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void testSelectSquareInPlayTileState() throws AWTException {

        Tile tile = new Tile('a', 2);
        btnTile = new BtnTile(gameModel, tile, new Dimension(50,50));

        assertEquals('\0', gameModel.getContentSquare(7,7));

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