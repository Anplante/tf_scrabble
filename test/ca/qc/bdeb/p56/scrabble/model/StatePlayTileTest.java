package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Language;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

/**
 * Created by TheFrenchOne on 10/4/2016.
 */
public class StatePlayTileTest {


    private Game game;
    private Player activePlayer;
    private Square centerSquare;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        game = gameManager.createNewGame(players, Language.FRENCH);
        game.startGame();
        activePlayer = game.getActivePlayer();
        centerSquare = game.getBoard().getSquare(7, 7);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRecallTiles() {

        Tile tile = activePlayer.getTiles().get(0);

        assertNull(centerSquare.getTileOn());

        game.selectLetter(tile);
        game.playTile(centerSquare);

        assertEquals(tile.getLetter(), centerSquare.getLetterOn());

        assertFalse(activePlayer.getTiles().contains(tile));

        game.recallTiles();

        assertTrue(activePlayer.getTiles().contains(tile));
        assertNull(centerSquare.getTileOn());
    }

    @Test
    public void testTilesReturnAtSamePositionAfterRecall()
    {
        List<Tile> originalOrder = activePlayer.getTiles();

        game.selectLetter( activePlayer.getTiles().get(0));
        game.playTile(centerSquare);

        assertFalse(activePlayer.getTiles().containsAll(originalOrder));

        game.recallTiles();

        assertTrue(activePlayer.getTiles().containsAll(originalOrder));
    }


    @Test
    public void testPlayFirstWordNotCenter() {

        Tile tileTested = activePlayer.getTiles().get(0);

        game.selectLetter(tileTested);
        game.playTile(game.getSquare(0,0));
        game.recallTiles();
        assertTrue(activePlayer.getTiles().contains(tileTested));
    }

    @Test
    public void testPlayTileAtDifferentAxe()
    {
        Tile firstTilePlayed  = activePlayer.getTiles().get(0);
        Tile secondTilePlayed = activePlayer.getTiles().get(1);

        Square differentAxeSquare = game.getSquare(0,0);

        assertNotEquals(differentAxeSquare, centerSquare);

        game.selectLetter(firstTilePlayed);
        game.playTile(centerSquare);
        assertFalse(activePlayer.getTiles().contains(firstTilePlayed));
        game.selectLetter(secondTilePlayed);
        game.playTile(differentAxeSquare);
        assertTrue(activePlayer.getTiles().contains(secondTilePlayed));
    }

    @Test
    public void testSwapTile(){


        Tile firstTilePlayed  = activePlayer.getTiles().get(0);
        Tile secondTilePlayed = activePlayer.getTiles().get(1);

        Square firstSquarePosition = game.getSquare(7,7);
        Square secondSquarePosition = game.getSquare(7,8);

        game.selectLetter(firstTilePlayed);
        game.playTile(firstSquarePosition);

        game.selectLetter(secondTilePlayed);
        game.playTile(secondSquarePosition);

        assertEquals(firstTilePlayed, firstSquarePosition.getTileOn());
        assertEquals(secondTilePlayed, secondSquarePosition.getTileOn());

        game.playTile(firstSquarePosition);
        game.playTile(secondSquarePosition);

        assertEquals(firstTilePlayed, secondSquarePosition.getTileOn());
        assertEquals(secondTilePlayed, firstSquarePosition.getTileOn() );
    }


    @Test
    public void testMoveTileOnBoard()
    {
        Tile tilePlayed = activePlayer.getTiles().get(0);
        Square firstSquarePosition = game.getSquare(7,7);
        Square secondSquarePosition = game.getSquare(7,8);
        game.selectLetter(tilePlayed);
        game.playTile(firstSquarePosition);

        assertEquals(tilePlayed,firstSquarePosition.getTileOn());
        assertNull(secondSquarePosition.getTileOn());

        game.playTile(firstSquarePosition);
        game.playTile(secondSquarePosition);

        assertEquals(tilePlayed,secondSquarePosition.getTileOn());
        assertNull(firstSquarePosition.getTileOn());
    }


    @Test
    public void testGoToPhaseExchange(){

        Tile tilePlayed = activePlayer.getTiles().get(0);
        Square squarePlayed = game.getSquare(7,7);

        game.selectLetter(tilePlayed);
        game.playTile(squarePlayed);

        game.exchangeLetter();

        assertNull(squarePlayed.getTileOn());

        assertTrue(activePlayer.getTiles().contains(tilePlayed));
    }

}
