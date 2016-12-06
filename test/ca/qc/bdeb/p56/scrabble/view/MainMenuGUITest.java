package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Created by Antoine Plante on 2016-09-15.
 */
public class MainMenuGUITest {

    private MainMenuGUI frame;
    private JButton btnAccept;
    private JTextField txtFirstPlayer;
    private JTextField txtSecondPlayer;
    private JTextField txtThirdPlayer;
    private JTextField txtFourthPlayer;
    private JButton btnFileChooser;
    private JComboBox cmbNumberOfPlayer;
    private JComboBox cmbBackgroundImage;
    private JComboBox cmbTheme;
    private ScrabbleGUI scrabbleGUI;

    @Before
    public void setUp()  throws Exception {

        scrabbleGUI = new ScrabbleGUI();
        scrabbleGUI.setBackgroundPath("simplistic.png");
        frame = scrabbleGUI.getMenu();
        txtFirstPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 0");
        txtSecondPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 1");
        txtThirdPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 2");
        txtFourthPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 3");
        btnFileChooser = (JButton) TestUtils.getChildNamed(frame, ConstanteTestName.FILE_CHOOSER + "3");
        btnAccept = (JButton) TestUtils.getChildNamed(frame, ConstanteTestName.CONFIRM_NAME);
        cmbNumberOfPlayer = (JComboBox) TestUtils.getChildNamed(frame, ConstanteTestName.QTE_HUMAN_NAME);
        cmbTheme = (JComboBox) TestUtils.getChildNamed(frame, ConstanteTestName.THEME_NAME);
        cmbBackgroundImage = (JComboBox) TestUtils.getChildNamed(frame, ConstanteTestName.BACKGROUND_NAME);
        cmbBackgroundImage.setSelectedIndex(0);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testComboBox() {

        cmbNumberOfPlayer.setSelectedIndex(2);
        btnAccept.doClick();
        assertEquals("4", cmbNumberOfPlayer.getSelectedItem().toString());
        assertEquals(4, frame.getLenghtPlayers());
    }

    @Test
    public void champInputGereEnter() {

        txtFirstPlayer.setText("Testing");
        txtFirstPlayer.postActionEvent();

        assertEquals("Testing", txtFirstPlayer.getText());
    }

    @Test
    public void testBackground() {

        // boardScrabble - attention si on ajoute une image, ce test risque de ne pas fonctionner

        cmbBackgroundImage.setSelectedIndex(0);
        cmbBackgroundImage.setSelectedIndex(1);
        btnAccept.doClick();
        String pathImage = this.getClass().getResource(
                ConstanteComponentMessage.PATH_BACKGROUND_RES + "minuit.jpg").getPath();
        pathImage = pathImage.substring(1);
        pathImage = pathImage.replace('/', '\\');
        assertEquals(pathImage, scrabbleGUI.getBackgroundPath());

    }

    @Test
    public void testVisibiltyOfTextField() {
        cmbNumberOfPlayer.setSelectedIndex(0);
        assertFalse(txtThirdPlayer.isVisible());
        cmbNumberOfPlayer.setSelectedIndex(1);
        assertFalse(txtFourthPlayer.isVisible());
        cmbNumberOfPlayer.setSelectedIndex(2);
        assertTrue(txtFourthPlayer.isVisible());
    }

    @Test
    public void testVisibilityOfButtons() {
        cmbNumberOfPlayer.setSelectedIndex(0);
        assertFalse(btnFileChooser.isVisible());
        cmbNumberOfPlayer.setSelectedIndex(1);
        assertFalse(btnFileChooser.isVisible());
        cmbNumberOfPlayer.setSelectedIndex(2);
        assertTrue(btnFileChooser.isVisible());
    }

    @Test
    public void testTheme() {
        cmbTheme.setSelectedIndex(1);
        btnAccept.doClick();
        assertEquals("./letters/frenchDictionaryValue/noble/", scrabbleGUI.getTheme().getThemeFolderPath());
        cmbTheme.setSelectedIndex(0);
        btnAccept.doClick();
        assertEquals("./letters/frenchDictionaryValue/basic/", scrabbleGUI.getTheme().getThemeFolderPath());
    }
}
