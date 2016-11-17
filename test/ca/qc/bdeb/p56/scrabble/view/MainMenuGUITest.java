package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Antoine Plante on 2016-09-15.
 */
public class MainMenuGUITest {

    private MainMenuGUI frame;
    private JButton btnAccept;
    private JTextField txtFirstPlayer;
    private JTextField txtFourthPlayer;
    private Player player;
    private JComboBox cmbNumberOfPlayer;
    private JComboBox cmbBackgroundImage;
    private ScrabbleGUI scrabbleGUI;

    @Before
    public void setUp()  throws Exception {

        scrabbleGUI = new ScrabbleGUI();
        scrabbleGUI.setBackgroundPath("simplistic.png");
        frame = scrabbleGUI.getMenu();
        txtFirstPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 0");
        txtFourthPlayer = (JTextField) TestUtils.getChildNamed(frame, ConstanteTestName.PLAYER_NAME + " 3");
        btnAccept = (JButton) TestUtils.getChildNamed(frame, ConstanteTestName.CONFIRM_NAME);
        cmbNumberOfPlayer = (JComboBox) TestUtils.getChildNamed(frame, ConstanteTestName.QTE_HUMAN_NAME);
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

        // boardScrabble - attention si onajoute une image, ce test risque de ne pas fonctionner

        cmbBackgroundImage.setSelectedIndex(0);
        cmbBackgroundImage.setSelectedIndex(1);
        btnAccept.doClick();
        assertEquals("minuit.jpg", scrabbleGUI.getBackgroundPath());

    }
}
