package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Antoine Plante on 2016-09-15.
 */
public class MainMenuGUITest implements ConstanteTestName {

    private MainMenuGUI frame;
    private JButton btnAccept;
    private JTextField txtInput;
    private Player player;
    private JComboBox cmbNombreAi;
    private JComboBox cmbBackgroundImage;
    private ScrabbleGUI scrabbleGUI;

    @Before
    public void setUp()  throws Exception {

        scrabbleGUI = new ScrabbleGUI();
        scrabbleGUI.setBackgroundPath("simplistic.png");
        frame = scrabbleGUI.getMenu();
        txtInput = (JTextField) TestUtils.getChildNamed(frame, PLAYER_NAME);
        btnAccept = (JButton) TestUtils.getChildNamed(frame, CONFIRM_NAME);
        cmbNombreAi = (JComboBox) TestUtils.getChildNamed(frame, QTE_AI_NAME);
        cmbBackgroundImage = (JComboBox) TestUtils.getChildNamed(frame, BACKGROUND_NAME);

        cmbBackgroundImage.setSelectedIndex(0);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testComboBox() {

        cmbNombreAi.setSelectedIndex(2);
        btnAccept.doClick();
        assertEquals("3", cmbNombreAi.getSelectedItem());
        assertEquals(4, frame.getLenghtPlayers());
    }

    @Test
    public void champInputGereEnter() {

        txtInput.setText("Testing");
        txtInput.postActionEvent();

        assertEquals("Testing", txtInput.getText());
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
