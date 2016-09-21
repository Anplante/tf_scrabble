package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Square;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class BtnSquare extends JButton {


    private Game gameModel;
    private int posRow;
    private int posColumn;


    public BtnSquare(Game gameModel, int posRow, int posColumn) {

        super(gameModel.getPremiumSquare(posRow, posColumn));
        this.gameModel = gameModel;
        this.posRow = posRow;
        this.posColumn = posColumn;
        setSize(50, 50);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameModel.playTile(gameModel.getSquare(posRow, posColumn));
                setText("" + String.valueOf(gameModel.getContentSquare(posRow, posColumn)));
                gameModel.goToNextState();
            }
        });
    }
}
