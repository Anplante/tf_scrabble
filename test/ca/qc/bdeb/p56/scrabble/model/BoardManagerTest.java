package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by TheFrenchOne on 11/6/2016.
 */
public class BoardManagerTest {

    private Game game;
    private BoardManager boardManager;


    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        game = gameManager.createNewGame(players);
        boardManager = game.getBoardManager();

    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void testOnlyCenterCanBePlay()
    {
        Square center = game.getSquare(7,7);

        assertEquals(center, boardManager.getSquarePositionAvailableToPlay().get(0));
    }

    @Test
    public void testOnlyCenterNeighboursCanBePlay()
    {
        Square center = game.getSquare(7,7);
        center.setLetter(new Tile("l", 2));

        List<Square> centerNeighbours = center.getNeighbours();
        List<Square> squaresPlayable = boardManager.getSquarePositionAvailableToPlay();

        assertEquals(squaresPlayable, centerNeighbours);
    }

    @Test
    public void testOnlySquareAroundWordCanBePlay()
    {
        Square center = game.getSquare(7,7);
        center.setLetter(new Tile("l", 2));
        Square rightNeighbour = game.getSquare(7,8);
        rightNeighbour.setLetter(new Tile("l", 2));

        List<Square> playableSquaresExpected = center.getNeighbours();

        playableSquaresExpected.remove(rightNeighbour);
        playableSquaresExpected.addAll(rightNeighbour.getNeighbours());
        playableSquaresExpected.remove(center);

        List<Square> squaresPlayable = boardManager.getSquarePositionAvailableToPlay();

        assertEquals(squaresPlayable, playableSquaresExpected);


    }
}
