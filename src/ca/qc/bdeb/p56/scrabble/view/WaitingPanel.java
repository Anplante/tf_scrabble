package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Julien Brosseau on 11/14/2016.
 */
public class WaitingPanel extends JPanel implements Observateur, KeyListener {

    private ScrabbleGUI parent;
    private Game game;
    private JLabel backgroundLabel;

    public WaitingPanel (Dimension dimension, ScrabbleGUI parent){
        setVisible(false);
        this.setEnabled(false);
        setSize(dimension);
        this.parent = parent;
        ImageIcon fillingIcon = new ImageIcon(getClass().getClassLoader().getResource(ConstanteComponentMessage.RES_WAITING_IMAGE));
        add(backgroundLabel = new JLabel(fillingIcon));
    }

    public void setGame(Game aGame){
        if (game != null) {
            game.retirerObservateur(this);
        }
        game = aGame;
        game.ajouterObservateur(this);
    }

    @Override
    public void changementEtat() {
        if(game.isWaitingNextTurn()){
            this.setVisible(true);
            setEnabled(true);
        }else {
            setVisible(false);
            this.setEnabled(false);
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        game.setWaitingNextTurn(false);
        game.aviserObservateurs();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
