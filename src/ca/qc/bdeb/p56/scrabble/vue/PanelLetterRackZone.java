package ca.qc.bdeb.p56.scrabble.vue;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Letter;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import java.util.List;
import javax.swing.*;

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


        int x = 10;
        int y = (getHeight()- 50)/2;

        List<Letter> letters = player.getLetters();

        for(Letter letter : letters)
        {
            JLabel label = new JLabel(""+ letter.getLetter());
            label.setSize(50, 50);
            label.setBounds(x, y, 50, 50);
            add(label);
            x += 60;
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
