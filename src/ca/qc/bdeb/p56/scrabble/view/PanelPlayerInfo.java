package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class PanelPlayerInfo extends JPanel implements Observateur{


    private static final String TEXT_FONT = "Comic Sans MS Bold";
    private static final Font fontOfPanel = new Font(TEXT_FONT, Font.PLAIN, 15);

    private Player playerModel;
    private JLabel lblName;
    private JLabel lblScore;
    private BufferedImage playerIcon;

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
        initIconOfPlayer();
    }

    private void initNameLabel() {
        lblName = new JLabel();
        lblName.setFont(fontOfPanel);
        lblName.setText(playerModel.getName());
        lblName.setBounds(12 , 61 , 150, 25);
        this.add(lblName);
    }

    private void initScoreLabel() {
        lblScore = new JLabel();
        lblScore.setName(ConstanteTestName.SCORE_NAME);
        lblScore.setFont(fontOfPanel);
        lblScore.setBounds(100, 31, 25, 25);
        this.add(lblScore);
    }

    private void initIconOfPlayer() {
        playerIcon = playerModel.getPlayerIcon();
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
        if(playerModel.isEliminated())
        {
            setBackground(new Color(230, 0, 20));
        }

        else if (playerModel.isActivated()) {
            g.setColor(Color.green);
            setBackground(new Color(176,224,230));
        }
        else {
            g.setColor(Color.darkGray);
            setBackground(null);
        }
        g.drawImage(playerIcon, 11, 11, 50,50,this);
        g.dispose();
    }
}
