package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class BtnTile extends JButton {

    private Game gameModel;

    private Tile tile;


    public BtnTile(Game gameModel, Tile tile, Rectangle bounds) {

        super("" + tile.getLetter());
        this.gameModel = gameModel;
        this.tile = tile;
        setBounds(bounds);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameModel.selectLetter(tile);
                gameModel.goToNextState();

                // TODO Louis: avertir l'observateur du lettre qu'elle a été sélectionné si on veut éventuellement que ca fasse quelque chose
            }
        });
    }
}
