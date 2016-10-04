package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class GameTest {

    private Game game;

    public GameTest() {
    }

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPlayTile() {

        game.startGame();
        Player activePlayer = game.getActivePlayer();
        Tile tile = activePlayer.getTiles().get(0);

        Square square = game.getBoard().getSquare(7, 7);
        assertNull(square.getTileOn());

        game.selectLetter(tile);
        game.playTile(square);

        assertEquals(tile.getLetter(), square.getLetterOn());
    }

    @Test
    public void testRecallTiles() {
        game.startGame();

        Player activePlayer = game.getActivePlayer();
        Tile tile = activePlayer.getTiles().get(0);

        Square square = game.getBoard().getSquare(7, 7);
        assertNull(square.getTileOn());

        game.selectLetter(tile);
        game.playTile(square);

        assertEquals(tile.getLetter(), square.getLetterOn());

        assertFalse(activePlayer.getTiles().contains(tile));

        game.recallTiles();

        assertTrue(activePlayer.getTiles().contains(tile));
        assertNull(square.getTileOn());
    }

    @Test
    public void testAlphabetBagsSize() {
        int sizeAlphabet = game.getlettersLeft();
        game.startGame();
        assertEquals(sizeAlphabet, game.getlettersLeft() + 7 * game.getPlayersLeft());
    }

    @Test
    public void testCalculateWordPoints() {

        game.startGame();
        Player activePlayer = game.getActivePlayer();
        Square square1 = game.getSquare(7,7);
        Square square2 = game.getSquare(7,8);
        Tile tile1 = activePlayer.getTiles().get(0);
        Tile tile2 =  activePlayer.getTiles().get(1);

        game.selectLetter(tile1);
        game.playTile(square1);
        game.selectLetter(tile2);
        game.playTile(square2);
        game.playWord();

        assertEquals(tile1.getValue() + tile2.getValue(), activePlayer.getScore());
    }

    @Test
    public void testPlayFirstWordNotCenter() {

        game.startGame();

        Player activePlayer = game.getActivePlayer();

        Tile tileTested = activePlayer.getTiles().get(0);

        game.selectLetter(tileTested);
        game.playTile(game.getSquare(0,0));
        game.recallTiles();
        assertTrue(activePlayer.getTiles().contains(tileTested));
    }
}