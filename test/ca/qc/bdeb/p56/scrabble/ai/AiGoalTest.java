package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Antoine on 10/26/2016.
 */
public class AiGoalTest {
    private Game game;
    private ArrayList<String> listName;
    private AiGoal aiGoal;

    @Before
    public void setUp()  throws Exception {
        listName = new ArrayList<>();
        GameManager gameManager = new GameManager();
        listName.add("Julien");
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
        game = gameManager.createNewGame(players);
        aiGoal = new AiGoal(game);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void testAllPossibleWord()
    {
        Dictionary dictionary = null;
        try {
            dictionary = new Dictionary();

        }
        catch (FileNotFoundException e) {

        }

        ArrayList<String> test = dictionary.getWordsEndingWith("er");
        int i;
    }
}
