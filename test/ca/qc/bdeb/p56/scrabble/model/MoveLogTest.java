package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDMove;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        MoveLog log = new MoveLog(player, wordPlayed, wordValue);

        assertEquals(IDMove.PLAYED_WORD, log.getMove());
    }

    @Test
    public void testLogPlayerPassedTurn() {

        MoveLog log = new MoveLog(player, IDMove.PASS);

        assertEquals(IDMove.PASS, log.getMove());
        assertEquals(0, log.getWordPoints());
        assertTrue(log.getWordPlayed().isEmpty());
    }

    @Test
    public void testLogPlayerEchangedLetters(){

        MoveLog log = new MoveLog(player, IDMove.EXCHANGED);

        assertEquals(IDMove.EXCHANGED, log.getMove());
    }

}
