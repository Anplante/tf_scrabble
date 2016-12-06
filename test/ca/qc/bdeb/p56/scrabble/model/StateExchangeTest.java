package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Language;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by 0993083 on 2016-10-13.
 */
public class StateExchangeTest {

    private Game game;
    private Player currentPlayer;

    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        players.add(new HumanPlayer("Antoine"));
        game = gameManager.createNewGame(players, Language.FRENCH);
        game.startGame();
        currentPlayer = game.getActivePlayer();
        game.exchangeLetter();

    }

    @Test
    public void testExchangeLetterChangePlayer()
    {
        game.selectLetter(currentPlayer.getTiles().get(0));
        game.exchangeLetter();
        assertNotEquals(currentPlayer, game.getActivePlayer());
    }

    @Test
    public void testExchangeTile()
    {
        List<Tile> playerTileBeforeExchange = currentPlayer.getTiles();

        for(Tile tile : playerTileBeforeExchange)
        {
            game.selectLetter(tile);
        }
        game.goToNextState();

        for(Tile tile : playerTileBeforeExchange)
        {
            assertFalse(currentPlayer.getTiles().contains(tile));
        }
    }

    @Test
    public void testCancel() {

        Tile tile = currentPlayer.getTiles().get(0);
        game.selectLetter(tile);
        game.cancelExchange();
        assertTrue(currentPlayer.getTiles().contains(tile));
    }
}
