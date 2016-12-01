package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Louis Luu Lim on 11/29/2016.
 */
public class StateEndingTest {


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
    public void testStayToSameState() {

        State expectedState = new StateEnding(player);
        player.setState(expectedState);
        player.nextState();

        assertEquals(expectedState, player.getState());

    }

}