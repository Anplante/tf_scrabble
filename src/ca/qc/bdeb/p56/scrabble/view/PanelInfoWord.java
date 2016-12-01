package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Julien Brosseau on 11/30/2016.
 */
public class PanelInfoWord extends JPanel implements Observateur{

    private JLabel wordPlayed;
    private Game game;

    public PanelInfoWord (Game game){
        this.game = game;
        wordPlayed = new JLabel(ConstanteComponentMessage.NO_WORD_POINTS);
        game.ajouterObservateur(this);
        add(wordPlayed);
        wordPlayed.setVisible(true);
        wordPlayed.setFont(new Font("Courier New", Font.ITALIC, 20));
        wordPlayed.setForeground(Color.BLACK);
    }


    @Override
    public void changementEtat() {
        if(game.getCurrentWord()!=null){

            wordPlayed.setText(game.getCurrentWord());
        }else{
            wordPlayed.setText(ConstanteComponentMessage.INVALID_WORD_POINTS);
        }
        if(game.getActivePlayer().getState().getName().equals(IDState.PENDING.getName())){
            wordPlayed.setText(ConstanteComponentMessage.NO_WORD_POINTS);
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }

}
