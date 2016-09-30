package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import ca.qc.bdeb.p56.scrabble.view.BtnTile;
import ca.qc.bdeb.p56.scrabble.view.PanelLetterRackZone;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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


    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private PanelLetterRackZone panelTested;
    private Game game;
    private GameManager gameManager;
    private JButton btnEchanger;
    private JButton btnCancel;
    private List<BtnTile> listTLetters;
    private ScrabbleGUI scrabbleGame;


    public StateExchangeTest()
    {
    }

    @Before
    public void setUp()  throws Exception {
        gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI(game, new Rectangle(screenSize));
        panelTested = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, "Player letter rack");

        btnEchanger = (JButton) TestUtils.getChildNamed(panelTested, "Exchange");
        btnCancel = (JButton) TestUtils.getChildNamed(panelTested, "Cancel_Exchange");

        listTLetters = panelTested.getListBtnTiles();

        for (int i = 0; i < listTLetters.size() ; i++) {
            listTLetters.get(i).setTile(new Tile('-',0));
        }
        }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExchangeOnlyClickOrdered()
    {
        btnEchanger.doClick();
        listTLetters.get(4).doClick();
        listTLetters.get(5).doClick();
        listTLetters.get(6).doClick();
        btnEchanger.doClick();

        Tile notExcepted =new Tile('-',0);
        Tile expected =new Tile('-',0);

        assertEquals(expected.getLetter(),listTLetters.get(0).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(1).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(2).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(3).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(4).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(5).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(6).getTile().getLetter());
    }

    @Test
    public void testExchangeAddRemoveTiles(){
        btnEchanger.doClick();
        listTLetters.get(2).doClick();
        listTLetters.get(3).doClick();
        listTLetters.get(5).doClick();
        listTLetters.get(3).doClick();
        btnEchanger.doClick();

        Tile notExcepted =new Tile('-',0);
        Tile expected =new Tile('-',0);

        assertEquals(expected.getLetter(),listTLetters.get(0).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(1).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(2).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(3).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(4).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(5).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(6).getTile().getLetter());
    }


    @Test
    public void testFromPlayTile(){
        listTLetters.get(0).doClick();
        btnEchanger.doClick();
        listTLetters.get(4).doClick();
        listTLetters.get(5).doClick();
        listTLetters.get(6).doClick();
        btnEchanger.doClick();

        Tile notExcepted =new Tile('-',0);
        Tile expected =new Tile('-',0);

        assertEquals(expected.getLetter(),listTLetters.get(0).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(1).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(2).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(3).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(4).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(5).getTile().getLetter());
        assertNotEquals(notExcepted,listTLetters.get(6).getTile().getLetter());
    }


    @Test
    public void testNoTile(){
        btnEchanger.doClick();
        listTLetters.get(4).doClick();
        listTLetters.get(5).doClick();
        listTLetters.get(6).doClick();
        btnEchanger.doClick();

        Tile expected =new Tile('-',0);

        assertEquals(expected.getLetter(),listTLetters.get(0).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(1).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(2).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(3).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(4).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(5).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(6).getTile().getLetter());

    }

    @Test
    public  void testCancel(){
        btnEchanger.doClick();
        listTLetters.get(4).doClick();
        listTLetters.get(5).doClick();
        listTLetters.get(6).doClick();
        btnCancel.doClick();

        Tile expected =new Tile('-',0);

        assertEquals(expected.getLetter(),listTLetters.get(0).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(1).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(2).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(3).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(4).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(5).getTile().getLetter());
        assertEquals(expected.getLetter(),listTLetters.get(6).getTile().getLetter());

    }


}

