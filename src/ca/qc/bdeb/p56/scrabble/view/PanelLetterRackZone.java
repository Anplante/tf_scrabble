package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class PanelLetterRackZone extends JPanel implements Observateur {

    private Player player;
    private Game game;

    private JButton btnPlayWord;

    public PanelLetterRackZone(Rectangle boundsZoneLetterRack) {

        super();
        player = null;
        setBounds(boundsZoneLetterRack);
        setLayout(null);
        initializeBtnPlayWord();

    }


    public void setPlayer(Player player) {
        if (player != null) {
            player.retirerObservateur(this);
        }
        this.player = player;
        this.player.ajouterObservateur(this);
    }

    public void setGame(Game aGame) {
        this.game = aGame;
    }


    private void initializeBtnPlayWord()
    {
        // TODO Louis : Mettre un label en dessous du boutton ou trouver un moyen pour que le text s'ajuste à la taille du bouton

        btnPlayWord = new JButton("Play");

        int x = getWidth() - 100;
        int y = (getHeight() - 50) / 2;

        btnPlayWord.setBounds(x,y, 50,50);
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

        if (player != game.getActivePlayer()) {
            setPlayer(game.getActivePlayer());
        }

        for (Component comp : getComponents()) {
            if (comp.getClass().equals(BtnTile.class))
                remove(comp);
        }

        List<Tile> playerTiles = player.getTiles();

        int x = ((getWidth()) / 2) - playerTiles.size()*50/2;
        int y = (getHeight() - 50) / 2;


        for (Tile letter : playerTiles) {
            BtnTile tile = new BtnTile(game, letter, new Rectangle(x, y, 50, 50));
            add(tile);
            x += 60;
        }
        repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
