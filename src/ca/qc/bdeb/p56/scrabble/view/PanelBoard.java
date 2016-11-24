package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;

import static ca.qc.bdeb.p56.scrabble.shared.IDState.*;

/**
 * Created by Julien Brosseau on 2016-11-15.
 */
public class PanelBoard extends JPanel implements Observateur {

    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void changementEtat() {

        setVisible(!game.isWaitingNextTurn());
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
