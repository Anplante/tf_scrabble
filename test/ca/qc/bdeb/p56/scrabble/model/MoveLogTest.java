package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.model.Log.ExchangedLog;
import ca.qc.bdeb.p56.scrabble.model.Log.MoveLog;
import ca.qc.bdeb.p56.scrabble.model.Log.PassedLog;
import ca.qc.bdeb.p56.scrabble.model.Log.WordLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by TheFrenchOne on 10/26/2016.
 */
public class MoveLogTest {

    private final int TURN_PLAYED = 1;
    private Player player;

    @Before
    public void setUp() throws Exception {

        player = new HumanPlayer("Louis");
    }

    @After
    public void tearDown() throws Exception {
        player = null;
    }

    @Test
    public void testLogPlayedPlayedWord() {

        String wordPlayed = "test";
        int wordValue = 10;
        MoveLog log = new WordLog(player, 1, wordPlayed, wordValue);
        player.addPoints(wordValue);
        assertEquals(wordPlayed, log.getMove());
        assertEquals(wordValue, log.getMovePoints());
        assertEquals(wordValue, log.getPointsAccumulated());
    }

    @Test
    public void testLogPlayerPassedTurn() {

        MoveLog log = new PassedLog(player, 1);

        assertEquals("Passed", log.getMove());
        assertEquals(0, log.getMovePoints());
    }

    @Test
    public void testLogPlayerEchangedLetters() {

        int numbersOfTilesExchanged = 7;

        MoveLog log = new ExchangedLog(player, 1, numbersOfTilesExchanged);

        assertEquals("Exchanged " + numbersOfTilesExchanged, log.getMove());
        assertEquals(0, log.getMovePoints());
    }

    @Test
    public void testLogAccumulatedPoints() {
        int pointsBeforeMove = 10;
        player.addPoints(pointsBeforeMove);

        String wordPlayed = "test";
        int wordValue = 10;
        MoveLog log = new WordLog(player, 1, wordPlayed, wordValue);
        player.addPoints(wordValue);

        assertEquals(wordValue, log.getMovePoints());
        assertEquals(wordValue + pointsBeforeMove, log.getPointsAccumulated());

    }
}
