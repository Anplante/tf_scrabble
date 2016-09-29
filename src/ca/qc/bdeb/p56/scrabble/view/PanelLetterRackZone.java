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

    private final double RATIO_LETTERS_ZONE = .1;
    private final int TILE_SIZE;
    private JButton btnSwapTiles;
    private JButton btnPassTurn;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JPanel panelLettersRack;



   // private final int LETTERS_ZONE_HEIGHT;
  //  private final int LETTERS_ZONE_WIDTH;


    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        currentPlayer = null;
        players = null;
        setBounds(boundsZoneLetterRack);
        panelLettersRack = new JPanel();

        setLayout(null);
        TILE_SIZE = (int) (getHeight() - getHeight()*RATIO_LETTERS_ZONE*2);
        initPanelLettersRack();
    }

    private void initPanelLettersRack()
    {


        int x = getWidth() / 4;
        int y = getHeight() / 8;

        int width = TILE_SIZE * 7;
        panelLettersRack.setBounds(x, y, width,TILE_SIZE-10);
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
        btnSwapTiles.setSize(100, 50);
        btnSwapTiles.setName("Exchange");
        add(btnSwapTiles);

        btnSwapTiles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (currentPlayer.getState().getName() != IDState.EXCHANGE.getName()) {
                    game.activateExchangeOption();                                 //// il aurait moyen de regrouper et laisser l'etat verifier puis juste avertir l'observateur
                    btnSwapTiles.setText("Confirmer");
                } else {
                    game.exchangeLetters();
                    changementEtat();
                    btnSwapTiles.setText("Échanger");
                }
            }
        });
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
        for (Tile letter : playerTiles) {
            BtnTile tile = new BtnTile(game, letter, new Dimension(TILE_SIZE, TILE_SIZE));
            tile.setName("Tile" + i);
            panelLettersRack.add(tile);
            i++;
        }
        panelLettersRack.revalidate();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
