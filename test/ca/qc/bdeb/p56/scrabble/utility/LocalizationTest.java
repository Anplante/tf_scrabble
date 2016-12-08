package ca.qc.bdeb.p56.scrabble.utility;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.HumanPlayer;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.view.MainMenuGUI;
import ca.qc.bdeb.p56.scrabble.view.ScrabbleGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private ResourceBundle messages;

    @Before
    public void setUp() throws Exception {

        System.setProperty("user.language", "fr");
        messages = ResourceBundle.getBundle("strings", Locale.getDefault());

        scrabbleGUI = new ScrabbleGUI();
        scrabbleGUI.setBackgroundPath("sunburst.jpg");
        frame = scrabbleGUI.getMenu();

        txtFirstPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 0");
        btnAccept = (JButton) TestUtils.getChildNamed(frame, ConstanteTestName.CONFIRM_NAME);
        lblTheme = (JLabel) TestUtils.getChildNamed(frame, ConstanteTestName.LBL_THEME);
        lblSecondPlayer = (JLabel) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + "1");
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
    public void testMainMenuInEnglish() {
        Locale.setDefault(new Locale("en"));
        System.setProperty("user.language", "eng");
        scrabbleGUI = new ScrabbleGUI();
        scrabbleGUI.setBackgroundPath("sunburst.jpg");
        MainMenuGUI frame = scrabbleGUI.getMenu();

        txtFirstPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 0");
        btnAccept = (JButton) TestUtils.getChildNamed(frame, ConstanteTestName.CONFIRM_NAME);
        lblTheme = (JLabel) TestUtils.getChildNamed(frame, ConstanteTestName.LBL_THEME);
        lblSecondPlayer = (JLabel) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + "1");
        assertEquals("Confirm", btnAccept.getText());
//        assertEquals("Thème :", lblTheme.getText());
//        assertEquals("Nom du joueur 2 :", lblSecondPlayer.getText());
//        assertEquals("Veuillez entrer un nom...", txtFirstPlayer.getText());
    }

}
