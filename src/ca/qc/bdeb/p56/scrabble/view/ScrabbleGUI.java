package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;

import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.*;

/**
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class ScrabbleGUI extends JFrame implements ConstanteComponentMessage, ConstanteTestName {

    private final  double RATIO_LETTER_RACK_ZONE = 0.1;
    public final static int MARGIN = 5;
    private final int LETTER_RACK_ZONE_HEIGHT;

    private String backgroundPath;
    PanelLetterRackZone panelLetterRack;
    JPanel pnlBoard;
    Game gameModel;
    DialogOptionsMenu options;
    private MainMenuGUI menu;

    JLabel background;

    private JPanel panelInformation;


    public ScrabbleGUI() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(screenSize);
        setBounds(bounds);

        LETTER_RACK_ZONE_HEIGHT = (int) (bounds.height * RATIO_LETTER_RACK_ZONE);
        setLayout(null);


        setResizable(false);
        setFocusable(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu = new MainMenuGUI(this);
        menu.setName(MENU_NAME);
        addKeyBindings();

    }

    private final AbstractAction actionEscape = new AbstractAction(ESCAPE_KEY) {
        @Override
        public void actionPerformed(ActionEvent e) {

            options = new DialogOptionsMenu(ScrabbleGUI.this);
            options.setName(OPTIONS_NAME);
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
    }

    private void createBackground() {

        background = new JLabel();
        background.setSize(getWidth(), getHeight());
        background.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource(PATH_BACKGROUND_RES +
                backgroundPath)).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
        setContentPane(background);
    }

    private void createPanelInformation() {

        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int width = ((getWidth() - getHeight() + LETTER_RACK_ZONE_HEIGHT) / 2) - MARGIN;
        int x = getWidth() - width;
        y *= 0.5;

        panelInformation = new JPanel();
        panelInformation.setLocation(x, MARGIN);
        panelInformation.setSize(width, y);
        panelInformation.setLayout(new GridLayout(gameModel.getPlayers().size(), 1, MARGIN, MARGIN));
        panelInformation.setOpaque(false);
        add(panelInformation);
    }

    private void addPlayersInfo() {
        List<Player> players = gameModel.getPlayers();

        for (Player player : players) {
            PanelPlayerInfo playerInfo = new PanelPlayerInfo(player);
            playerInfo.setName(INFO_NAME + player.getName());
            panelInformation.add(playerInfo);
        }
    }


    private void createPanelLetterRack() {

        int x = pnlBoard.getX();
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int witdhBoard = pnlBoard.getWidth();

        Rectangle boundsZoneLetterRack = new Rectangle(x, y + MARGIN, witdhBoard, LETTER_RACK_ZONE_HEIGHT - MARGIN * 2);
        panelLetterRack = new PanelLetterRackZone(boundsZoneLetterRack);

        panelLetterRack.setPlayer(gameModel.getPlayers());
        panelLetterRack.setGame(gameModel);
        panelLetterRack.setName(LETTER_RACK_NAME);
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
        pnlBoard.setName(BOARD_NAME);
    }

    private void initGrid() {

        pnlBoard.setLayout(new GridLayout(15, 15, 2, 2));

        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                BtnSquare square = new BtnSquare(gameModel, row, column);
                square.setFocusable(false);
                square.setName(SQUARE_NAME + row + column);
                pnlBoard.add(square);
            }
        }
    }
    private void createGame() {

        gameModel.startGame();
    }

    private void addKeyBindings() {

        JRootPane contentPane = getRootPane();
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        contentPane.getActionMap().put(ESCAPE_KEY, actionEscape);
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String path) {
        this.backgroundPath = path;
    }

    public void changeBackground(String filePath) {
        this.backgroundPath = filePath;
    }


    public MainMenuGUI getMenu() {
        return menu;
    }

    public void returnToMenu() {
        setVisible(false);
        menu.setVisible(true);
    }
}
