package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheFrenchOne on 10/26/2016.
 */
public class SquareTest {

    private Game game;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void testRightNeighbours() {

        int posCenter = 7;

        Square centerSquare = game.getSquare(posCenter, posCenter);
        Square upperNeighbour = game.getSquare(posCenter + 1, posCenter);
        Square lowerNeighbour = game.getSquare(posCenter - 1, posCenter);
        Square rightNeighbour = game.getSquare(posCenter, posCenter + 1);
        Square leftNeighbour = game.getSquare(posCenter, posCenter -1 );

        assertTrue(centerSquare.isNeighbours(upperNeighbour));
        assertTrue(centerSquare.isNeighbours(lowerNeighbour));
        assertTrue(centerSquare.isNeighbours(rightNeighbour));
        assertTrue(centerSquare.isNeighbours(leftNeighbour));
    }

    @Test
    public void testNoNeighbours()
    {
       int posLeftCoinSquare = 0;
       int posRightCoinSquare = 14;

       Square leftCoinSquare = game.getSquare(posLeftCoinSquare,posLeftCoinSquare);
        Square rightCoinSquare = game.getSquare(posRightCoinSquare, posRightCoinSquare);

        assertNull(leftCoinSquare.getAdjacentUp());
        assertNull(leftCoinSquare.getAdjacentLeft());

        assertNull(rightCoinSquare.getAdjacentRight());
        assertNull(rightCoinSquare.getAdjacentDown());
    }
}
