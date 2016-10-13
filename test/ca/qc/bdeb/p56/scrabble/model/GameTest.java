package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class GameTest {

    private Game game;


    @Before
    public void setUp() throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPlayTile() {

        game.startGame();
        Player activePlayer = game.getActivePlayer();
        Tile tile = activePlayer.getTiles().get(0);

        Square square = game.getBoard().getSquare(7, 7);
        assertNull(square.getTileOn());

        game.selectLetter(tile);
        game.playTile(square);

        assertEquals(tile.getLetter(), square.getLetterOn());
    }



    @Test
    public void testAlphabetBagsSize() {
        int sizeAlphabet = game.getlettersLeft();
        game.startGame();
        assertEquals(sizeAlphabet, game.getlettersLeft() + 7 * game.getPlayersLeft());
    }

    @Test
    public void testCalculateWordPoints() {

        game.startGame();
        Player activePlayer = game.getActivePlayer();
        Square square1 = game.getSquare(7,7);
        Square square2 = game.getSquare(7,8);
        Tile tile1 = activePlayer.getTiles().get(0);
        Tile tile2 =  activePlayer.getTiles().get(1);

        game.selectLetter(tile1);
        game.playTile(square1);
        game.selectLetter(tile2);
        game.playTile(square2);
        game.playWord();
        assertEquals(tile1.getValue() + tile2.getValue(), activePlayer.getScore());
    }


    @Test
    public void testValidWordFr(){

        String wordTested = "bonjour";
        assertTrue(game.isValidWord(wordTested));
    }

    @Test
    public void testInvalidWord(){

        String wordTested = "ssdfaf";
        assertFalse(game.isValidWord(wordTested));
    }

    @Ignore
    @Test
    public void testValidWordEn(){
        String wordtested = "hello";
        assertFalse(game.isValidWord(wordtested));
    }

    @Test
    public void testPlayWord()
    {

        Square square1 = game.getSquare(0,0);
        square1.setLetter(new Tile("l",2));
        Square square2 =game.getSquare(1,0);
        square2.setLetter(new Tile("a",2));

        List<Square> test = new ArrayList<>();
        test.add(square1);
        test.add(square2);

        assertTrue(game.playWord(test));
    }

    @Test
    public void testPlayAfterWord()
    {

        Square square1 = game.getSquare(0,0);
        square1.setLetter(new Tile("l",2));
        Square square2 =game.getSquare(1,0);
        square2.setLetter(new Tile("e",2));

        List<Square> test = new ArrayList<>();
        test.add(square1);
        test.add(square2);
        assertTrue(game.playWord(test));


        Square square3d = game.getSquare(2,0);
        square3d.setLetter(new Tile("s",2));
        test.clear();
        test.add(square3d);
        assertTrue(game.playWord(test));

    }
}