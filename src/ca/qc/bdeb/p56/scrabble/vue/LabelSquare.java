package ca.qc.bdeb.p56.scrabble.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class LabelSquare extends JLabel {


    public LabelSquare(String text)
    {
        super(text, SwingConstants.CENTER);
        setSize(50, 50);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }
        });
    }
}
