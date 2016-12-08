package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.shared.Language;
import ca.qc.bdeb.p56.scrabble.shared.Theme;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
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

    private static final String TRIPLE_LETTER = "TL";
    private Game gameModel;
    private String theme;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();
        java.util.List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        gameModel = gameManager.createNewGame(players, Language.FRENCH);
        gameModel.startGame();
        theme = ConstanteComponentMessage.RES_ROOT_FRENCH + ConstanteComponentMessage.RES_IMAGES_BASIC;
    }

    @After
    public void tearDown() throws Exception {
        gameModel = null;
    }

    @Test
    public void testTripleLetterSquare() throws AWTException {

        ButtonSquare tripleLetterSquare = new ButtonSquare(gameModel.getSquare(1,5), 50, theme);
        assertEquals(TRIPLE_LETTER, tripleLetterSquare.getText());
    }
}