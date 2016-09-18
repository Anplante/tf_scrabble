package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class PanelPlayerInfo extends JPanel implements Observateur{

    private Player playerModel;
    private JLabel lblName;
    private JLabel lblScore;

    public PanelPlayerInfo(Player player){

        playerModel = player;
        player.ajouterObservateur(this);
        initComponents();
        changementEtat();
    }

    private void initComponents() {

        setLayout(new GridLayout(2,2));
        lblName = new JLabel();
        lblScore = new JLabel();
        add(lblName);
        add(lblScore);
    }

    @Override
    public void changementEtat() {

        lblName.setText(playerModel.getName());
        lblScore.setText(Integer.toString(playerModel.getScore()));
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }
}
