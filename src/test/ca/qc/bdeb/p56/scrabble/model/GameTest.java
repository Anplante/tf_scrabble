package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class GameTest {

/**
    private GameManager gameManager;
    private Game game;

    public GameTest()
    {


    }

    @Before
    public void setUp()  throws Exception {

        gameManager = new GameManager();
        game = gameManager.createNewGame();
    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void testSelectTile()
    {
        Letter letter1 = new Letter('a', 2);
        Letter letter2 = new Letter ('b', 3);
        Square square = new Square();
        square.setLetter(letter2);

        assertNotEquals(letter1, letter2);
        assertNotEquals(letter1.getLetter(), square.getLetterOn());

        game.selectLetter(letter1);
        game.playTile(square);

        assertEquals(letter1.getLetter(), square.getLetterOn());
    }

    @Test
    public void testAlphabetBagsSize()
    {
        assertEquals(100, game.lettersLeft() + 7*game.getPlayersLeft());
    }
    **/
}