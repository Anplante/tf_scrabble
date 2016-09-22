package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Antoine on 9/18/2016.
 */
public class ScrabbleGUITest {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ScrabbleGUI scrabbleGame;
    private Game game;
    private GameManager gameManager;
    private JButton btnFinish;
    private Player player;


    PanelLetterRackZone zoneJoueur;


    @Before
    public void setUp()  throws Exception {

        gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new Player("Antoine"));
        lstPlayer.add(new Player("Louis"));
        lstPlayer.add(new Player("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI(game, new Rectangle(screenSize));

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testChangePlayer() {
        btnFinish = (JButton) TestUtils.getChildNamed(scrabbleGame, "finish");
        Player firstPlayer = game.getActivePlayer();
        btnFinish.doClick();
        assertNotEquals(firstPlayer, game.getActivePlayer());
        btnFinish.doClick();
        btnFinish.doClick();
        assertEquals(firstPlayer, game.getActivePlayer());
    }

    @Test
    public void testPlayWord()
    {
        zoneJoueur = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, "Player letter rack");
        JPanel board = (JPanel) TestUtils.getChildNamed(scrabbleGame, "Board");
        BtnSquare square = (BtnSquare) TestUtils.getChildNamed(board, "Square 7;7");
        BtnTile  tileJoueur = ( BtnTile ) TestUtils.getChildNamed(zoneJoueur, "Tile0");
        player = game.getActivePlayer();
        Tile tile = player.getTiles().get(0);
        PanelPlayerInfo infoJoueur = (PanelPlayerInfo) TestUtils.getChildNamed(scrabbleGame, "Info : " + player.getName());
        JLabel score = (JLabel) TestUtils.getChildNamed(infoJoueur, "Score");

        assertEquals('\0', game.getContentSquare(7,7));
        assertEquals(0, Integer.parseInt(score.getText()));

        tileJoueur.doClick();
        square.doClick();

        assertEquals(tile.getLetter(), game.getContentSquare(7,7));

        game.playWord();

        assertEquals(tile.getValue(), Integer.parseInt(score.getText()));
    }

}
