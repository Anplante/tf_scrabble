package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.view.MainMenuGUI;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Antoine on 9/28/2016.
 */
public class AiplayerTest {

    private Game game;
    private ArrayList<String> listName;

    @Before
    public void setUp()  throws Exception {
        listName = new ArrayList<>();
        GameManager gameManager = new GameManager();
        listName.add("Julien");
        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        game = gameManager.createNewGame(players);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAiName()
    {
        GameManager gameManager = new GameManager();
        List<Player> players = new ArrayList<Player>();
        players.add(new HumanPlayer("Louis"));
        Game oneGame;
        oneGame = gameManager.createNewGame(players);
        AiPlayer aiPlayer = new AiPlayer(listName);
        listName.add("Antoine");
        players.add(new AiPlayer(listName));
        assertEquals("Antoine", players.get(1).getName());
        listName.add("Bot");
        players.add(new AiPlayer(listName));
        assertEquals("Bot", players.get(2).getName());
    }
}
