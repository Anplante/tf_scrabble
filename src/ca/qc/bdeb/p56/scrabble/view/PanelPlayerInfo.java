package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class PanelPlayerInfo extends JPanel implements Observateur{


    private static final String TEXT_FONT = "Comic Sans MS Bold";
    private static final Font fontOfPanel = new Font(TEXT_FONT, Font.PLAIN, 15);

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
        this.setLayout(null);
        initNameLabel();
        initScoreLabel();
    }

    private void initNameLabel() {
        lblName = new JLabel();
        lblName.setFont(fontOfPanel);
        lblName.setText(playerModel.getName());
        lblName.setBounds(11 , 11 , 100, 25);
        this.add(lblName);
    }

    private void initScoreLabel() {
        lblScore = new JLabel();
        lblScore.setName(ConstanteTestName.SCORE_NAME);
        lblScore.setFont(fontOfPanel);
        lblScore.setBounds(50, 50, 25, 25);
        this.add(lblScore);
    }

    @Override
    public void changementEtat() {
        lblScore.setText(Integer.toString(playerModel.getScore()));
        this.repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (playerModel.isActivated()) {
            g.setColor(Color.green);
            setBackground(new Color(176,224,230));
        }
        else {
            g.setColor(Color.darkGray);
            setBackground(null);
        }
        //g.drawRect(0,0, this.getWidth()- 10,this.getHeight() - 1);
        g.dispose();
    }
}
