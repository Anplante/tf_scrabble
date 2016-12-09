package ca.qc.bdeb.p56.scrabble.view;


import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

import static com.sun.org.apache.xml.internal.serializer.utils.Utils.messages;

/**
 * Created by Louis Luu Lim on 9/11/2016.
 */
public class PanelLetterRackZone extends JPanel implements Observateur, ActionListener {


    private ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());
    private final int MAX_TILES_IN_HAND = 7;
    private final double RATIO_TILES_RACK = .5;
    private final int POS_Y = 0;
    private final int LETTERS_RACK_WIDTH;
    private final int OPTIONS_WIDTH;
    private final Dimension TILE_DIMENSION;

    private Player currentPlayer;
    private Game gameModel;
    private ButtonExchange btnExchange;
    private JButton btnPassTurn;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JButton btnForfeit;
    private JButton btnCancelExchange;
    private JButton btnOrderTiles;
    private JPanel panelLettersRack;
    private ScrabbleGUI parent;
    private HashMap<String, ImageIcon> iconsTile;
    private String imageThemePath;
    private boolean testMode = false;

    protected PanelLetterRackZone(Rectangle boundsZoneLetterRack, ScrabbleGUI parent, Game game, String imageThemePath) {

        super();
        this.imageThemePath = imageThemePath;
        this.parent = parent;
        this.gameModel = game;
        setLayout(null);
        setBounds(boundsZoneLetterRack);
        LETTERS_RACK_WIDTH = (int) (getWidth() * RATIO_TILES_RACK);
        OPTIONS_WIDTH = ((getWidth() - LETTERS_RACK_WIDTH) / 4) - ScrabbleGUI.MARGIN;
        int width = LETTERS_RACK_WIDTH / MAX_TILES_IN_HAND;
        TILE_DIMENSION = new Dimension(width, width);
        initIconsTile();

    }

    public void setPlayer(List<Player> players) {

        if (currentPlayer != null) {
            for (Player player : players) {
                player.retirerObservateur(this);
            }
        }

        for (Player player : players) {
            player.ajouterObservateur(this);

        }
    }

    protected void setGameModel(Game aGame) {
        if (gameModel != null) {
            gameModel.retirerObservateur(this);
            gameModel.retirerObservateur(btnExchange);
        }
        gameModel = aGame;


        initPanelLettersRack();
        initializeOptions();
        gameModel.ajouterObservateur(this);
        gameModel.ajouterObservateur(btnExchange);
    }

    private void initPanelLettersRack() {

        int x = getWidth() / 2 - LETTERS_RACK_WIDTH / 2;
        panelLettersRack = new JPanel();
        panelLettersRack.setLayout(null);
        panelLettersRack.setLocation(x, POS_Y);
        panelLettersRack.setSize(LETTERS_RACK_WIDTH, getHeight());
        panelLettersRack.setName(ConstanteTestName.LETTER_RACK_NAME);
        panelLettersRack.setOpaque(false);
        add(panelLettersRack);

    }

    private void initializeOptions() {

        initForfeitOption();
        initExchangeOption();
        initRecallOption();
        initiBtnPlayWord();
        initPassTurnOption();
        initOrderTilesOption();
    }

    private void setImageBtn(JButton btn, String path) {        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));

        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(btn.getWidth(), btn.getHeight(), java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        btn.setIcon(icon);
    }

    private void initPassTurnOption() {

        int x = getWidth() - OPTIONS_WIDTH;
        btnPassTurn = new JButton();
        btnPassTurn.setName(ConstanteTestName.PASS_TURN_NAME);
        btnPassTurn.setSize(OPTIONS_WIDTH, getHeight());
        btnPassTurn.setLocation(x, POS_Y);
        btnPassTurn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPassTurn.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPassTurn.setVisible(false);
        btnPassTurn.setOpaque(false);
        btnPassTurn.setFocusPainted(false);
        btnPassTurn.setBorderPainted(false);
        btnPassTurn.setContentAreaFilled(false);
        btnPassTurn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setImageBtn(btnPassTurn, ConstanteComponentMessage.RES_NEXT);

        add(btnPassTurn);

        btnPassTurn.addActionListener(e -> gameModel.passTurn());
    }

    private void initiBtnPlayWord() {

        int x = getWidth() - OPTIONS_WIDTH;

        btnPlayWord = new JButton();
        btnPlayWord.setName(ConstanteTestName.PLAY_NAME);
        btnPlayWord.setSize(OPTIONS_WIDTH, getHeight());
        btnPlayWord.setLocation(x, POS_Y);
        add(btnPlayWord);
        btnPlayWord.setVisible(false);
        btnPlayWord.setOpaque(false);
        btnPlayWord.setFocusPainted(false);
        btnPlayWord.setBorderPainted(false);
        btnPlayWord.setContentAreaFilled(false);
        btnPlayWord.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setImageBtn(btnPlayWord, ConstanteComponentMessage.RES_NEXT);
        btnPlayWord.addActionListener(e -> gameModel.selectPlayWordAction());
    }


    private void initRecallOption() {

        int x = getWidth() - OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;

        btnRecall = new JButton(messages.getString("Recall"));
        btnRecall.setName(ConstanteTestName.RECALL_NAME);
        btnRecall.setSize(OPTIONS_WIDTH, getHeight());
        btnRecall.setLocation(x, POS_Y);
        btnRecall.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnRecall);

        btnRecall.addActionListener(actionEvent -> gameModel.recallTiles());
    }

    private void initOrderTilesOption() {

        int x = getWidth() - OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;
        btnOrderTiles = new JButton();
        btnOrderTiles.setName(ConstanteTestName.ORDER_NAME);

        btnOrderTiles.setSize(OPTIONS_WIDTH, getHeight());
        btnOrderTiles.setLocation(x, POS_Y);
        btnOrderTiles.setLocation(x, POS_Y);
        btnOrderTiles.setOpaque(false);
        btnOrderTiles.setFocusPainted(false);
        btnOrderTiles.setBorderPainted(false);
        btnOrderTiles.setContentAreaFilled(false);
        btnOrderTiles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOrderTiles.setSize(OPTIONS_WIDTH, getHeight());
        setImageBtn(btnOrderTiles, ConstanteComponentMessage.RES_AZ);
        add(btnOrderTiles);

        add(btnOrderTiles);

        btnOrderTiles.addActionListener(e -> {
            currentPlayer.orderTiles();
        });
    }

    private void initExchangeOption() {


        int x = OPTIONS_WIDTH + ScrabbleGUI.MARGIN;
        Rectangle bounds = new Rectangle(x, POS_Y, OPTIONS_WIDTH, getHeight());

        btnExchange = new ButtonExchange(messages.getString("Exchange"), gameModel, bounds);
        btnExchange.setName(ConstanteTestName.EXCHANGE_NAME);
        btnExchange.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExchange.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExchange.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        x = getWidth() - OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;
        btnCancelExchange = new JButton();
        btnCancelExchange.setVisible(false);
        btnCancelExchange.setName(ConstanteTestName.CANCEL_EXCHANGE_NAME);
        btnCancelExchange.setLocation(x, POS_Y);
        btnCancelExchange.setSize(OPTIONS_WIDTH, getHeight());
        btnCancelExchange.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setImageBtn(btnCancelExchange, ConstanteComponentMessage.RES_RETURN);

        add(btnExchange);
        add(btnCancelExchange);

        btnExchange.addActionListener(e -> {

            // TODO Louis : Utiliser aviserObservateur
            if (currentPlayer.getState().getName() != IDState.EXCHANGE.getName()) {
                btnExchange.setText(messages.getString("Confirm"));
                disableAllOtherBtnExchange(false);
            } else {
                disableAllOtherBtnExchange(true);
                btnExchange.setText(messages.getString("Exchange"));
            }
            gameModel.exchangeLetter();
        });

        btnCancelExchange.addActionListener(e -> {

            gameModel.cancelExchange();
            btnExchange.setText(messages.getString("Exchange"));
            disableAllOtherBtnExchange(true);
        });
    }

    private void disableAllOtherBtnExchange(boolean enabler) {
        btnPlayWord.setEnabled(enabler);
        btnRecall.setEnabled(enabler);
        btnPassTurn.setEnabled(enabler);
        btnCancelExchange.setVisible(!enabler);
    }

    // TODO Louis : Faire en sorte qu'on enleve le joueur et non quitter la partie
    private void initForfeitOption() {

        btnForfeit = new JButton();
        btnForfeit.setName(ConstanteTestName.FORFEIT_NAME);
        btnForfeit.setSize(OPTIONS_WIDTH, getHeight());
        btnForfeit.setLocation(POS_Y, POS_Y);
        setImageBtn(btnForfeit, ConstanteComponentMessage.RES_ABANDON);
        btnForfeit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnForfeit);
        btnForfeit.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(null,
                    messages.getString("Replay"),
                    messages.getString("Abandon"),
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                gameModel.forfeit();
            }
        });
    }

    @Override
    public void changementEtat() {

        if (currentPlayer != gameModel.getActivePlayer()) {
            currentPlayer = gameModel.getActivePlayer();
        }

        if (currentPlayer.getState().getName().equals(IDState.PLAY_TILE.getName())) {
            btnPassTurn.setVisible(false);
            btnOrderTiles.setVisible(false);
            btnRecall.setVisible(true);
            btnPlayWord.setVisible(true);
        } else {
            btnRecall.setVisible(false);
            btnPlayWord.setVisible(false);
            btnPassTurn.setVisible(true);
            btnOrderTiles.setVisible(true);
        }

        for (Component comp : panelLettersRack.getComponents()) {
            panelLettersRack.remove(comp);
        }

        List<Tile> playerTiles = currentPlayer.getTiles();
        int i = 0;

        int x = (int) ((MAX_TILES_IN_HAND - playerTiles.size()) * TILE_DIMENSION.getWidth() / 2);

        for (Tile letter : playerTiles) {

            ButtonTile btnTile = new ButtonTile(gameModel, letter, iconsTile.get(letter.getLetter()));
            btnTile.setSize(TILE_DIMENSION);
            btnTile.setLocation(x, POS_Y);
            letter.ajouterObservateur(btnTile);
            btnTile.setName(ConstanteTestName.TILE_NAME + i);
            btnTile.addActionListener(this);
            panelLettersRack.add(btnTile);
            i++;
            x += TILE_DIMENSION.getWidth();
        }

        panelLettersRack.repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

        if (e.equals(Event.SELECT_BLANK_TILE_VALUE)) {
            if(!testMode)
            {
                Tile tileSelected = (Tile) o;
                DialogBlankTileChoice tileChoice = new DialogBlankTileChoice(parent, tileSelected, imageThemePath);
                tileChoice.setModal(true);
                tileChoice.setVisible(true);
            }
        }
    }

    private void initIconsTile() {

        iconsTile = new HashMap<>();
        int size = (int) TILE_DIMENSION.getWidth();

        String themFolder = imageThemePath;

        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET; start++) {
            String resource = themFolder + "/"+ start + ConstanteComponentMessage.EXT_PNG;
            URL res = getClass().getClassLoader().getResource(resource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
            iconsTile.put(Character.toString(start), icon);
        }
        iconsTile.put("",
                ImagesManager.getIcon(getClass().getClassLoader().getResource(themFolder + "/"+
                         "1" + ConstanteComponentMessage.EXT_PNG), size, size));

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        ButtonTile tileClicked = (ButtonTile) actionEvent.getSource();

        Tile tileSelected = tileClicked.getTile();

        gameModel.selectLetter(tileSelected);

    }
}
