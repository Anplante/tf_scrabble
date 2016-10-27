package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class PanelPlayerInfo extends JPanel implements Observateur, ConstanteTestName{


    private static final String TEXT_FONT = "Comic Sans MS Bold";

    private Player playerModel;
    private JLabel lblName;
    private JLabel lblTitre;
    private JLabel lblScore;
    private Color playerColor;

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
        lblScore.setName(SCORE_NAME);
        add(lblName);
        add(lblScore);

        Font font = new Font(TEXT_FONT, Font.PLAIN, 15);

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
        this.repaint();
        this.revalidate();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO Antoine : L'observateur devrait nous dire lequel des jouers est actif et inactif
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
