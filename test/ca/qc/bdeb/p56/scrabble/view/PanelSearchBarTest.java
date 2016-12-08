package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.Language;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import ca.qc.bdeb.p56.scrabble.shared.Theme;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Antoine on 11/27/2016.
 */
public class PanelSearchBarTest {

    private final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());
    private ScrabbleGUI scrabbleGame;
    private Game game;
    private PanelSearchBar panelSearchBar;

    private JButton btnSearch;
    private JTextField searchBar;
    private JLabel lblResult;
    private PanelLetterRackZone panelRacks;
    private JButton btnPass;

    @Before
    public void setUp()  throws Exception {
        GameManager gameManager = new GameManager();
        List lstPlayer = new ArrayList<Player>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        scrabbleGame = new ScrabbleGUI();
        scrabbleGame.setBackgroundPath("scrabble.png");
        game = gameManager.createNewGame(lstPlayer, Language.FRENCH);
        scrabbleGame.setGameTheme(Theme.BASIC);
        scrabbleGame.createScrabbleGame(game);


        panelSearchBar = (PanelSearchBar) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.SEARCH_BAR);
        panelRacks = (PanelLetterRackZone) TestUtils.getChildNamed(scrabbleGame, ConstanteTestName.LETTER_RACK_NAME);
        btnSearch = (JButton) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.SEARCH_BUTTON);
        searchBar = (JTextField) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.SEARCH_TXT);
        lblResult = (JLabel) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.LBL_RESULT);
        btnPass = (JButton) TestUtils.getChildNamed(panelRacks, ConstanteTestName.PASS_TURN_NAME);

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
        assertTrue(lblResult.getText().equals(messages.getString("Valid_Word")));

        searchBar.setText("asdfsadfs");
        btnSearch.doClick();
        assertTrue(lblResult.getText().equals(messages.getString("Invalid_Word")));
    }

    @Test
    public void testSearchResetAfterEndOfTurn(){
        searchBar.setText("BoNjoUrs");
        btnSearch.doClick();
        btnPass.doClick();
        assertEquals(messages.getString("Enter_Word"),searchBar.getText());
    }

}
