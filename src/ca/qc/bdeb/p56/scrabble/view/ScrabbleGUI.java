package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import javafx.scene.paint.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Paint;
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

        createPanelBoard();
        createPanelLetterRack();
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
        y *= 0.5;

        panelInformation = new JPanel();

        // TODO Antoine : change les positions pour qu'il soit relatif à la grandeur de l'écran
        panelInformation.setLocation(x + 10,4);
        panelInformation.setSize((getWidth() - witdh) - 20, y);
        panelInformation.setLayout(new GridLayout(gameModel.getPlayers().size(), 1));
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

        int x = pnlBoard.getX();
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT*2;
        int witdhBoard = pnlBoard.getWidth();

        Rectangle boundsZoneLetterRack = new Rectangle(x, y, witdhBoard, BOARD_ZONE_HEIGHT);
        panelLetterRack = new PanelLetterRackZone(boundsZoneLetterRack);

        panelLetterRack.setPlayer(gameModel.getPlayers());
        panelLetterRack.setGame(gameModel);

        panelLetterRack.setName("Player letter rack");
        panelLetterRack.setBackground(Color.CYAN);
        panelLetterRack.changementEtat();
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
