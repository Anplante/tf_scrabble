package ca.qc.bdeb.p56.scrabble.vue;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Antoine on 9/12/2016.
 */
public class MenuCreation extends  JFrame {

    JPanel gridPanel;

    public MenuCreation() {

        JFrame fenetre = new JFrame();
        fenetre.pack();
        Insets insets = fenetre.getInsets();
        fenetre = null;
        this.setSize(new Dimension(insets.left + insets.right + 500,
                insets.top + insets.bottom + 500));
        this.setVisible(true);
        setResizable(false);
        setLayout(null);
    }
}
