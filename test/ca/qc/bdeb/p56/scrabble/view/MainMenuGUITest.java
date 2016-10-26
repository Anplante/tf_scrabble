package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
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
 * Created by 1467160 on 2016-09-15.
 */
public class MainMenuGUITest {

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
        // TODO LOUIS : comment accéder au dialog sans passer par le getter
        frame = scrabbleGUI.getMenu();
        txtInput = (JTextField) TestUtils.getChildNamed(frame, "textBox");
        btnAccept = (JButton) TestUtils.getChildNamed(frame, "Confirm");
        cmbNombreAi = (JComboBox) TestUtils.getChildNamed(frame, "choix");
        cmbBackgroundImage = (JComboBox) TestUtils.getChildNamed(frame, "background");

        cmbBackgroundImage.setSelectedIndex(0);
    }

    @After
    public void tearDown() throws Exception {

        scrabbleGUI.dispose();
    }

    @Test
    public void testComboBox() {
        cmbNombreAi.setSelectedIndex(2);
        btnAccept.doClick();
        assertEquals("3", cmbNombreAi.getSelectedItem());
        assertEquals(4, frame.getLenghtPlayers());
    }

    @Test
    public void testCreatePlayer() {
        txtInput.setText("Antoine");
        btnAccept.doClick();
        assertEquals(frame.getPlayer().getName(), txtInput.getText());
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
        cmbBackgroundImage.setSelectedIndex(1);
        btnAccept.doClick();
        assertEquals("boardScrabble.jpeg", scrabbleGUI.getBackgroundPath());

    }

    private void setupTxtInput() {
        txtInput.setName("input");
        txtInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                txtInputActionPerformed(e);
            }
        });
    }


    private void txtInputActionPerformed(ActionEvent evt) {
        txtInput.setText(txtInput.getText() + '?');
    }
}
