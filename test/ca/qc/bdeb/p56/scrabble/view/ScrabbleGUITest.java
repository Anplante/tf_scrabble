package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Antoine Plante on 9/18/2016.
 */
public class ScrabbleGUITest {

    private ScrabbleGUI scrabbleGame;
    private Game game;
    private GameManager gameManager;
    private Player player;


    PanelLetterRackZone zoneJoueur;


    @Before
    public void setUp()  throws Exception {

        gameManager = new GameManager();

        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        lstPlayer.add(new HumanPlayer("Julien"));

        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("simplistic.png");
        scrabbleGame.createScrabbleGame(game);

    }

    @After
    public void tearDown() throws Exception {

    }


    @Ignore
    @Test
    public void testPlayWord()
    {
        String centerSquarePos = "77";
        String playerFirstTilePos = "0";

        zoneJoueur = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.LETTER_RACK_NAME);
        JPanel board = (JPanel) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.BOARD_NAME);
        BtnSquare square = (BtnSquare) TestUtils.getChildNamed(board, ConstanteTestName.SQUARE_NAME + centerSquarePos);
        ButtonTile tileJoueur = (ButtonTile) TestUtils.getChildNamed(zoneJoueur, ConstanteTestName.TILE_NAME + playerFirstTilePos);
        player = game.getActivePlayer();
        Tile tile = player.getTiles().get(0);
        PanelPlayerInfo infoJoueur = (PanelPlayerInfo) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.INFO_NAME + player.getName());
        JLabel score = (JLabel) TestUtils.getChildNamed(infoJoueur, ConstanteTestName.SCORE_NAME);

        assertFalse((game.getSquare(7,7).containLetter()));
        assertEquals(0, Integer.parseInt(score.getText()));

        tileJoueur.doClick();
        square.doClick();

        assertEquals(tile.getLetter(), game.getContentSquare(7,7));

        game.playWord();

        assertEquals(tile.getValue(), Integer.parseInt(score.getText()));
    }
}
