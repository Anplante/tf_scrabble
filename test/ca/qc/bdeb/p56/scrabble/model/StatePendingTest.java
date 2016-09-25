package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by TheFrenchOne on 9/24/2016.
 */
public class StatePendingTest {

    private Game game;
    private Player player;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        game = gameManager.createNewGame(players);
        game.startGame();
        player = game.getActivePlayer();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testDrawWhenHandIsFull()
    {
        int bagSizeBefore = game.getlettersLeft();

        List<Tile> playerLettersBefore = player.getTiles();

        game.drawTile();

        assertEquals(bagSizeBefore, game.getlettersLeft());

        assertEquals(playerLettersBefore,  player.getTiles());
    }

    @Test
    public void testDrawToFullHand()
    {
        int bagSizeBefore = game.getlettersLeft();
        List<Tile> playerLetters = player.getTiles();

        playerLetters.clear();

        assertEquals(0, playerLetters.size());

        game.drawTile();

        assertEquals(7,  player.getTiles().size());
    }

    @Test
    public void testDrawWhenBagIsEmpty()
    {
        List<Tile> playerLetters = player.getTiles();
        while(game.getlettersLeft() > 0)
        {
            playerLetters.clear();
            playerLetters = player.getTiles();
            game.drawTile();
        }
        int lettersCount = player.getTiles().size();
        game.drawTile();

        assertEquals( lettersCount, player.getTiles().size());

    }
}