package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;

/**
 * Created by 1468636 on 2016-11-15.
 */
public class PanelPlayers extends JPanel implements Observateur {

    private Game game;

    public void setGame (Game game){
        this.game = game;
    }
    @Override
    public void changementEtat() {

        if(game.isWaitingNextTurn()){
            setVisible(false);
            setEnabled(false);
        }else {
            setVisible(true);
            setEnabled(true);
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
