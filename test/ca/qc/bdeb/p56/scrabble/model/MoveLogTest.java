package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.model.Log.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by TheFrenchOne on 10/26/2016.
 */
public class MoveLogTest {

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

        int turnPlayed = 1;
        MoveLog log = new PassedLog(player, turnPlayed);

        assertEquals("Passed", log.getMove());
        assertEquals(0, log.getMovePoints());
        assertEquals(turnPlayed, log.getTurnPlayed());
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

    @Test
    public void testLogForfeited(){

        MoveLog log = new ForfeitedLog(player, 1);

        assertEquals("Forfeited", log.getMove());
        assertEquals(0, log.getMovePoints());
    }
}
