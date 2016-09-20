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
        game.startGame();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSelectTile()
    {
        Tile tile1 = new Tile('a', 2);
        Tile tile2 = new Tile('b', 3);

        Square square = new Square();
        square.setLetter(tile2);

        assertNotEquals(tile1, tile2);
        assertNotEquals(tile1.getLetter(), square.getLetterOn());

        game.selectLetter(tile1);
        game.goToNextState();
        game.playTile(square);

        assertEquals(tile1.getLetter(), square.getLetterOn());
    }

    @Test
    public void testAlphabetBagsSize()
    {
        assertEquals(102, game.lettersLeft() + 7 * game.getPlayersLeft());
    }
}