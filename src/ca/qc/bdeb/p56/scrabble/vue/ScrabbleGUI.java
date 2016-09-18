package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.*;

import javax.swing.*;
import java.util.List;
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
    private JButton btnFinish;
    BoardManager boardManager;

    private JPanel panelInformation;


    public ScrabbleGUI(Game game, Rectangle bounds)
    {
        this.gameModel = game;
        setBounds(bounds);
        BOARD_ZONE_HEIGHT = (int) (bounds.height * RATIO_BOARD_ZONE) > 100 ? (int) (bounds.height*RATIO_BOARD_ZONE) :  100;
        LETTER_RACK_ZONE_HEIGHT = (int) (bounds.height * RATIO_LETTER_RACK_ZONE) > 100 ? (int) (bounds.height*RATIO_LETTER_RACK_ZONE) : 100;
        setLayout(null);
        createGame();
        initializeComponents();
        addPlayersInfo();


        //setUndecorated(true);
        setResizable(false);



    }

    private void initializeComponents() {
        createPanelLetterRack();
        createPanelBoard();
        createPanelInformation();
        createButton();

    }

    private void createButton() {
        btnFinish = new JButton();
        int witdh = (int) (getWidth() - getWidth()* RATIO_PANEL_INFORMATION);
        int x = witdh;
        btnFinish.setSize(100,30);
        btnFinish.setName("finish");
        btnFinish.setLocation(x + 25,785 );
        btnFinish.setText("Finir tour");
        btnFinish.setVisible(true);
        add(btnFinish);
    }

    private void createPanelInformation() {

        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT - BOARD_ZONE_HEIGHT;
        int witdh = (int) (getWidth() - getWidth()* RATIO_PANEL_INFORMATION);
        int x = witdh;

        panelInformation = new JPanel();
        panelInformation.setLocation(x,0);
        panelInformation.setSize(getWidth() - witdh, y);
        add(panelInformation);
    }

    private void addPlayersInfo()
    {

        List<Player> players = gameModel.getPlayers();

        for(Player player : players)
        {
            PanelPlayerInfo playerInfo = new PanelPlayerInfo(player);
            panelInformation.add(playerInfo);
        }
        panelInformation.revalidate();

    }


    private void createPanelLetterRack() {

        int x = 0;
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT*2;
        int witdhBoard = (int) (getWidth() - getWidth() * RATIO_PANEL_INFORMATION);
        Rectangle boundsZoneLetterRack = new Rectangle(x, y, witdhBoard, BOARD_ZONE_HEIGHT);
        panelLetterRack = new PanelLetterRackZone(boundsZoneLetterRack);

        panelLetterRack.setPlayer(gameModel.getActivePlayerIndex());
        panelLetterRack.setGame(gameModel);
        panelLetterRack.changementEtat();
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

                LabelSquare label = new LabelSquare(gameModel.getSquare(row, column), gameModel);

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
       // gameModel = gameManager.createNewGame();
        gameModel.startGame();
    }


}
