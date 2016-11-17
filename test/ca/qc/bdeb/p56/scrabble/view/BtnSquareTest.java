package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Louis Luu Lim on 9/18/2016.
 */
public class BtnSquareTest {

    private static final String TRIPLE_LETTER = "TL";
    private BtnSquare btnSquare;
    private ButtonTile btnTile;
    private Game gameModel;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();
        java.util.List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        gameModel = gameManager.createNewGame(players);
        gameModel.startGame();
    }

    @After
    public void tearDown() throws Exception {
        gameModel = null;
    }

    @Test
    public void testTripleLetterSquare() throws AWTException {

        BtnSquare tripleLetterSquare = new BtnSquare(gameModel.getSquare(1,5), 50, ConstanteComponentMessage.RES_IMAGES_FR_BASIC);
        assertEquals(TRIPLE_LETTER, tripleLetterSquare.getText());
    }
}