package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JPanel pnlBoard;
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


        //setUndecorated(true);  // pour enlever le x
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
        btnFinish.setVisible(true);
        int witdh = (int) (getWidth() - getWidth()* RATIO_PANEL_INFORMATION);
        int x = witdh;
        btnFinish.setSize(100,30);
        btnFinish.setName("finish");
        btnFinish.setLocation(x + 25,785 );
        btnFinish.setText("Finir tour");
        btnFinish.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              gameModel.passTurn();
            }
        });
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
            playerInfo.setName("Info : " + player.getName());
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

        panelLetterRack.setPlayer(gameModel.getPlayers());
        panelLetterRack.setGame(gameModel);
        panelLetterRack.changementEtat();
        panelLetterRack.setName("Player letter rack");
        add(panelLetterRack);
    }

    private void createPanelBoard() {

        pnlBoard = new JPanel();
        pnlBoard.setLocation(100,0);
        int widthBoard = (int) (getWidth() - getWidth() * RATIO_PANEL_INFORMATION) - 100;
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT- BOARD_ZONE_HEIGHT;
        pnlBoard.setSize(widthBoard, y);
        add(pnlBoard);
        initGrid();
        pnlBoard.setName("Board");
    }



    private void initGrid() {

        pnlBoard.setLayout(new GridLayout(15, 15));

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {

                BtnSquare square = new BtnSquare(gameModel, row, column);
                square.setName("Square " + row + ";" + column);
                pnlBoard.add(square);
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
        gameModel.startGame();
    }


}
