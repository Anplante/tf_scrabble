package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class PanelLetterRackZone extends JPanel implements Observateur{

    private Player player;
    private Game game;

    public PanelLetterRackZone(){
        super();
         player = null;
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


        if(player != null)
        {

        }

    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
