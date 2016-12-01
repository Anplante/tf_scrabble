package ca.qc.bdeb.p56.scrabble.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Louis Luu Lim on 11/29/2016.
 */
public class PlayerTest {


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

    public void orderTilesTest(){
        Tile firstTile = new Tile(" ",0);
        Tile secondTile = new Tile("a",0);
        Tile thirdTile = new Tile("z",0);
        player.addLetter(firstTile);
        player.addLetter(secondTile);
        player.addLetter(thirdTile);

        player.orderTiles();

        List<Tile> tiles = player.getTiles();

        assertEquals(tiles.get(0),firstTile);
        assertEquals(tiles.get(1),secondTile);
        assertEquals(tiles.get(2),thirdTile);

    }

}