package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Letter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class LabelTile extends JLabel {

    private Game gameModel;

    private Letter letter;


    public LabelTile(Game gameModel, Letter letter, Rectangle bounds)
    {
        super(""+ letter.getLetter(), SwingConstants.CENTER);
        this.gameModel = gameModel;
        this.letter = letter;

        //setSize(50, 50);
        setBounds(bounds);
        //label.setBackground(Color.YELLOW);
        setOpaque(true);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                gameModel.selectLetter(letter);
                gameModel.goToNextState();
                setText("");  // solution temporaire pour le sprint de mardi, utiliser obersaveteur.
            }
        });
    }
}
