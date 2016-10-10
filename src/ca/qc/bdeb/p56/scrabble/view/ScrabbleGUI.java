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
public class ScrabbleGUI extends JFrame {

    private final double RATIO_BOARD_ZONE = 0.1;

    private final double RATIO_PANEL_INFORMATION = 0.1;
    private final double RATIO_LETTER_RACK_ZONE = 0.1;
    private final int BOARD_ZONE_HEIGHT;
    private final int LETTER_RACK_ZONE_HEIGHT;
    private final int MARGIN = 5;

    private JLabel lblNumberLetter;
    private Image bagImg;
    PanelLetterRackZone panelLetterRack;
    JPanel pnlBoard;
    Game gameModel;
    MnuOptions options;
    private MainMenuGUI menu;
    private PanelBag bagOfLetter;

    JLabel background;

    private JPanel panelInformation;


    public ScrabbleGUI() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(screenSize);
        setBounds(bounds);

        BOARD_ZONE_HEIGHT = (int) (bounds.height * RATIO_BOARD_ZONE) > 100 ? (int) (bounds.height * RATIO_BOARD_ZONE) : 100;
        LETTER_RACK_ZONE_HEIGHT = (int) (bounds.height * RATIO_LETTER_RACK_ZONE) > 100 ? (int) (bounds.height * RATIO_LETTER_RACK_ZONE) : 100;
        setLayout(null);

        try {
            bagImg = ImageIO.read(this.getClass().getResource("/Image/bag_scrabble.png"));
        } catch (IOException ex) {
            // FIXME ANTOINE : ???
        }

        setResizable(false);
        setFocusable(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu = new MainMenuGUI(this);
        menu.setName("Menu");
        addKeyBindings();
    }

    private final AbstractAction actionEscape = new AbstractAction("Escape") {
        @Override
        public void actionPerformed(ActionEvent e) {


            options = new MnuOptions(ScrabbleGUI.this);
            options.setName("Options");
            options.setVisible(true);
        }
    };

    public void createScrabbleGame(Game game) {

        if (gameModel != null) {
            remove(panelInformation);
            remove(panelLetterRack);
            remove(pnlBoard);
        }

        this.gameModel = game;
        createGame();
        initializeComponents();
        addPlayersInfo();
        setVisible(true);
    }

    private void initializeComponents() {

        createBackground();
        createPanelBoard();
        createPanelLetterRack();
        createPanelInformation();
        createLabelNumberLetters();

    }

    private void createBackground() {

        background = new JLabel();
        background.setSize(getWidth(), getHeight());
        // L'ancien background etait beaucoup trop aggresif sur les yeux, un background simple est preferable
        background.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Image/scrabble.jpg")).getImage()
                .getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
        setContentPane(background);

    }

    private void createLabelNumberLetters() {

        bagOfLetter = new PanelBag(gameModel);
        bagOfLetter.setLayout(new BorderLayout());
        int y = panelInformation.getHeight();
        int witdh = (int) (getWidth() - getWidth() * RATIO_PANEL_INFORMATION);
        int x = witdh;
        bagOfLetter.setBounds(x + 50, y + 20, 60, 60);
        add(bagOfLetter);
        gameModel.ajouterObservateur(bagOfLetter);
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

    private void addPlayersInfo() {
        List<Player> players = gameModel.getPlayers();

        for (Player player : players) {
            PanelPlayerInfo playerInfo = new PanelPlayerInfo(player);
            playerInfo.setName("Info : " + player.getName());
            panelInformation.add(playerInfo);
        }
        //panelInformation.repaint();
    }


    private void createPanelLetterRack() {

        int x = pnlBoard.getX();
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int witdhBoard = pnlBoard.getWidth();

        Rectangle boundsZoneLetterRack = new Rectangle(x, y + MARGIN, witdhBoard, LETTER_RACK_ZONE_HEIGHT/2);
        panelLetterRack = new PanelLetterRackZone(boundsZoneLetterRack);

        panelLetterRack.setPlayer(gameModel.getPlayers());
        panelLetterRack.setGame(gameModel);

        panelLetterRack.setName("Player letter rack");
        panelLetterRack.setOpaque(false);
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
    }

    private void initGrid() {

        pnlBoard.setLayout(new GridLayout(15, 15, 2, 2));

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                BtnSquare square = new BtnSquare(gameModel, row, column);
                square.setFocusable(false);
                square.setName("Square " + row + ";" + column);
                pnlBoard.add(square);
            }
        }


    }

    private void createGame() {
        initGame();

        // initilaiser player rack
        // initiate scoreboard
        // etc
    }

    private void initGame() {
        gameModel.startGame();
    }



    private void addKeyBindings()
    {

        JRootPane contentPane = (JRootPane) getRootPane();
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        contentPane.getActionMap().put("Escape", actionEscape);
    }




    public MainMenuGUI getMenu() {
        return menu;
    }

    public void returnToMenu() {
        setVisible(false);
        menu.setVisible(true);
    }
}
