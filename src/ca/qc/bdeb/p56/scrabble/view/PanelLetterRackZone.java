package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.GameManager;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class PanelLetterRackZone extends JPanel implements Observateur {

    private final int MAX_TILES_IN_HAND = 7;
    private final double RATIO_TILES_RACK = .5;
    private final int POS_Y = 0;
    private final int LETTERS_RACK_WIDTH;
    private final int OPTIONS_WIDTH;

    private Player currentPlayer;
    private List<Player> players;
    private List<Player> resetPlayers;
    private Game game;
    private ButtonExchange btnSwapTiles;
    private JButton btnPassTurn;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JButton btnForfeit;
    private JButton btnCancelExchange;
    private JPanel panelLettersRack;


    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        super();

        setLayout(null);
        setBounds(boundsZoneLetterRack);
        LETTERS_RACK_WIDTH =  (int) (getWidth() * RATIO_TILES_RACK);
        OPTIONS_WIDTH = ((getWidth() - LETTERS_RACK_WIDTH) / 4)  -  ScrabbleGUI.MARGIN;
    }

    public void setPlayer(List<Player> players) {

        if (currentPlayer != null) {
            for (Player player : players) {
                player.retirerObservateur(this);
            }
        }
        this.players = players;
        for (Player player : players) {
            player.ajouterObservateur(this);

        }
    }

    public void setGame(Game aGame) {
        if (game != null) {
            game.retirerObservateur(this);
            game.retirerObservateur(btnSwapTiles);
        }
        game = aGame;


        initPanelLettersRack();
        initializeOptions();
        game.ajouterObservateur(this);
        game.ajouterObservateur(btnSwapTiles);
    }

    private void initPanelLettersRack() {

        int x = getWidth() / 2 - LETTERS_RACK_WIDTH / 2;
        panelLettersRack = new JPanel();
        panelLettersRack.setLayout(null);
        panelLettersRack.setLocation(x, POS_Y);
        panelLettersRack.setSize(LETTERS_RACK_WIDTH, getHeight());
        panelLettersRack.setName("Letter rack");
        add(panelLettersRack);
    }

    private void initializeOptions() {

        initForfeitOption();
        initExchangeOption();
        initRecallOption();
        initiBtnPlayWord();
        initPassTurnOption();
    }

    private void initPassTurnOption() {

        int x = getWidth() - OPTIONS_WIDTH;
        btnPassTurn = new JButton("Passer le tour");

        btnPassTurn.setName("Pass turn");
        btnPassTurn.setSize( OPTIONS_WIDTH , getHeight());
        btnPassTurn.setLocation(x, POS_Y);
        btnPassTurn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPassTurn.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPassTurn.setVisible(false);  // Pas certain que c'est necessaire

        add(btnPassTurn);


        btnPassTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.passTurn();
            }
        });
    }

    private void initiBtnPlayWord() {

        int x = getWidth() - OPTIONS_WIDTH;

        btnPlayWord = new JButton("Play");
        btnPlayWord.setName("Play");
        btnPlayWord.setSize( OPTIONS_WIDTH, getHeight());
        btnPlayWord.setLocation(x, POS_Y);

        add(btnPlayWord);
        btnPlayWord.setVisible(false);
        btnPlayWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.playWord();
            }
        });
    }

    private void initRecallOption() {

        int x = getWidth() -  OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;

        btnRecall = new JButton("Recall");
        btnRecall.setName("Recall");
        btnRecall.setSize( OPTIONS_WIDTH, getHeight());
        btnRecall.setLocation(x , POS_Y);

        add(btnRecall);

        btnRecall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                game.recallTiles();
            }
        });
    }


    private void initExchangeOption() {


        int x =  OPTIONS_WIDTH + ScrabbleGUI.MARGIN ;
        Rectangle bounds = new Rectangle(x , POS_Y,  OPTIONS_WIDTH, getHeight());

        btnSwapTiles = new ButtonExchange("Échanger", game ,  bounds );
        btnSwapTiles.setName("Exchange");
        btnSwapTiles.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSwapTiles.setHorizontalTextPosition(SwingConstants.CENTER);

        x = getWidth() -  OPTIONS_WIDTH * 2 - ScrabbleGUI.MARGIN;
        btnCancelExchange = new JButton("Annuler");
        btnCancelExchange.setVisible(false);
        btnCancelExchange.setName("Cancel Exchange");
        btnCancelExchange.setLocation(x, POS_Y);
        btnCancelExchange.setSize( OPTIONS_WIDTH, getHeight());

        add(btnSwapTiles);
        add(btnCancelExchange);

        btnSwapTiles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (currentPlayer.getState().getName() != IDState.EXCHANGE.getName()) {
                    btnSwapTiles.setText("Confirmer");
                    disableAllOtherBtnExchange(false);
                } else {
                    disableAllOtherBtnExchange(true);
                    btnSwapTiles.setText("Échanger");
                }
                game.exchangeLetter();
            }
        });

        btnCancelExchange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                game.cancelExchange();
                btnSwapTiles.setText("Échanger");
                disableAllOtherBtnExchange(true);
            }
        });
    }

    private void disableAllOtherBtnExchange(boolean enabler) {
        btnPlayWord.setEnabled(enabler);
        btnRecall.setEnabled(enabler);
        btnPassTurn.setEnabled(enabler);
        btnCancelExchange.setVisible(!enabler);
    }

    private void initForfeitOption() {

        btnForfeit = new JButton("Abandonner");
        btnForfeit.setName("forfeit");

        btnForfeit.setSize( OPTIONS_WIDTH, getHeight());
        btnForfeit.setLocation(POS_Y, POS_Y);

        add(btnForfeit);
        btnForfeit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog((Component) null,
                        "Voulez recommencez la partie?",
                        "Abandonner",
                        JOptionPane.YES_NO_CANCEL_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    resetPlayer();
                    reinitializeGame();
                }
            }
        });
    }

    private void resetPlayer() {

        resetPlayers = new ArrayList<>();
        resetPlayers.add(new Player(currentPlayer.getName()));

        for (int i = 0; i < players.size() - 1; i++) {
            resetPlayers.add(new AiPlayer());
        }
    }

    private void reinitializeGame() {

        GameManager gameManager = new GameManager();
        game = gameManager.createNewGame(resetPlayers);
        game.ajouterObservateur(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  // TODO Louis : à revoir, car je ne crois pas que le component devrait directement recréer son parent.
        ScrabbleGUI gameGUI = new ScrabbleGUI();

      /*  gameGUI.createScrabbleGame(game);
        gameGUI.setVisible(true);
        setVisible(false);
        gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.windowForComponent(this).dispose();*/
    }

    @Override
    public void changementEtat() {

        if (currentPlayer != game.getActivePlayer()) {
            currentPlayer = game.getActivePlayer();
        }

        if (currentPlayer.getState().getName() == IDState.PLAY_TILE.getName()) {
            btnPassTurn.setVisible(false);
            btnRecall.setVisible(true);
            btnPlayWord.setVisible(true);
        } else {
            btnRecall.setVisible(false);
            btnPlayWord.setVisible(false);
            btnPassTurn.setVisible(true);
        }

        for (Component comp : panelLettersRack.getComponents()) {
            panelLettersRack.remove(comp);
        }

        List<Tile> playerTiles = currentPlayer.getTiles();
        int i = 0;
        int width = LETTERS_RACK_WIDTH/MAX_TILES_IN_HAND ;
        int x = (MAX_TILES_IN_HAND - playerTiles.size()) * width  / 2;

       Dimension dimension = new Dimension(width ,width);

        for (Tile letter : playerTiles) {

            ButtonTile btnTile = new ButtonTile(game, letter, dimension);
            btnTile.setLocation(x, POS_Y);
            letter.ajouterObservateur(btnTile);
            btnTile.setName("Tile" + i);
            panelLettersRack.add(btnTile);
            i++;
            x += width ;
        }

        panelLettersRack.repaint();

    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
