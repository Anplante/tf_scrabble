package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.ai.AiPlayer;
import ca.qc.bdeb.p56.scrabble.model.Game;
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


    private Player currentPlayer;
    private List<Player> players;
    private List<Player> resetPlayers;
    private Game game;

    private final double RATIO_LETTERS_ZONE = .1;
    private final int TILE_SIZE;
    private JButton btnSwapTiles;
    private JButton btnPassTurn;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JButton btnForfeit;
    private JButton btnCancelExchange;
    private JPanel panelLettersRack;


    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        super(new FlowLayout());
        currentPlayer = null;
        players = null;
        setBounds(boundsZoneLetterRack);
        panelLettersRack = new JPanel(new FlowLayout());

       // setLayout(null);
        TILE_SIZE = getWidth()/12;
        initPanelLettersRack();
    }

    private void initPanelLettersRack()
    {


        int x = getWidth() / 4;
        int y = getHeight() / 8;

        int width = TILE_SIZE * 7;
        panelLettersRack.setBounds(x, y, width,TILE_SIZE);
        panelLettersRack.setName("Letter rack");
        add(panelLettersRack);
        initializeOptions();

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
        }
        this.game = aGame;
        this.game.ajouterObservateur(this);
    }

    private void initializeOptions() {

        initPassTurnOption();
        initExchangeOption();
        initRecallOption();
        initiBtnPlayWord();
       // initForfeitOption();
    }

    private void initPassTurnOption()
    {
        btnPassTurn = new JButton("Passer le tour");
        btnPassTurn.setSize(100, 50);
        btnPassTurn.setName("Pass turn");
        add(btnPassTurn);
        btnPassTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.passTurn();
            }
        });
    }

    private void initExchangeOption() {

        btnSwapTiles = new JButton("Échanger");

        btnCancelExchange = new JButton("Annuler");
        btnCancelExchange.setVisible(false);


        btnSwapTiles.setName("Exchange");

        btnCancelExchange.setName("Cancel_Exchange");



        add(btnSwapTiles);
        add(btnCancelExchange);


        btnSwapTiles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                currentPlayer.selectNextState(IDState.EXCHANGE);



                if(currentPlayer.getState().getName()!= IDState.EXCHANGE.getName()) {
                    btnSwapTiles.setText("Confirmer");
                    disableAllOtherBtnExchange(false);
                }else {
                    disableAllOtherBtnExchange(true);
                    //changementEtat();
                    btnSwapTiles.setText("Échanger");
                }
                game.goToNextState();
            }
        });

        btnCancelExchange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentPlayer.selectNextState(IDState.SELECT_ACTION);
                btnSwapTiles.setText("Échanger");
                currentPlayer.nextState();
                disableAllOtherBtnExchange(true);
            }
        });
    }

    private void disableAllOtherBtnExchange(boolean enabler){
        btnPlayWord.setEnabled(enabler);
        btnRecall.setEnabled(enabler);
        btnPassTurn.setEnabled(enabler);
        btnCancelExchange.setVisible(!enabler);

    }
    private void initRecallOption() {

        btnRecall = new JButton("Recall");
        btnRecall.setSize(100, 50);
        btnRecall.setName("Recall");
        add(btnRecall);

        btnRecall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                game.recallTiles();
            }
        });
    }

    private void initiBtnPlayWord() {
        // TODO Louis : Mettre un label en dessous du boutton ou trouver un moyen pour que le text s'ajuste à la taille du bouton

        btnPlayWord = new JButton("Play");
        btnPlayWord.setSize(100, 50);
        btnPlayWord.setName("Play");
        add(btnPlayWord);

        btnPlayWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.playWord();
            }
        });
    }

 /*   private void initForfeitOption() {
        btnForfeit = new JButton("Abandonner");
        btnForfeit.setSize(100, 50);
        btnForfeit.setName("forfeit");
        add(btnForfeit);
        btnForfeit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int result = JOptionPane.showConfirmDialog((Component) null, "Voulez recommencez la partie?","Abandonner", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    resetPlayer();
                    reinitializeGame();
                }
            }
        });
    }*/

    private void resetPlayer() {
        resetPlayers = new ArrayList<>();
        resetPlayers.add(new Player(currentPlayer.getName()));

        for (int i = 0; i < players.size() - 1 ; i++) {
            resetPlayers.add(new AiPlayer());
        }
    }

/*    private void reinitializeGame() {

        GameManager gameManager = new GameManager();
        game = gameManager.createNewGame(resetPlayers);
        game.ajouterObservateur(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ScrabbleGUI gameGUI = new ScrabbleGUI(game, new Rectangle(screenSize));
        gameGUI.setVisible(true);
        setVisible(false);
        gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.windowForComponent(this).dispose();

    }*/

    @Override
    public void changementEtat() {


            if (currentPlayer != game.getActivePlayer()) {
                currentPlayer = game.getActivePlayer();
            }

            for (Component comp : panelLettersRack.getComponents()) {
                panelLettersRack.remove(comp);
            }

            List<Tile> playerTiles = currentPlayer.getTiles();

            int i = 0;

            Dimension dimension = new Dimension(TILE_SIZE, TILE_SIZE);

        for (Tile letter : playerTiles) {
            BtnTile btnTile = new BtnTile(game, letter, dimension );
            btnTile.setFocusable(false);
            letter.ajouterObservateur(btnTile);
            btnTile.setName("Tile" + i);
            panelLettersRack.add(btnTile);
            i++;
        }
            panelLettersRack.repaint();
        }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
