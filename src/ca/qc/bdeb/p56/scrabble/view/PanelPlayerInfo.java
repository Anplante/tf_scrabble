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
    private JLabel lblTitre;
    private JLabel lblScore;

    public PanelPlayerInfo(Player player){

        playerModel = player;
        player.ajouterObservateur(this);
        initComponents();
        changementEtat();
    }

    private void initComponents() {

        this.setLayout(null);
        lblName = new JLabel();
        lblTitre = new JLabel();
        lblScore = new JLabel();
        lblScore.setName("Score");
        add(lblName);
        add(lblScore);

        Font font = new Font("Comic Sans MS Bold", Font.PLAIN, 15);

        lblName.setFont(font);
        lblName.setBounds(11 , 11 ,
                100, 25);
        lblScore.setFont(font);
        lblScore.setBounds(50, 50, 25, 25);
    }

    @Override
    public void changementEtat() {

        lblName.setText(playerModel.getName());
        lblScore.setText(Integer.toString(playerModel.getScore()));
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(0,0, this.getWidth()- 10,this.getHeight() - 1);
        g.dispose();
    }
}
