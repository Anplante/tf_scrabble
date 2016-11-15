package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.ai.AiGoal;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
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
        players.add(new HumanPlayer("Louis"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
        game = null;
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
    public void testCalculateWordPointsWithoutPremium() {

        game.startGame();
        Player activePlayer = game.getActivePlayer();

        Tile tile1 = new Tile("l", 2);
        Tile tile2 = new Tile("a", 2);

        Square noPremiumSquare1 = game.getSquare(1, 0);
        noPremiumSquare1.setLetter(tile1);
        Square noPremimumSquare2 = game.getSquare(2, 0);
        noPremimumSquare2.setLetter(tile2);

        List<Square> lettersPlayed = new ArrayList<>();

        lettersPlayed.add(noPremiumSquare1);
        lettersPlayed.add(noPremimumSquare2);

        game.playWord(lettersPlayed);

        assertEquals(tile1.getValue() + tile2.getValue(), activePlayer.getScore());
    }

    @Test
    public void testCalculateWordPointsWithTripleWordPremium() {
        game.startGame();
        Player activePlayer = game.getActivePlayer();

        Tile tile1 = new Tile("l", 2);
        Tile tile2 = new Tile("a", 2);

        Square tripleWordSquare = game.getSquare(0, 0);
        tripleWordSquare.setLetter(tile1);
        Square noPremiumSquare = game.getSquare(1, 0);
        noPremiumSquare.setLetter(tile2);

        List<Square> lettersPlayed = new ArrayList<>();

        lettersPlayed.add(tripleWordSquare);
        lettersPlayed.add(noPremiumSquare);

        game.playWord(lettersPlayed);

        assertEquals((tile1.getValue() + tile2.getValue()) * 3, activePlayer.getScore());
    }

    @Test
    public void testCalculateWordPointsWithDifferentPremium() {
        game.startGame();

        List<Tile> tilesPlayed = new ArrayList<>();
        List<Square> lettersPlayed = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            tilesPlayed.add(new Tile("l", 2));
            Square square = game.getSquare(i, 0);
            square.setLetter(tilesPlayed.get(i));
            lettersPlayed.add(square);
        }

        int values = game.calculateWordPoints(lettersPlayed);

        assertEquals(30, values);
    }

    @Test
    public void testCalculateWordPointsWithTwoLetterPremium() {
        game.startGame();

        List<Tile> tilesPlayed = new ArrayList<>();
        List<Square> lettersPlayed = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            tilesPlayed.add(new Tile("l", 2));
            Square square = game.getSquare(8, i + 6);
            square.setLetter(tilesPlayed.get(i));
            lettersPlayed.add(square);
        }

        int values = game.calculateWordPoints(lettersPlayed);

        assertEquals(12, values);
    }

    @Test
    public void testUsePremiumTwice() {
        game.startGame();

        Tile tileTested = new Tile("l", 2);

        Square tripleWordPremiumSquare = game.getSquare(0, 0);
        tripleWordPremiumSquare.setLetter(tileTested);

        List<Square> lettersPlayed = new ArrayList<>();

        lettersPlayed.add(tripleWordPremiumSquare);

        int values = game.calculateWordPoints(lettersPlayed);

        assertEquals(6, values);

        values = game.calculateWordPoints(lettersPlayed);

        assertEquals(2, values);

    }

    @Test
    public void testDoublePointsWordAtCenter() {

        game.startGame();

        Tile tileTested1 = new Tile("l", 2);
        Tile tileTested2 = new Tile("l", 3);
        Square centerSquare = game.getSquare(7, 7);
        Square adjacentToCenterSquare = game.getSquare(7, 8);
        centerSquare.setLetter(tileTested1);
        adjacentToCenterSquare.setLetter(tileTested2);

        List<Square> lettersPlayed = new ArrayList<>();

        lettersPlayed.add(centerSquare);
        lettersPlayed.add(adjacentToCenterSquare);

        int values = game.calculateWordPoints(lettersPlayed);

        assertEquals(10, values);
    }


    @Test
    public void testValidWordFr() {

        String validWord = "bonjour";
        assertTrue(game.isValidWord(validWord));
    }

    @Test
    public void testInvalidWord() {

        String notAWord = "ssdfaf";
        assertFalse(game.isValidWord(notAWord));
    }


    @Test
    public void testValidWordEn() {
        String wordtested = "wrong";
        assertFalse(game.isValidWord(wordtested));
    }

    @Test
    public void testPlayWord() {

        Square tripleWordSquare = game.getSquare(0, 0);
        tripleWordSquare.setLetter(new Tile("l", 2));
        Square normalSquare = game.getSquare(1, 0);
        normalSquare.setLetter(new Tile("a", 2));

        List<Square> test = new ArrayList<>();
        test.add(tripleWordSquare);
        test.add(normalSquare);

        assertTrue(game.playWord(test));
    }

    @Test
    public void testPlayAfterWordVertical() {

        Square square1 = game.getSquare(0, 0);
        square1.setLetter(new Tile("l", 2));
        Square square2 = game.getSquare(1, 0);
        square2.setLetter(new Tile("e", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        assertTrue(game.playWord(lettersPlayed));


        Square square3d = game.getSquare(2, 0);
        square3d.setLetter(new Tile("s", 2));
        lettersPlayed.clear();
        lettersPlayed.add(square3d);
        assertTrue(game.playWord(lettersPlayed));
    }

    @Test
    public void testWordCrossingOtherWord() {

        Square square1 = game.getSquare(7, 7);
        square1.setLetter(new Tile("l", 2));
        Square square2 = game.getSquare(7, 8);
        square2.setLetter(new Tile("e", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        assertTrue(game.playWord(lettersPlayed));


        Square square3 = game.getSquare(6, 8);
        square3.setLetter(new Tile("s", 2));
        Square square4 = game.getSquare(8, 8);
        square4.setLetter(new Tile("s", 2));
        lettersPlayed.clear();
        lettersPlayed.add(square3);
        lettersPlayed.add(square4);
        assertTrue(game.playWord(lettersPlayed));
    }

    @Test
    public void testWordCreatedFromOtherWord() {

        Square square1 = game.getSquare(7, 7);
        square1.setLetter(new Tile("l", 2));
        Square square2 = game.getSquare(7, 8);
        square2.setLetter(new Tile("e", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        assertTrue(game.playWord(lettersPlayed));


        Square square3 = game.getSquare(7, 9);
        square3.setLetter(new Tile("s", 2));
        Square square5 = game.getSquare(8, 9);
        square5.setLetter(new Tile("e", 2));
        Square square4 = game.getSquare(9, 9);
        square4.setLetter(new Tile("s", 2));
        lettersPlayed.clear();
        lettersPlayed.add(square3);
        lettersPlayed.add(square4);
        lettersPlayed.add(square5);
        assertTrue(game.playWord(lettersPlayed));
    }

    @Test
    public void testPlayAfterWordHorizontal() {

        Square square1 = game.getSquare(0, 0);
        square1.setLetter(new Tile("l", 2));
        Square square2 = game.getSquare(0, 1);
        square2.setLetter(new Tile("e", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        assertTrue(game.playWord(lettersPlayed));


        Square square3d = game.getSquare(0, 2);
        square3d.setLetter(new Tile("s", 2));
        lettersPlayed.clear();
        lettersPlayed.add(square3d);
        assertTrue(game.playWord(lettersPlayed));
    }

    @Test
    public void testWordsCreatedAddinngOneLetterToTwoWords() {

        Square square1 = game.getSquare(0, 0);
        square1.setLetter(new Tile("l", 2));
        Square square2 = game.getSquare(1, 0);
        square2.setLetter(new Tile("e", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        assertTrue(game.playWord(lettersPlayed));


        Square square3 = game.getSquare(2, 1);
        square3.setLetter(new Tile("e", 2));
        Square square4 = game.getSquare(2, 2);
        square4.setLetter(new Tile("s", 2));

        lettersPlayed.clear();
        lettersPlayed.add(square3);
        lettersPlayed.add(square4);
        assertTrue(game.playWord(lettersPlayed));
        lettersPlayed.clear();
        Square square5 = game.getSquare(2, 0);
        square5.setLetter(new Tile("s", 2));
        lettersPlayed.add(square5);

        assertTrue(game.playWord(lettersPlayed));
    }

    @Test
    public void testWordWithMultipleWordCreated() {

        //"LES" Center word (7;7)
        Square square1 = game.getSquare(7, 7);
        square1.setLetter(new Tile("s", 2));
        Square square2 = game.getSquare(7, 8);
        square2.setLetter(new Tile("e", 2));
        Square square3 = game.getSquare(7, 9);
        square3.setLetter(new Tile("s", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        lettersPlayed.add(square3);
        assertTrue(game.playWord(lettersPlayed));

        //"LUNES" from pos(3;9) to (7;9)
        Square square4 = game.getSquare(6, 4);
        square4.setLetter(new Tile("l", 2));
        Square square5 = game.getSquare(6, 5);
        square5.setLetter(new Tile("u", 2));
        Square square6 = game.getSquare(6, 6);
        square6.setLetter(new Tile("n", 2));
        Square square7 = game.getSquare(6, 7);
        square7.setLetter(new Tile("e", 2));
        Square square8 = game.getSquare(6, 8);
        square8.setLetter(new Tile("s", 2));

        lettersPlayed.clear();
        lettersPlayed.add(square4);
        lettersPlayed.add(square5);
        lettersPlayed.add(square6);
        lettersPlayed.add(square7);
        lettersPlayed.add(square8);
        assertTrue(game.playWord(lettersPlayed));
        System.out.println(game.getActivePlayer().getScore());
    }

    @Test
    public void testCalculateNewWordCreatedFromExistingWord() {
        Square square1 = game.getSquare(7, 7);
        square1.setLetter(new Tile("s", 2));
        Square square2 = game.getSquare(7, 8);
        square2.setLetter(new Tile("e", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square1);
        lettersPlayed.add(square2);
        game.playWord(lettersPlayed);

        Square square3 = game.getSquare(7, 9);
        square3.setLetter(new Tile("s", 2));
        lettersPlayed.clear();
        lettersPlayed.add(square3);
        assertTrue(game.playWord(lettersPlayed));
    }

    @Test
    public void testWordsClose() {
        Square square1 = game.getSquare(7, 7);
        square1.setLetter(new Tile("s", 2));
        Square square2 = game.getSquare(7, 8);
        square2.setLetter(new Tile("e", 2));
        Square square3 = game.getSquare(7, 9);
        square3.setLetter(new Tile("s", 2));
        Square square4 = game.getSquare(6, 7);
        square4.setLetter(new Tile("t", 2));
        Square square5 = game.getSquare(6, 8);
        square5.setLetter(new Tile("s", 2));
        Square square6 = game.getSquare(6, 9);
        square6.setLetter(new Tile("t", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square4);
        lettersPlayed.add(square5);
        lettersPlayed.add(square6);
        assertFalse(game.playWord(lettersPlayed));
    }

    @Test
    public void testWordWithMultipleWordCreatedNotValid() {

        //"LE" Center word (7;7)
        Square square1 = game.getSquare(7, 7);
        square1.setLetter(new Tile("l", 2));
        Square square2 = game.getSquare(7, 8);
        square2.setLetter(new Tile("s", 2));

        //"SE" On the letter E pos(7;8)
        Square square3 = game.getSquare(6, 8);
        square1.setLetter(new Tile("", 2));

        //"LUNES" from pos(3;9) to (7;9)
        Square square4 = game.getSquare(3, 9);
        square4.setLetter(new Tile("l", 2));
        Square square5 = game.getSquare(4, 9);
        square5.setLetter(new Tile("u", 2));
        Square square6 = game.getSquare(5, 9);
        square6.setLetter(new Tile("n", 2));
        Square square7 = game.getSquare(6, 9);
        square7.setLetter(new Tile("e", 2));
        Square square8 = game.getSquare(7, 9);
        square8.setLetter(new Tile("s", 2));

        List<Square> lettersPlayed = new ArrayList<>();
        lettersPlayed.add(square4);
        lettersPlayed.add(square5);
        lettersPlayed.add(square6);
        lettersPlayed.add(square7);
        lettersPlayed.add(square8);
        assertFalse(game.playWord(lettersPlayed));
    }


    @Test
    public void testNotEndOfTheGame() {

        game.startGame();

        assertFalse(game.checkForEndOfTheGame());
    }

    @Test
    public void testEndOfTheGameWhenPlayingOut() {

        game.startGame();
        game.getActivePlayer().emptyHand();
        game.emptyBag();
        assertTrue(game.checkForEndOfTheGame());
    }

    @Test
    public void testEndOfTheGameWhenSixConseixConsecutiveScorelessTurn() {

        game.startGame();

        for(int i = 0; i < 6; i++)
        {
            game.passTurn();
        }
        assertTrue(game.checkForEndOfTheGame());
    }
}