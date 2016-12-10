package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Created by TheFrenchOne on 12/9/2016.
 */
public class DialogBlankTileChoiceTest {


    private DialogBlankTileChoice dialogBlankTileChoice;
    private Tile testTile;

    @Before
    public void setUp() throws Exception {

        ScrabbleGUI scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setTestMode(true);
        testTile = new Tile("", 0);
        dialogBlankTileChoice = new DialogBlankTileChoice(scrabbleGame, testTile, "letters/frenchDictionaryValue/basic");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLetterChoice(){

        JButton letterA = (JButton) TestUtils.getChildNamed(dialogBlankTileChoice, "A");
        letterA.doClick();
        assertEquals(testTile.getLetter(), "A");
    }

    @Test
    public void testCancelLetterChoix(){

        JButton btnCancel = (JButton) TestUtils.getChildNamed(dialogBlankTileChoice, ConstanteTestName.CANCEL_NAME);
        btnCancel.doClick();

        assertTrue(testTile.getLetter().isEmpty());
    }

}