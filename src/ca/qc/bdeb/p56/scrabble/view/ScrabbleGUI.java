package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Fenêtre de jeu principal du jeu de Scrabble.
 *
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class ScrabbleGUI extends JFrame implements ActionListener, Observateur {

    private static final Locale locale = new Locale(System.getProperty("user.language"),
            System.getProperty("user.country"));
    private static final ResourceBundle messages = ResourceBundle.getBundle("strings", locale);
    private static final double RATIO_LETTER_RACK_ZONE = 0.1;
    protected static final int MARGIN = 5;

    private final int LETTER_RACK_ZONE_HEIGHT;
    private String backgroundPath;
    private PanelLetterRackZone panelLetterRack;
    private JPanel pnlBoard;
    private Game gameModel;
    private DialogOptionsMenu options;
    private MainMenuGUI menu;
    private Theme theme;
    private DialogWaiting dialogWaiting;
    private JLabel background;
    private JPanel panelInformation;
    private PanelSearchBar panelSearchBar;
    private  JPanel panelInfoWord;

    private JScrollPane scrollMoveLog;

    public ScrabbleGUI() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(screenSize);
        setBounds(bounds);

        LETTER_RACK_ZONE_HEIGHT = (int) (bounds.height * RATIO_LETTER_RACK_ZONE);
        setLayout(null);

        setResizable(false);
        setFocusable(true);
        setUndecorated(true);

        addKeyBindings();


        menu = new MainMenuGUI(this);
        menu.setName(ConstanteTestName.MENU_NAME);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private final AbstractAction actionEscape = new AbstractAction(ConstanteComponentMessage.ESCAPE_KEY) {
        @Override
        public void actionPerformed(ActionEvent e) {

            options = new DialogOptionsMenu(ScrabbleGUI.this);
            options.setName(ConstanteTestName.OPTIONS_NAME);
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
        gameModel.ajouterObservateur(this);
        gameModel.startGame();
        initializeComponents();
        addPlayersInfo();
        setVisible(true);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setGameTheme(Theme theme) {
        this.theme = theme;
    }

    private void initializeComponents() {

        createBackground();
        createPanelBoard();
        createPanelLetterRack();
        createPanelPlayersInformation();
        createPanelMoveLog();
        createSearchBar();
        createPanelInfoWord();

    }

    private void createPanelInfoWord() {
        panelInfoWord = new PanelInfoWord(gameModel);
        int width = ((getWidth() - getHeight() + LETTER_RACK_ZONE_HEIGHT) / 2) - MARGIN*2;
        panelInfoWord.setLocation(MARGIN, scrollMoveLog.getHeight()+25 +panelSearchBar.getHeight());
        panelInfoWord.setBackground(Color.WHITE);
        panelInfoWord.setSize(width, 50 );
        add(panelInfoWord);
    }

    private void createBackground() {

        background = new JLabel();
        background.setName(ConstanteTestName.BACKGROUND);
        background.setSize(getWidth(), getHeight());
        background.setIcon(new ImageIcon(new ImageIcon(backgroundPath)
                .getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
        setContentPane(background);
    }

    private void createPanelPlayersInformation() {

        int heigth = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int width = ((getWidth() - getHeight() + LETTER_RACK_ZONE_HEIGHT) / 2) - MARGIN;
        int x = getWidth() - width;
        width -= MARGIN;
        heigth *= 0.5;

        panelInformation = new JPanel();
        panelInformation.setLocation(x, MARGIN);
        panelInformation.setSize(width, heigth);
        panelInformation.setLayout(new GridLayout(gameModel.getPlayers().size(), 1, MARGIN, MARGIN));
        panelInformation.setOpaque(false);
        add(panelInformation);
    }

    private void addPlayersInfo() {

        List<Player> players = gameModel.getPlayers();

        for (Player player : players) {
            PanelPlayerInfo playerInfo = new PanelPlayerInfo(player);
            playerInfo.setName(ConstanteTestName.INFO_NAME + player.getName());
            panelInformation.add(playerInfo);
            gameModel.getLogManager().ajouterObservateur(playerInfo);
        }
    }


    private void createPanelLetterRack() {

        int x = pnlBoard.getX();
        int y = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int witdhBoard = pnlBoard.getWidth();

        Rectangle boundsZoneLetterRack = new Rectangle(x, y + MARGIN, witdhBoard, LETTER_RACK_ZONE_HEIGHT - MARGIN * 2);
        panelLetterRack = new PanelLetterRackZone(boundsZoneLetterRack, this);

        panelLetterRack.setPlayer(gameModel.getPlayers());
        panelLetterRack.setGameModel(gameModel);
        panelLetterRack.setName(ConstanteTestName.LETTER_RACK_NAME);
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
        pnlBoard.setName(ConstanteTestName.BOARD_NAME);
    }


    private void createPanelMoveLog() {

        int height = getHeight() - LETTER_RACK_ZONE_HEIGHT;
        int width = ((getWidth() - getHeight() + LETTER_RACK_ZONE_HEIGHT) / 2) - MARGIN * 2;
        height *= 0.5;

        TableMoveLog tabMoveLog = new TableMoveLog(gameModel);
        gameModel.getLogManager().ajouterObservateur(tabMoveLog);
        gameModel.getLogManager().ajouterObservateur(this);
        scrollMoveLog = new JScrollPane(tabMoveLog);

        scrollMoveLog.setLocation(MARGIN, MARGIN);

        scrollMoveLog.setSize(width, height);
        add(scrollMoveLog);
    }

    private void createSearchBar() {

        int y = scrollMoveLog.getHeight() + 15;
        int width = ((getWidth() - getHeight() + LETTER_RACK_ZONE_HEIGHT) / 2) - MARGIN * 2;
        panelSearchBar = new PanelSearchBar(gameModel,width);
        panelSearchBar.setLocation(MARGIN, y);
        panelSearchBar.setName(ConstanteTestName.SEARCH_BAR);
        add(panelSearchBar);
    }

    private void initGrid() {

        pnlBoard.setLayout(new GridLayout(BoardManager.BOARD_SIZE, BoardManager.BOARD_SIZE, 2, 2));
        int size = pnlBoard.getWidth() / BoardManager.BOARD_SIZE;


        for (int row = 0; row < BoardManager.BOARD_SIZE; row++) {
            for (int column = 0; column < BoardManager.BOARD_SIZE; column++) {
                ButtonSquare square = new ButtonSquare(gameModel.getSquare(row, column), size, theme);
                square.setName(ConstanteTestName.SQUARE_NAME + row + column);
                square.addActionListener(this);
                pnlBoard.add(square);
            }
        }
    }


    private void addKeyBindings() {

        JRootPane contentPane = getRootPane();
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ConstanteComponentMessage.ESCAPE_KEY);
        contentPane.getActionMap().put(ConstanteComponentMessage.ESCAPE_KEY, actionEscape);
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        ButtonSquare squareClicked = (ButtonSquare) actionEvent.getSource();
        Square square = squareClicked.getSelectedSquare();


        gameModel.playTile(square);

    }

    @Override
    public void changementEtat() {

    }


    @Override
    public void changementEtat(Enum<?> e, Object o) {


        if (e.equals(Event.END_GAME)) {
            List<Player> winner = (List<Player>) o;
            showGameResult(winner);
            panelLetterRack.setVisible(false);

        } else if (e.equals(Event.MOVE_PLAYED)) {
            dialogWaiting = new DialogWaiting(getSize());
            dialogWaiting.setVisible(true);
        }
    }


    private void showGameResult(List<Player> winner){

        String message;
        if (winner.size() == 1) {
            message = winner.get(0).getName() + messages.getString("Winner");
        } else {
            message = messages.getString("Null_Game");

            for (int i = 0; i < winner.size(); i++) {
                message += " " + winner.get(i).getName();

                if (i + 1 == winner.size() - 1) {
                    message += messages.getString("And");
                } else if (i + 1 < winner.size()) {
                    message += ",";
                }
            }

            message += ".";
        }

        JOptionPane.showConfirmDialog(null, message, messages.getString("End_Game"), JOptionPane.PLAIN_MESSAGE);
    }
}
