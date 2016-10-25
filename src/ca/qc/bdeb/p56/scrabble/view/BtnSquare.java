package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Square;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class BtnSquare extends JButton implements Observateur {


    private Game gameModel;
    private int posRow;
    private int posColumn;
    private Square square;

    private final static Color COLOR_DL = new Color(113, 205, 207);
    private final static Color COLOR_TW = new Color(252, 179, 87);
    private final static Color COLOR_TL = new Color(91, 187, 71);
    private final static Color COLOR_DW = new Color(238, 49, 50);

    public BtnSquare(Game gameModel, int posRow, int posColumn) {
        super(gameModel.getPremiumSquare(posRow, posColumn));
        this.gameModel = gameModel;
        this.posRow = posRow;
        this.posColumn = posColumn;

        setForeground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        setFocusable(false);
        changementEtat();

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (square == null) {
                    square = gameModel.getSquare(posRow, posColumn);
                    square.ajouterObservateur(BtnSquare.this);
                }
                    gameModel.playTile(square);
                if(square.getTileOn() != null &&gameModel.getActivePlayer().getState().getName().equals(IDState.PLAY_TILE.getName())){
                    ImageIcon fillingIcon = new ImageIcon(getClass().getClassLoader().getResource("./letters/englishDictionaryValue/" +square.getLetterOn().toUpperCase()+".png"));
                    Image img = fillingIcon.getImage() ;
                    Image newImage = img.getScaledInstance( getWidth(), getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;
                    ImageIcon icon = new ImageIcon( newImage );
                    setIcon(icon);
                }
            }
        });
    }

    @Override
    public void changementEtat() {

        String content = gameModel.getContentSquare(posRow, posColumn);

        setText(content);

        if (content.isEmpty()) {
            setBackground(Color.LIGHT_GRAY);
        } else if (content.length() == 1) {
            setBackground(Color.black);
            setText(null);
        } else if (content.equals("TW")) {
            setBackground(COLOR_TW);
        } else if (content.equals("DW")) {
            setBackground(COLOR_DW);
        } else if (content.equals("DL")) {
            setBackground(COLOR_DL);
        } else {
            setBackground(COLOR_TL);
        }
        if(square != null && square.getTileOn() == null){
            setIcon(null);
        }
        repaint();
    }

    @Override
    public void changementEtat(Enum<?> property, Object o) {

    }
}
