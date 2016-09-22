package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.StateExchange;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import com.sun.org.apache.bcel.internal.generic.NEW;

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

    private JButton BtnSwapTiles;
    private JButton btnSkipTurn;
    private JButton btnHint;
    private JButton btnPlayWord;
    private JButton BtnContextAction;
    private ArrayList<BtnTile> playerRack;

    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        super();
        currentPlayer = null;
        players = null;
        playerRack = new ArrayList<>();
        setBounds(boundsZoneLetterRack);
        setLayout(null);
        initializeBtnPlayWord();
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


        //TODO refactor and combine

        int x = getWidth() - 100;
        int y = (getHeight() - 50) / 2;

        BtnSwapTiles = new JButton("Échanger");
        btnSkipTurn = new JButton("Passer le tour");
        btnHint = new JButton("Indice");
        BtnContextAction = new JButton();

        BtnContextAction.setVisible(false);
        BtnContextAction.setBounds(x-420, y+5, 75, 40);
        BtnContextAction.setMargin(new Insets(0, 0, 0, 0));

        BtnSwapTiles.setBounds(x-530, y, 100, 50);
        BtnSwapTiles.setMargin(new Insets(0, 0, 0, 0));

        btnSkipTurn.setBounds(x-640, y, 100, 50);
        btnSkipTurn.setMargin(new Insets(0, 0, 0, 0));

        btnHint.setBounds(x-750, y, 100, 50);
        btnHint.setMargin(new Insets(0, 0, 0, 0));

        add(btnHint);
        add(btnSkipTurn);
        add(BtnSwapTiles);
        add(BtnContextAction);

        BtnSwapTiles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentPlayer.getState().getName()!= IDState.EXCHANGE.getName()) {
                    BtnSwapTiles.setText("Confirmer");
                    BtnContextAction.setText("Annuler");
                    BtnContextAction.setVisible(true);
                    game.getActivePlayer().exchangeMode(new StateExchange(currentPlayer));
                }else {
                    StateExchange stateX = (StateExchange) currentPlayer.getState();
                    if (stateX.getSelectedTiles().size() != 0) {
                        for (int i = 0; i < stateX.getSelectedTiles().size(); i++) {
                            boolean found = false;
                            for (int j = 0; j < currentPlayer.getTiles().size() && !found; j++) {
                                if (stateX.getSelectedTiles().get(i) == currentPlayer.getTiles().get(j)) {
                                    game.addATile(game.getActivePlayer().getTiles().get(j));
                                    currentPlayer.getTiles().remove(j);
                                    currentPlayer.getTiles().add(game.getATile());
                                    found = true;
                                }
                            }
                        }

                    } else {
                        //TODO MESSSAGE ERROR CANT SWAP NOTHING
                    }
                    paintCurrentTilesPlayer();
                    BtnContextAction.setVisible(false);
                    BtnSwapTiles.setText("Échanger");
                }
            }
        });
        btnSkipTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnHint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        BtnContextAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentPlayer.selectNextState(IDState.PENDING);
                BtnContextAction.setVisible(false);
                BtnSwapTiles.setText("Échanger");
                currentPlayer.nextState();
            }
        });
    }

    private void exchangeTiles(){

    }

    private void initializeBtnPlayWord() {
        // TODO Louis : Mettre un label en dessous du boutton ou trouver un moyen pour que le text s'ajuste à la taille du bouton

        btnPlayWord = new JButton("Play");

        int x = getWidth() - 100;
        int y = (getHeight() - 50) / 2;

        btnPlayWord.setBounds(x, y, 50, 50);
        btnPlayWord.setMargin(new Insets(0, 0, 0, 0));

        btnPlayWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.playWord();
            }
        });

        add(btnPlayWord);
    }

    @Override
    public void changementEtat() {

        if (currentPlayer != game.getActivePlayer()) {
            currentPlayer = game.getActivePlayer();
        }

        for (Component comp : getComponents()) {
            if (comp.getClass().equals(BtnTile.class))
                remove(comp);
        }

        List<Tile> playerTiles = currentPlayer.getTiles();

        int x = 200;
        int y = (getHeight() - 50) / 2;


        for (Tile letter : playerTiles) {
            BtnTile tile = new BtnTile(game, letter, new Rectangle(x, y, 50, 50));
            playerRack.add(tile);
            add(tile);
            x += 60;
        }
        repaint();
}

    private void paintCurrentTilesPlayer(){
        for (int i = 0; i < currentPlayer.getTiles().size(); i++) {
            playerRack.get(i).setTile(currentPlayer.getTiles().get(i));
        }
        repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
