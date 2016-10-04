package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import javafx.scene.paint.*;

import javax.swing.*;
import javax.swing.border.Border;
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

    private final double RATIO_BOARD_ZONE = 0.1;

    private final double RATIO_PANEL_INFORMATION = 0.1;
    private final double RATIO_LETTER_RACK_ZONE = 0.15;
    private final int BOARD_ZONE_HEIGHT;
    private final int LETTER_RACK_ZONE_HEIGHT;
    private final  int MARGIN = 5;

    private JLabel lblNumberLetter;
    PanelLetterRackZone panelLetterRack;
    Board board;
    JPanel pnlBoard;
    GameManager gameManager;
    Game gameModel;
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

       // MnuOptions options = new MnuOptions(getWidth()/2,getHeight()/2);
        //options.setVisible(true);
    }

    private void initializeComponents() {

        createPanelBoard();
        createPanelLetterRack();
        createPanelInformation();
        createLabelNumberLetters();

    }

    private void createLabelNumberLetters() {
        int y = getHeight() - BOARD_ZONE_HEIGHT;
        y = panelInformation.getHeight();
        int witdh = (int) (getWidth() - getWidth()* RATIO_PANEL_INFORMATION);
        int x = witdh;
        lblNumberLetter =  new JLabel();
        lblNumberLetter.setLocation(1, y);
        lblNumberLetter.setSize(lblNumberLetter.getPreferredSize());
        lblNumberLetter.setText(Integer.toString(gameModel.getlettersLeft()));
        lblNumberLetter.setVisible(true);
        add(lblNumberLetter);
    }

    private void createPanelInformation() {

        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT - BOARD_ZONE_HEIGHT;
        int witdh = ((getWidth() - getHeight()+ LETTER_RACK_ZONE_HEIGHT)/2) - MARGIN  ;
        int x = getWidth() - witdh;
        y *= 0.5;

        panelInformation = new JPanel();

        panelInformation.setLocation(x, MARGIN);
        panelInformation.setSize(witdh, y);
        panelInformation.setLayout(new GridLayout(gameModel.getPlayers().size(), 1, MARGIN, MARGIN));
        panelInformation.setBackground(Color.YELLOW);
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
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int witdhBoard = pnlBoard.getWidth();

        Rectangle boundsZoneLetterRack = new Rectangle(x, y, witdhBoard, LETTER_RACK_ZONE_HEIGHT);
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

        int heightBoard = getHeight() - LETTER_RACK_ZONE_HEIGHT - MARGIN;
        int x = (getWidth() - heightBoard) /2;
        pnlBoard.setLocation(x, MARGIN );
        pnlBoard.setSize(heightBoard, heightBoard);
        add(pnlBoard);
        initGrid();
        pnlBoard.setName("Board");
        pnlBoard.setBackground(Color.RED);
    }

    private void initGrid() {

        pnlBoard.setLayout(new GridLayout(15, 15, 2, 2));

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
