package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Square;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

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
public class BtnSquare extends JButton implements Observateur {


    private Game gameModel;
    private int posRow;
    private int posColumn;
    private Square square;


    public BtnSquare(Game gameModel, int posRow, int posColumn) {

        super(gameModel.getPremiumSquare(posRow, posColumn));
        this.gameModel = gameModel;
        this.posRow = posRow;
        this.posColumn = posColumn;

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(square == null)
               {
                   square = gameModel.getSquare(posRow, posColumn);
                   square.ajouterObservateur(BtnSquare.this);
               }
                gameModel.playTile(square);
            }
        });
    }

    @Override
    public void changementEtat() {
        setText("" + String.valueOf(gameModel.getContentSquare(posRow, posColumn)));
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }
}
