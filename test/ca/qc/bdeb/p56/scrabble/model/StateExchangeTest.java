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
    private ArrayList<BtnTile> listTLetters;


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

        panelTested = new PanelLetterRackZone(new Rectangle(1000,1000));
        listTLetters = new ArrayList<>();

        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));
        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));
        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));
        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));
        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));
        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));
        listTLetters.add(new BtnTile(game,new Tile('-',3),new Rectangle(50,50)));

        }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void testExchangeOnlyClick()
    {
        listTLetters.get(5).doClick();
        listTLetters.get(6).doClick();
        listTLetters.get(7).doClick();
        btnEchanger.doClick();

        BtnTile notExcepted = new BtnTile(game,new Tile('-',3),new Rectangle(50,50));

        assertNotEquals(notExcepted,listTLetters.get(5));
        assertNotEquals(notExcepted,listTLetters.get(6));
        assertNotEquals(notExcepted,listTLetters.get(7));
    }

    @Test
    public void testExchangeAddRemoveTiles(){

    }

    @Test
    public void testNoTile(){

    }

    @Test
    public  void testCancel(){

    }


}

