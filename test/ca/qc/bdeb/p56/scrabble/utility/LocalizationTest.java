package ca.qc.bdeb.p56.scrabble.utility;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.Language;
import ca.qc.bdeb.p56.scrabble.view.MainMenuGUI;
import ca.qc.bdeb.p56.scrabble.view.PanelSearchBar;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
import ca.qc.bdeb.p56.scrabble.view.Theme;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 *  Classe test s'occupant de confirmer certains traductions, note : quand on set le language au début, ce language
 *
 * Created by TheFrenchOne on 10/24/2016. Edited by Antoine
 */
public class LocalizationTest {

    private Game game;
    private MainMenuGUI frame;
    private ScrabbleGUI scrabbleGUI;
    private JButton btnAccept;
    private JTextField txtFirstPlayer;
    private JLabel lblSecondPlayer;
    private JLabel lblTheme;
    private JLabel lblResult;
    private JButton btnSearch;
    private JTextField searchBar;

    private PanelSearchBar panelSearchBar;

    @Before
    public void setUp() throws Exception {

        setLocale("fr");
        refreshScrabble();
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }


    @Test
    public void testMainMenuInFrench() {

        assertEquals("Confirmer", btnAccept.getText());
        assertEquals("Thème :", lblTheme.getText());
        assertEquals("Nom du joueur 2 :", lblSecondPlayer.getText());
        assertEquals("Veuillez entrer un nom...", txtFirstPlayer.getText());
    }

    @Test
    public void testPanelSearchBarInFrench() {
        searchBar.setText("BoNjoUrs");
        btnSearch.doClick();
        assertEquals("Ce mot est valide!!", lblResult.getText());
        searchBar.setText("asdgasd");
        btnSearch.doClick();
        assertEquals("Ce mot est invalide.", lblResult.getText());
    }

    @Test
    public void testMainMenuInEnglish() {
        setLocale("en");
        refreshScrabble();
        assertEquals("Confirm", btnAccept.getText());
        assertEquals("Theme :", lblTheme.getText());
        assertEquals("Name of player 2 :", lblSecondPlayer.getText());
        assertEquals("Enter a name...", txtFirstPlayer.getText());
    }



    private void setLocale(String language) {
        Locale.setDefault(new Locale(language));
    }

    private void refreshScrabble() {
        GameManager gameManager = new GameManager();
        List<Player> lstPlayer = new ArrayList<>();
        lstPlayer.add(new HumanPlayer("Antoine"));
        lstPlayer.add(new HumanPlayer("Louis"));
        scrabbleGUI = new ScrabbleGUI();
        scrabbleGUI.setTestMode(true);
        scrabbleGUI.setBackgroundPath("sunburst.jpg");
        frame = scrabbleGUI.getMenu();
        game = gameManager.createNewGame(lstPlayer, Language.FRENCH);
        scrabbleGUI.setGameTheme(Theme.BASIC);
        scrabbleGUI.createScrabbleGame(game);

        txtFirstPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 0");
        btnAccept = (JButton) TestUtils.getChildNamed(frame, ConstanteTestName.CONFIRM_NAME);
        lblTheme = (JLabel) TestUtils.getChildNamed(frame, ConstanteTestName.LBL_THEME);
        lblSecondPlayer = (JLabel) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + "1");
        panelSearchBar = (PanelSearchBar) TestUtils.getChildNamed(scrabbleGUI, ConstanteTestName.SEARCH_BAR);
        btnSearch = (JButton) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.SEARCH_BUTTON);
        searchBar = (JTextField) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.SEARCH_TXT);
        lblResult = (JLabel) TestUtils.getChildNamed(panelSearchBar, ConstanteTestName.LBL_RESULT);
    }

}
