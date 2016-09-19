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
        Letter letter1 = new Letter('a', 2);
        Letter letter2 = new Letter ('b', 3);

        Square square = new Square();
        square.setLetter(letter2);

        assertNotEquals(letter1, letter2);
        assertNotEquals(letter1.getLetter(), square.getLetterOn());

        game.selectLetter(letter1);
        game.goToNextState();
        game.playTile(square);

        assertEquals(letter1.getLetter(), square.getLetterOn());
    }

    @Test
    public void testAlphabetBagsSize()
    {
        assertEquals(100, game.lettersLeft() + 7 * game.getPlayersLeft());
    }
}