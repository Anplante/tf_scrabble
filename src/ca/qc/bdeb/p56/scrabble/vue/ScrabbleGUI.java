package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class ScrabbleGUI extends JFrame {

    private final double RATIO_BOARD_ZONE = 0.12;
    private final double RATIO_PANEL_INFORMATION = 0.20;
    private final double RATIO_LETTER_RACK_ZONE = 0.12;
    private final int BOARD_ZONE_HEIGHT;
    private final int LETTER_RACK_ZONE_HEIGHT;

    PanelLetterRackZone panelLetterRack;

    Board board;
    JPanel gridPanel;

    GameManager gameManager;
    Game gameModel;
    BoardManager boardManager;




    public ScrabbleGUI(GameManager gameManager, Rectangle bounds)
    {
        this.gameManager = gameManager;
        createGame();
        setBounds(bounds);
        BOARD_ZONE_HEIGHT = (int) (bounds.height * RATIO_BOARD_ZONE) > 100 ? (int) (bounds.height*RATIO_BOARD_ZONE) :  100;
        LETTER_RACK_ZONE_HEIGHT = (int) (bounds.height * RATIO_LETTER_RACK_ZONE) > 100 ? (int) (bounds.height*RATIO_LETTER_RACK_ZONE) : 100;

        //setUndecorated(true);
        setResizable(false);
        setLayout(null);

        initializeComponents();


    }

    private void initializeComponents() {
        createPanelLetterRack();
        createPanelBoard();
    }




    private void createPanelLetterRack() {

        int x = 0;
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int witdhBoard = (int) (getWidth() - getWidth() * RATIO_PANEL_INFORMATION);

        panelLetterRack = new PanelLetterRackZone();
        panelLetterRack.setLayout(null);
        panelLetterRack.setLocation(x,y);
        panelLetterRack.setSize(witdhBoard, LETTER_RACK_ZONE_HEIGHT);
        add(panelLetterRack);
    }

    private void createPanelBoard() {

        gridPanel = new JPanel();
        gridPanel.setLocation(100,0);
        int widthBoard = (int) (getWidth() - getWidth() * RATIO_PANEL_INFORMATION) - 100;
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT- BOARD_ZONE_HEIGHT;
        gridPanel.setSize(widthBoard, y);
        add(gridPanel);
        initGrid();

    }



    private void initGrid() {
        gridPanel.setLayout(new GridLayout(15, 15));

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {

                LabelSquare label = new LabelSquare(""+String.valueOf(gameModel.getContentSquare(row, column)));

                gridPanel.add(label);
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
        gameModel = gameManager.createNewGame();
        gameModel.startGame();
    }


}
