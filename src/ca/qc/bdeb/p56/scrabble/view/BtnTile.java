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


    public BtnTile(Game gameModel, Tile tile, Dimension dimension) {

        super("" + tile.getLetter());
        this.gameModel = gameModel;
        this.tile = tile;
        setSize(dimension);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    gameModel.selectLetter(tile);

                // TODO Louis: avertir l'observateur du lettre qu'elle a été sélectionné si on veut éventuellement que ca fasse quelque chose
            }
        });
    }

    public void setTile(Tile aTile){
        this.tile = aTile;
        this.setText("" + aTile.getLetter());
    }
}
