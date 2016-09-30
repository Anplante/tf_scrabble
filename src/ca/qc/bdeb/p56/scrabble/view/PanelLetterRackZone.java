package ca.qc.bdeb.p56.scrabble.view;

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
    private Game game;
    private List<BtnTile> listBtnTiles;

    private final double RATIO_LETTERS_ZONE = .1;
    private final int TILE_SIZE;
    private JButton btnSwapTiles;
    private JButton btnPassTurn;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JButton btnCancelExchange;
    private JPanel panelLettersRack;


    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        super(new FlowLayout());
        currentPlayer = null;
        players = null;
        setBounds(boundsZoneLetterRack);
        listBtnTiles = new ArrayList<>();
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

    public  List<BtnTile> getListBtnTiles(){
        return listBtnTiles;
    }

    private void initializeOptions() {

        initPassTurnOption();
        initExchangeOption();
        initRecallOption();
        initiBtnPlayWord();
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

                if(currentPlayer.getState().getName()!= IDState.EXCHANGE.getName()) {
                    btnRecall.doClick();
                    currentPlayer.selectNextState(IDState.EXCHANGE);                                 //// il aurait moyen de regrouper et laisser l'etat verifier puis juste avertir l'observateur
                    currentPlayer.nextState();
                    btnSwapTiles.setText("Confirmer");
                    disableAllOtherBtnExchange(false);
                }else {
                    disableAllOtherBtnExchange(true);
                    game.exchangeLetters(getListBtnTiles());
                    changementEtat();
                    btnSwapTiles.setText("Échanger");

                }
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
            BtnTile tile = new BtnTile(game, letter, dimension );
            tile.setMinimumSize(dimension);
            tile.setMaximumSize(dimension);
            tile.setName("Tile" + i);
            panelLettersRack.add(tile);
            listBtnTiles.add(tile);
            i++;
        }
      //  panelLettersRack.revalidate();
        panelLettersRack.repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
