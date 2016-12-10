package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Language;
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

        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Louis"));
        players.add(new HumanPlayer("Julien"));
        game = gameManager.createNewGame(players, Language.FRENCH);
        game.startGame();
        player = game.getActivePlayer();
    }

    @After
    public void tearDown() throws Exception {
        game = null;
        player = null;
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
        int row = 7;
        int column = 7;

        while(!player.getTiles().isEmpty())
        {
            game.selectLetter(player.getTiles().get(0));
            game.playTile(game.getSquare(row,column));
            row++;
        }
        assertEquals(0,  player.getTiles().size());
        game.selectPlayWordAction();
        game.passTurn();

        assertEquals(7, player.getTiles().size());
    }

    @Test
    public void testDrawWhenBagIsEmpty()
    {
        game.emptyBag();
        player.emptyHand();

        assertEquals(0, player.getTiles().size());

        game.drawTile();

        assertEquals(0, player.getTiles().size());

    }
}