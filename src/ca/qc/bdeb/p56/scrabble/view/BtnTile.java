package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Letter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class BtnTile extends JButton {

    private Game gameModel;

    private Letter letter;


    public BtnTile(Game gameModel, Letter letter, Rectangle bounds) {

        super("" + letter.getLetter());
        this.gameModel = gameModel;
        this.letter = letter;
        setBounds(bounds);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                gameModel.selectLetter(letter);
                gameModel.goToNextState();

                // TODO Louis: avertir l'observateur du lettre qu'elle a été sélectionné si on veut éventuellement que ca fasse quelque chose
            }
        });
    }
}
