package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class ScrabbleGUI extends JFrame implements ActionListener{

    public static final int SQUARE_SIZE = 10;


    Board board; //test commit
    int deux;
    int trois;
    JPanel gridPanel;
    JPanel firstClick;
    JPanel lastClick;

    JLabel[][] grid;
    JPanel[][] squares;
    GameManager gameManager;
    JPanel playerLetters;
    Game gameModel;
    BoardManager boardManager;




    public ScrabbleGUI(GameManager gameManager, Dimension screenSize)
    {
        this.gameManager = gameManager;

        setLayout(new BorderLayout());
        setLocation(screenSize.width / 4, screenSize.height / 4);

        JPanel firstClick = null;
        JPanel lastClick = null;
        gridPanel = new JPanel();
        squares = new JPanel[15][15];
        playerLetters = new JPanel();
        grid = new JLabel[15][15];
        createGame();
        initGrid();
        initPlayerLetters();
        add(gridPanel, BorderLayout.EAST);
        add(playerLetters,BorderLayout.PAGE_END);

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

    private void initPlayerLetters(){
        playerLetters.setLayout(new FlowLayout());
        for (int column = 0; column < 7; column++) {
            JPanel panel = new JPanel();
            panel.setSize(50, 50);
            Tile tileDrawn = gameModel.drawCard();
            panel.add(new JLabel("  "+ tileDrawn.getLetterTile() +"  "));
            playerLetters.add(panel);
            playerLetters.setBackground(Color.darkGray);
        }
    }

    private void createGame()
    {
        initGame();
        gameModel.setTileBag(gameManager.createTileList());
        // initilaiser player rack
        // initiate scoreboard
        // etc
    }

    private void initGame()
    {
        gameModel = gameManager.createGame();
        gameModel.startGame();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
