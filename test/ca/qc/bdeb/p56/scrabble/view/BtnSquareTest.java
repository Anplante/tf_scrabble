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

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        java.util.List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        gameModel = gameManager.createNewGame(players);

        btnSquare = new BtnSquare(gameModel, 0, 0);
        gameModel.startGame();

    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void testSelectSquareInPlayTileState() throws AWTException {

        Letter letter = new Letter('a', 2);
        btnTile = new BtnTile(gameModel, letter, new Rectangle(0,0,10,10));

        assertEquals('\0', gameModel.getContentSquare(0,0));

        btnTile.doClick();
        btnSquare.doClick();
        assertEquals(letter.getLetter(), gameModel.getContentSquare(0,0));


    }

    @Test
    public void testSelectSquareInSelectState() throws AWTException {


        assertEquals(IDState.SELECT.getName(), gameModel.getState());
        btnSquare.doClick();
        assertEquals(IDState.SELECT.getName(), gameModel.getState());

    }

}