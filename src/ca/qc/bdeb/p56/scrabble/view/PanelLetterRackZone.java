package ca.qc.bdeb.p56.scrabble.view;


import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class PanelLetterRackZone extends JPanel implements Observateur, ActionListener {


    private final int MAX_TILES_IN_HAND = 7;
    private final double RATIO_TILES_RACK = .5;
    private final int POS_Y = 0;
    private final int LETTERS_RACK_WIDTH;
    private final int OPTIONS_WIDTH;
    private final Dimension TILE_DIMENSION;

    private Player currentPlayer;
    private Game gameModel;
    private ButtonExchange btnSwapTiles;
    private JButton btnPassTurn;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JButton btnForfeit;
    private JButton btnCancelExchange;
    private JButton btnShuffleTiles;
    private JPanel panelLettersRack;
    private ScrabbleGUI parent;
    private HashMap<String, ImageIcon> iconsTile;


    public PanelLetterRackZone(Rectangle boundsZoneLetterRack, ScrabbleGUI parent) {

        super();

        this.parent = parent;
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

    public void setGameModel(Game aGame) {
        if (gameModel != null) {
            gameModel.retirerObservateur(this);
            gameModel.retirerObservateur(btnSwapTiles);
        }
        gameModel = aGame;


        initPanelLettersRack();
        initializeOptions();
        gameModel.ajouterObservateur(this);
        gameModel.ajouterObservateur(btnSwapTiles);
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
        initShuffleTilesOption();
    }

    private void initPassTurnOption() {

        int x = getWidth() - OPTIONS_WIDTH;
        btnPassTurn = new JButton(ConstanteComponentMessage.MESS_PASS_TURN);

        btnPassTurn.setName(ConstanteTestName.PASS_TURN_NAME);
        btnPassTurn.setSize(OPTIONS_WIDTH, getHeight());
        btnPassTurn.setLocation(x, POS_Y);
        btnPassTurn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPassTurn.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPassTurn.setVisible(false);

        add(btnPassTurn);

        btnPassTurn.addActionListener(e -> gameModel.passTurn());
    }

    private void initiBtnPlayWord() {

        int x = getWidth() - OPTIONS_WIDTH;

        btnPlayWord = new JButton(ConstanteComponentMessage.MESS_PLAY);
        btnPlayWord.setName(ConstanteTestName.PLAY_NAME);
        btnPlayWord.setSize(OPTIONS_WIDTH, getHeight());
        btnPlayWord.setLocation(x, POS_Y);

        add(btnPlayWord);
        btnPlayWord.setVisible(false);
        btnPlayWord.addActionListener(e -> gameModel.selectPlayWordAction());
    }


    private void initRecallOption() {

        int x = getWidth() - OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;

        btnRecall = new JButton(ConstanteComponentMessage.MESS_RECALL);
        btnRecall.setName(ConstanteTestName.RECALL_NAME);
        btnRecall.setSize(OPTIONS_WIDTH, getHeight());
        btnRecall.setLocation(x, POS_Y);
        add(btnRecall);

        btnRecall.addActionListener(actionEvent -> gameModel.recallTiles());
    }

    private void initShuffleTilesOption() {

        int x = getWidth() - OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;
        btnShuffleTiles = new JButton(ConstanteComponentMessage.TITLE_SHUFFLE);
        btnShuffleTiles.setName(ConstanteTestName.SHUFFLE_NAME);

        btnShuffleTiles.setSize(OPTIONS_WIDTH, getHeight());
        btnShuffleTiles.setLocation(x, POS_Y);
        add(btnShuffleTiles);

        btnShuffleTiles.addActionListener(e -> {
            currentPlayer.shuffleTiles();
        });
    }

    private void initExchangeOption() {


        int x = OPTIONS_WIDTH + ScrabbleGUI.MARGIN;
        Rectangle bounds = new Rectangle(x, POS_Y, OPTIONS_WIDTH, getHeight());

        btnSwapTiles = new ButtonExchange(ConstanteComponentMessage.MESS_EXCHANGE, gameModel, bounds);
        btnSwapTiles.setName(ConstanteTestName.EXCHANGE_NAME);
        btnSwapTiles.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSwapTiles.setHorizontalTextPosition(SwingConstants.CENTER);

        x = getWidth() - OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;
        btnCancelExchange = new JButton(ConstanteComponentMessage.MESS_CANCEL);
        btnCancelExchange.setVisible(false);
        btnCancelExchange.setName(ConstanteTestName.CANCEL_EXCHANGE_NAME);
        btnCancelExchange.setLocation(x, POS_Y);
        btnCancelExchange.setSize(OPTIONS_WIDTH, getHeight());

        add(btnSwapTiles);
        add(btnCancelExchange);

        btnSwapTiles.addActionListener(e -> {

            // TODO Louis : Utiliser aviserObservateur
            if (currentPlayer.getState().getName() != IDState.EXCHANGE.getName()) {
                btnSwapTiles.setText(ConstanteComponentMessage.MESS_CONFIRM);
                disableAllOtherBtnExchange(false);
            } else {
                disableAllOtherBtnExchange(true);
                btnSwapTiles.setText(ConstanteComponentMessage.MESS_EXCHANGE);
            }
            gameModel.exchangeLetter();
        });

        btnCancelExchange.addActionListener(e -> {

            gameModel.cancelExchange();
            btnSwapTiles.setText(ConstanteComponentMessage.MESS_EXCHANGE);
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

        btnForfeit = new JButton(ConstanteComponentMessage.TITLE_SURRENDER);
        btnForfeit.setName(ConstanteTestName.FORFEIT_NAME);

        btnForfeit.setSize(OPTIONS_WIDTH, getHeight());
        btnForfeit.setLocation(POS_Y, POS_Y);

        add(btnForfeit);
        btnForfeit.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(null,
                    ConstanteComponentMessage.MESS_RESTART_GAME,
                    ConstanteComponentMessage.TITLE_SURRENDER,
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
            btnShuffleTiles.setVisible(false);
            btnRecall.setVisible(true);
            btnPlayWord.setVisible(true);
        } else {
            btnRecall.setVisible(false);
            btnPlayWord.setVisible(false);
            btnPassTurn.setVisible(true);
            btnShuffleTiles.setVisible(true);
        }

        for (Component comp : panelLettersRack.getComponents()) {
            panelLettersRack.remove(comp);
        }

        List<Tile> playerTiles = currentPlayer.getTiles();
        int i = 0;

        int x = (int) ((MAX_TILES_IN_HAND - playerTiles.size()) * TILE_DIMENSION.getWidth() / 2);

        //DragListener drag = new DragListener();   // Todo Louis : permettre le drag & drop

        for (Tile letter : playerTiles) {

            ButtonTile btnTile = new ButtonTile(gameModel, letter, iconsTile.get(letter.getLetter()));
            btnTile.setSize(TILE_DIMENSION);
            btnTile.setLocation(x, POS_Y);
            letter.ajouterObservateur(btnTile);
            btnTile.setName(ConstanteTestName.TILE_NAME + i);
            btnTile.addActionListener(this);
            //  btnTile.addMouseListener(drag);
            //btnTile.addMouseMotionListener(drag);
            panelLettersRack.add(btnTile);
            i++;
            x += TILE_DIMENSION.getWidth();
        }

        setVisible(!gameModel.isWaitingNextTurn());
        panelLettersRack.repaint();
    }

    private void initIconsTile() {

        iconsTile = new HashMap<>();
        int size = (int) TILE_DIMENSION.getWidth();

        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET; start++) {
            String resource = parent.getImgPath() + start + ConstanteComponentMessage.EXT_PNG;
            URL res = getClass().getClassLoader().getResource(resource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
            iconsTile.put(Character.toString(start), icon);
        }
        iconsTile.put("",
                ImagesManager.getIcon(getClass().getClassLoader().getResource(parent.getImgPath()
                        + "1" + ConstanteComponentMessage.EXT_PNG), size, size));

    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        ButtonTile tileClicked = (ButtonTile) actionEvent.getSource();

        Tile tileSelected = tileClicked.getTile();

        if (tileSelected.isBlankTile()) {
            DialogBlankTileChoice tileChoice = new DialogBlankTileChoice(parent, tileSelected);
            tileChoice.setModal(true);
            tileChoice.setVisible(true);
        }
        gameModel.selectLetter(tileSelected);

    }
}
