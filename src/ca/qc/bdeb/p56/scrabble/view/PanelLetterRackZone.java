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
    private ArrayList<BtnTile> listBtnTiles;

    private JButton btnSwapTiles;
    private JButton btnSkipTurn;
    private JButton btnHint;
    private JButton btnPlayWord;
    private JButton btnRecall;
    private JButton btnCancelExchange;
    private JPanel panelLettersRack;



    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        super(new FlowLayout());
        currentPlayer = null;
        players = null;
        listBtnTiles = new ArrayList<>();
        setBounds(boundsZoneLetterRack);



        panelLettersRack = new JPanel(new FlowLayout());
        int x = getWidth()/2 - 150;
        int y = (getHeight() - 50) / 2;
        panelLettersRack.setBounds(x, y, 350, 50);
        add(panelLettersRack);
        initializeBtnPlayWord();
        initializeOptions();
        initializeRecallOption();
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

    public  ArrayList<BtnTile> getListBtnTiles(){
        return listBtnTiles;
    }

    private void initializeOptions() {

        //TODO refactor and combine

        int x = getWidth() - 100;
        int y = (getHeight() - 50) / 2;

        btnSwapTiles = new JButton("Échanger");
        btnSkipTurn = new JButton("Passer le tour");
        btnHint = new JButton("Indice");

        btnCancelExchange = new JButton("Annuler");
        btnCancelExchange.setVisible(false);


        btnSwapTiles.setSize(100, 50);
        btnSwapTiles.setMargin(new Insets(0, 0, 0, 0));

        btnCancelExchange.setSize(100,50);
        btnCancelExchange.setMargin(new Insets(0,0,0,0));

        btnSkipTurn.setSize(100, 50);
        btnSkipTurn.setMargin(new Insets(0, 0, 0, 0));
        btnSkipTurn.setName("Pass turn");




        add(btnSkipTurn);
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

        btnSkipTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.passTurn();
            }
        });
        btnHint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
        btnHint.setEnabled(enabler);
        btnPlayWord.setEnabled(enabler);
        btnSkipTurn.setEnabled(enabler);
        btnRecall.setEnabled(enabler);
        btnCancelExchange.setVisible(!enabler);

    }

    private void initializeRecallOption()
    {
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

        for (Component comp : panelLettersRack.getComponents()) {
                panelLettersRack.remove(comp);
        }

        List<Tile> playerTiles = currentPlayer.getTiles();

        int x = 200;
        int y = (getHeight() - 50) / 2;
        int i = 0;

        for (Tile letter : playerTiles) {
            BtnTile tile = new BtnTile(game, letter, new Dimension(50,50));
            tile.setName("Tile" +i);
            panelLettersRack.add(tile);
            listBtnTiles.add(tile);
            x += 60;
            i++;
        }
        panelLettersRack.revalidate();
        //repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
