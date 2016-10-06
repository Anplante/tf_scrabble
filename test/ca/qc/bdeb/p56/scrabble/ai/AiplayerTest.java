package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Player;
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

    @Before
    public void setUp()  throws Exception {

        GameManager gameManager = new GameManager();

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Louis"));
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
        players.add(new Player("Louis"));
        Game oneGame;
        oneGame = gameManager.createNewGame(players);
        AiPlayer aiPlayer = new AiPlayer();
        aiPlayer.tmpList.clear();
        aiPlayer.tmpList.add("Antoine");
        players.add(new AiPlayer());
        assertEquals("Antoine", players.get(1).getName());
        aiPlayer.tmpList.add("Bot");
        players.add(new AiPlayer());
        assertEquals("Bot", players.get(2).getName());
        aiPlayer.tmpList = ListOfName.AIName;
        aiPlayer.tmpList.add("Antoine");
        aiPlayer.tmpList.add("Louis");
        aiPlayer.tmpList.add("Wow");
        aiPlayer.tmpList.add("AntoinePlante");
    }
}
