package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.*;

import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.awt.*;

/**
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class ScrabbleGUI extends JFrame implements KeyListener, Observateur {

    private final double RATIO_BOARD_ZONE = 0.1;

    private final double RATIO_PANEL_INFORMATION = 0.1;
    private final double RATIO_LETTER_RACK_ZONE = 0.15;
    private final int BOARD_ZONE_HEIGHT;
    private final int LETTER_RACK_ZONE_HEIGHT;
    private final  int MARGIN = 5;

    private JLabel lblNumberLetter;
    private Image bagImg;
    PanelLetterRackZone panelLetterRack;
    Board board;
    JPanel pnlBoard;
    GameManager gameManager;
    Game gameModel;
    BoardManager boardManager;
    JPanel options;
    JLabel background;

    private JPanel panelInformation;


    public ScrabbleGUI(Game game, Rectangle bounds)
    {
        this.gameModel = game;
        setBounds(bounds);
        BOARD_ZONE_HEIGHT = (int) (bounds.height * RATIO_BOARD_ZONE) > 100 ? (int) (bounds.height*RATIO_BOARD_ZONE) :  100;
        LETTER_RACK_ZONE_HEIGHT = (int) (bounds.height * RATIO_LETTER_RACK_ZONE) > 100 ? (int) (bounds.height*RATIO_LETTER_RACK_ZONE) : 100;
        setLayout(null);
        try {
            bagImg = ImageIO.read(this.getClass().getResource("/Image/bag_scrabble.png"));
        } catch (IOException ex) {
        }
        createGame();
        initializeComponents();
        addPlayersInfo();



        //setUndecorated(true);  // pour enlever le x
        setResizable(false);
        setFocusable(true);
        addKeyListener(this);
        gameModel.ajouterObservateur(this);

       // MnuOptions options = new MnuOptions(getWidth()/2,getHeight()/2);
        //options.setVisible(true);
    }

    private void initializeComponents() {

        createPanelBoard();
        createPanelLetterRack();
        createPanelInformation();
        createLabelNumberLetters();
        createOptionsPanel();
        createBackground();
    }

    private void createOptionsPanel() {
        options = new MnuOptions(getWidth() / 2, getHeight() / 2, gameModel);
        options.setVisible(false);
        add(options);
    }

    private void createBackground() {
        background = new JLabel();
        background.setSize(getWidth(), getHeight());
        // L'ancien background etait beaucoup trop aggresif sur les yeux, un background simple est preferable
        background.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Image/scrabble.jpg")).getImage()
                .getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
        add(background);

    }

    private void createLabelNumberLetters() {
        ImageIcon imageBag = null;
        Image image = null;
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        int y = getHeight() - BOARD_ZONE_HEIGHT;
        y = panelInformation.getHeight();
        int witdh = (int) (getWidth() - getWidth()* RATIO_PANEL_INFORMATION);
        int x = witdh;
        panel.setBounds(x + 50,y + 20,60,60);

        lblNumberLetter =  new JLabel(new ImageIcon(bagImg.getScaledInstance(60,60, Image.SCALE_DEFAULT)));
        lblNumberLetter.setSize(lblNumberLetter.getPreferredSize());
        lblNumberLetter.setText(Integer.toString(gameModel.getlettersLeft()));
        lblNumberLetter.setForeground(Color.white);
        lblNumberLetter.setHorizontalTextPosition(JLabel.CENTER);
      //  lblNumberLetter.setVerticalTextPosition(JLabel.BOTTOM);
        lblNumberLetter.setVisible(true);
       // lblNumberLetter.setIcon(imageBag);
        panel.add(lblNumberLetter);
        add(panel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawImage(bagImg,0,0,50,50);
    }

    private void createPanelInformation() {

        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT - BOARD_ZONE_HEIGHT;
        int witdh = ((getWidth() - getHeight() + LETTER_RACK_ZONE_HEIGHT) / 2) - MARGIN;
        int x = getWidth() - witdh;
        y *= 0.5;

        panelInformation = new JPanel();

        panelInformation.setLocation(x, MARGIN);
        panelInformation.setSize(witdh, y);
        panelInformation.setLayout(new GridLayout(gameModel.getPlayers().size(), 1, MARGIN, MARGIN));
        panelInformation.setOpaque(false);
        add(panelInformation);
    }

    private void addPlayersInfo()
    {
        List<Player> players = gameModel.getPlayers();

        for (Player player : players) {
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
        int x = (getWidth() - heightBoard) / 2;
        pnlBoard.setLocation(x, MARGIN);
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!gameModel.getIsInMenu()) {
                pnlBoard.setVisible(false);
                panelLetterRack.setVisible(false);
                panelInformation.setVisible(false);
                options.setVisible(true);
                gameModel.setIsInMenu(true);
            }else {
                pnlBoard.setVisible(true);
                panelLetterRack.setVisible(true);
                panelInformation.setVisible(true);
                options.setVisible(false);
                gameModel.setIsInMenu(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void changementEtat() {
        if (!gameModel.getIsInMenu()) {
            pnlBoard.setVisible(true);
            panelLetterRack.setVisible(true);
            panelInformation.setVisible(true);
            options.setVisible(false);
        }
        if(gameModel.getIsOver()){
            dispose();
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
