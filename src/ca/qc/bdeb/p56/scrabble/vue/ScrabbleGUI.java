package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.*;

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
    GameManager gameManager;
    Game gameModel;
    BoardManager boardManager;




    public ScrabbleGUI(GameManager gameManager, Dimension screenSize)
    {
        this.gameManager = gameManager;

        setLayout(new BorderLayout());
        setLocation(screenSize.width / 4, screenSize.height / 4);

        gridPanel = new JPanel();
        squares = new JPanel[15][15];
        grid = new JLabel[15][15];
        createGame();
        initGrid();
        add(gridPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

    }



    private void initGrid() {
        gridPanel.setLayout(new GridLayout(15, 15));

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                JPanel panel = new JPanel();
                panel.setSize(50, 50);
                panel.setBorder(BorderFactory.createEtchedBorder());
                JLabel label = new JLabel(""+String.valueOf(gameModel.getContentSquare(row, column)));
                panel.add(label);
                squares[row][column] = panel;
                grid[row][column] = label;
                gridPanel.add(panel);
            }
        }
    }

    private void createGame()
    {
        initGame();
        // initilaiser player rack
        // initiate scoreboard
        // etc

    }

    private void initGame()
    {
        gameModel = gameManager.createGame();
        gameModel.startGame();
    }


}
