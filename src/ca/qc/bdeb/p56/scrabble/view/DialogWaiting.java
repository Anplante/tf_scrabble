package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by TheFrenchOne on 11/27/2016.
 */
public class DialogWaiting extends JDialog implements KeyListener {

    public DialogWaiting(Dimension dimension) {

        setSize(dimension);

        ImageIcon fillingIcon = new ImageIcon(getClass().getClassLoader().getResource(ConstanteComponentMessage.RES_WAITING_IMAGE));
        add(new JLabel(fillingIcon));
        setModal(true);
        setUndecorated(true);
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
