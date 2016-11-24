package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Julien Brosseau on 11/21/2016.
 */
public class WaitingPanelTest {
    private ScrabbleGUI scrabbleGame;
    private Game game;
    private GameManager gameManager;
    private Player player;
    private WaitingPanel testedPanel;


    @Before
    public void setUp()  throws Exception {

        gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        lstPlayer.add(new HumanPlayer("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setImgPath(ConstanteComponentMessage.RES_IMAGES_FR_BASIC);
        scrabbleGame.setBackgroundPath("simplistic.png");
        scrabbleGame.createScrabbleGame(game);
        testedPanel = (WaitingPanel) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.WAITING_PANEL_NAME);

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testPassTurn()
    {
        JButton btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.PASS_TURN_NAME);


        btnFinish.doClick();

        assertTrue(testedPanel.isVisible());
    }

    @Ignore
    @Test
    public  void testPlayWord(){

        JButton btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.PLAY_NAME);


        Square tripleWordSquare = game.getSquare(0, 0);
        tripleWordSquare.setLetter(new Tile("l", 2));
        Square normalSquare = game.getSquare(1, 0);
        normalSquare.setLetter(new Tile("a", 2));

        List<Square> test = new ArrayList<>();
        test.add(tripleWordSquare);
        test.add(normalSquare);
        game.playWord(test);

        btnFinish.doClick();

        assertTrue(testedPanel.isVisible());
    }
}

