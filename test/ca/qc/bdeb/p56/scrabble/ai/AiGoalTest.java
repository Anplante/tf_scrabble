package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        players.add(new HumanPlayer("Louis"));
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
        List<String> results = new ArrayList<>(Arrays.asList("banna","banane", "banne", "nana", "ben", "bea", "ban", "ane", "ana", "ne", "na", "en", "an"));

        String lettersOfAi= "nnbaae";
        List<String>  wordsFound = aiGoal.getPossibleWord(lettersOfAi);

        for(String word : results)
        {
            assertTrue(wordsFound.contains(word));
        }
    }
}
