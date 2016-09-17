package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.Board;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Letter;
import ca.qc.bdeb.p56.scrabble.model.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class LabelSquare extends JLabel {



    Game game;
    Square square;


    public LabelSquare(Square square, Game game)
    {

        super(""+String.valueOf(square.getLetterOn()), SwingConstants.CENTER);
        this.game = game;
        this.square = square;
        setSize(50, 50);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                game.playTile(square);
                setText(""+String.valueOf(square.getLetterOn()));
                game.goToNextState();
            }
        });
    }
}
