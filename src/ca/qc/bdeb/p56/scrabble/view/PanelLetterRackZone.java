package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Letter;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class PanelLetterRackZone extends JPanel implements Observateur{

    private Player player;
    private Game game;

    public PanelLetterRackZone(Rectangle boundsZoneLetterRack){

        super();
        player = null;
        setBounds(boundsZoneLetterRack);
        setLayout(null);
    }


    public void setPlayer(Player player)
    {
        if(player != null)
        {
            player.retirerObservateur(this);
        }
        this.player = player;
        this.player.ajouterObservateur(this);
    }

    void setGame(Game aGame)
    {
        this.game = aGame;
    }

    @Override
    public void changementEtat() {

        if(player != game.getActivePlayer())
        {
            setPlayer(game.getActivePlayer());
        }

        for (Component comp : getComponents()) {
            remove(comp);
        }

        int x = ((getWidth()) /2) - 150;
        int y = (getHeight()- 50)/2;

        List<Letter> letters = player.getLetters();

        for(Letter letter : letters)
        {
            BtnTile tile = new BtnTile(game, letter, new Rectangle(x, y, 50, 50) );
            add(tile);
            x += 60;
       }
        repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
