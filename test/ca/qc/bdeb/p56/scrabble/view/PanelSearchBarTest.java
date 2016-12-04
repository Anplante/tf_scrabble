package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Antoine on 11/27/2016.
 */
public class PanelSearchBarTest {

    private ScrabbleGUI scrabbleGame;
    private Game game;
    private PanelSearchBar panelSearchBar;
    private JButton btnSearch;
    private JTextField searchBar;
    private JLabel lblResult;

    @Before
    public void setUp()  throws Exception {
        GameManager gameManager = new GameManager();
        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("scrabble.png");
        game = gameManager.createNewGame(lstPlayer);
        scrabbleGame.setGameTheme(Theme.BASIC);
        scrabbleGame.createScrabbleGame(game);

        panelSearchBar = (PanelSearchBar) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.SEARCH_BAR);
        btnSearch = (JButton) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.SEARCH_BUTTON);
        searchBar = (JTextField) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.SEARCH_TXT);
        lblResult = (JLabel) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.LBL_RESULT);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGhostTextNotSearched() {
        btnSearch.doClick();
        assertEquals("", lblResult.getText());
    }

    @Test
    public void testSearchIsCorrect() {
        searchBar.setText("BoNjoUrs");
        btnSearch.doClick();
        assertTrue(lblResult.getText().equals(ConstanteComponentMessage.VALID_WORD));

        searchBar.setText("asdfsadfs");
        btnSearch.doClick();
        assertTrue(lblResult.getText().equals(ConstanteComponentMessage.INVALID_WORD));
    }

}
