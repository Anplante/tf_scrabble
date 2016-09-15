package ca.qc.bdeb.p56.scrabble.vue;

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
        lblName = new JLabel();
        lblScore = new JLabel();

        setLayout(new GridLayout(2,2));

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
