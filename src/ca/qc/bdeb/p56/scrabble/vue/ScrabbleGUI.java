package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.Board;
import ca.qc.bdeb.p56.scrabble.model.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class ScrabbleGUI extends JFrame {

    public static final int SQUARE_SIZE = 10;


    Board board; //test commit
    int deux;
    int trois;
    JPanel gridPanel;

    JLabel[][] grid;
    JPanel[][] squares;

    {

    }


    public ScrabbleGUI()
    {

        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 4, screenSize.height / 4);

        gridPanel = new JPanel();
        squares = new JPanel[Board.BOARD_SIZE][Board.BOARD_SIZE];
        grid = new JLabel[Board.BOARD_SIZE][Board.BOARD_SIZE];

        initGrid();
        add(gridPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

    }



    private void initGrid() {
        gridPanel.setLayout(new GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE));
        board = new Board();            ////////// ailleurs stp
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                Tile tile = board.getTile(row, column);
                JPanel panel = new JPanel();
                panel.setSize(50, 50);
                panel.setBorder(BorderFactory.createEtchedBorder());
                JLabel label = new JLabel(getContent(tile));
                panel.add(label);
                squares[row][column] = panel;
                grid[row][column] = label;
                gridPanel.add(panel);
            }
        }
    }

    private String getContent(Tile tile) {
        char content = tile.getContent();
        if (Character.isLetter(content)) {
            return ""+content;
        } else {
            switch(content) {
                case Tile.THREE_WORD_BONUS :
                    return "3W";
                case Tile.TWO_WORD_BONUS :
                    return "2W";
                case Tile.THREE_LETTER_BONUS :
                    return "3L";
                case Tile.TWO_LETTER_BONUS :
                    return "2L";
            }
        }
        return "";
    }

}
