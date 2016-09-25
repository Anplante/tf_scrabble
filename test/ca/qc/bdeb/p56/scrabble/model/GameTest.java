package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class GameTest {

    private Game game;

    public GameTest()
    {
    }

    @Before
    public void setUp()  throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSelectTile()
    {
        game.startGame();

        Tile tile1 = new Tile('a', 2);
        Tile tile2 = new Tile('b', 3);

        Square square = new Square();
        square.setLetter(tile2);

        assertNotEquals(tile1.getLetter(), square.getLetterOn());

        game.selectLetter(tile1);
        game.playTile(square);

        assertEquals(tile1.getLetter(), square.getLetterOn());
    }

    @Test
    public void testAlphabetBagsSize()
    {
        int sizeAlphabet = game.getlettersLeft();
        game.startGame();
        assertEquals(sizeAlphabet, game.getlettersLeft() + 7 * game.getPlayersLeft());
    }

    @Test
    public void testCalculateWordPoints()
    {
        game.startGame();

        Square square1 = new Square();
        Square square2 = new Square();
        Tile tile1 = new Tile('a', 2);
        Tile tile2 = new Tile('b', 3);

        game.selectLetter(tile1);
        game.playTile(square1);
        game.selectLetter(tile2);
        game.playTile(square2);
        Player currentPlayer = game.getActivePlayer();
        game.playWord();
        assertEquals(tile1.getValue() + tile2.getValue(),currentPlayer.getScore() );

    }
}