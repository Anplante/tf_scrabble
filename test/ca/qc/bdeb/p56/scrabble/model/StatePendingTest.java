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
        while(!player.getTiles().isEmpty())
        {
            game.selectLetter(player.getTiles().get(0));
            game.playTile(game.getSquare(7,7));
        }
        assertEquals(0,  player.getTiles().size());
        game.playWord();
        game.passTurn();

        assertEquals(7, player.getTiles().size());
    }

    @Test
    public void testDrawWhenBagIsEmpty()
    {
        List<Tile> playerLetters = player.getTiles();
        while(game.getlettersLeft() > 0)
        {
            while(!player.getTiles().isEmpty())
            {
                game.selectLetter(player.getTiles().get(0));
                game.playTile(game.getSquare(7,7));
            }
            assertEquals(0,  player.getTiles().size());
            game.playWord();
            game.passTurn();
        }
        int lettersCount = player.getTiles().size();
        game.drawTile();

        assertEquals( lettersCount, player.getTiles().size());

    }
}