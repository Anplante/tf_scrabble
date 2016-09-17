package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.vue.PanelEditGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by 1467160 on 2016-09-15.
 */
public class PanelEditGameTest {

    private PanelEditGame frame;
    private JTextField txtInput;

    @Before
    public void setUp()  throws Exception {
        frame = new PanelEditGame();
        txtInput = (JTextField) TestUtils.getChildNamed(frame, "textBox");
    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void champInputGereEnter() {
        txtInput.setText("Testing");
        txtInput.postActionEvent();

        assertEquals("Testing", txtInput.getText());
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
