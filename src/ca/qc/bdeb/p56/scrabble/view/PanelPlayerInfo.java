package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Log.MoveLog;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.*;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import sun.misc.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;

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
    private JLabel lblScoreTitle;
    private JLabel lblCoupTitle;
    private JLabel lblCoupJoue;
    private JLabel lblPointsTitle;
    private JLabel lblPoints;

    public PanelPlayerInfo(Player player){

        playerModel = player;
        player.ajouterObservateur(this);

        initComponents();
        changementEtat();

    }

    private void initComponents() {
        this.setLayout(null);
        initNameLabel();
        initIconOfPlayer();
        initScoreLabel();
        initPlayedLabel();
        initPointsLabel();
    }

    private void initNameLabel() {
        lblName = new JLabel();
        lblName.setFont(fontOfPanel);
        lblName.setText(": " + playerModel.getName());
        lblName.setLocation(75, 25);
        lblName.setSize(lblName.getPreferredSize());
        this.add(lblName);
    }

    private void initScoreLabel() {

        lblScoreTitle = new JLabel();
        lblScoreTitle.setFont(fontOfPanel);
        lblScoreTitle.setText("Score : ");
        lblScoreTitle.setLocation(5, 75);
        lblScoreTitle.setSize(lblScoreTitle.getPreferredSize());

        lblScore = new JLabel();
        lblScore.setName(ConstanteTestName.SCORE_NAME);
        lblScore.setFont(fontOfPanel);
        lblScore.setLocation(lblScoreTitle.getWidth(),75);
        lblScore.setSize(lblScore.getPreferredSize());

        this.add(lblScoreTitle);
        this.add(lblScore);
    }

    private void initPlayedLabel() {
        lblCoupTitle = new JLabel();
        lblCoupTitle.setFont(fontOfPanel);
        lblCoupTitle.setText("Dernier coup joué : ");
        lblCoupTitle.setLocation(5, 100);
        lblCoupTitle.setSize(lblCoupTitle.getPreferredSize());

        lblCoupJoue = new JLabel();
        lblCoupJoue.setFont(fontOfPanel);
        lblCoupJoue.setText("------");
        lblCoupJoue.setLocation(lblCoupTitle.getWidth() + 10, 100);
        lblCoupJoue.setSize(lblCoupJoue.getPreferredSize());


        this.add(lblCoupTitle);
        this.add(lblCoupJoue);
    }

    private void initPointsLabel() {
        lblPointsTitle = new JLabel();
        lblPointsTitle.setFont(fontOfPanel);
        lblPointsTitle.setText("");
        lblPointsTitle.setLocation(lblCoupTitle.getWidth() + 10, 125);
        lblPointsTitle.setSize(lblPointsTitle.getPreferredSize());

        lblPoints = new JLabel();
        lblPoints.setFont(fontOfPanel);
        lblPoints.setText("");
        lblPoints.setLocation(lblCoupTitle.getWidth() + 55, 125);
        lblPoints.setSize(lblCoupJoue.getPreferredSize());


        this.add(lblPointsTitle);
        this.add(lblPoints);
    }

    private void initIconOfPlayer() {
        playerIcon = playerModel.getPlayerIcon();
    }


    @Override
    public void changementEtat() {
        lblScore.setText(Integer.toString(playerModel.getScore()));
        lblScore.setSize(lblScore.getPreferredSize());
        this.repaint();
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

        if (e.equals(Event.MOVE_PLAYED)) {


            MoveLog move = (MoveLog) o;

            if(move.getPlayer().equals(playerModel))
            {
                lblCoupJoue.setText(move.getMove());
                lblCoupJoue.setSize(lblCoupJoue.getPreferredSize());
                if (move.getMovePoints() != 0) {
                    lblPointsTitle.setText("pour ");
                    lblPointsTitle.setSize(lblPointsTitle.getPreferredSize());
                    lblPoints.setText(Integer.toString(move.getMovePoints()) + " points");
                    lblPoints.setSize(lblPoints.getPreferredSize());
                }
                else {
                    lblPoints.setText("");
                    lblPoints.setSize(lblPoints.getPreferredSize());
                    lblPointsTitle.setText("");
                    lblPointsTitle.setSize(lblPointsTitle.getPreferredSize());
                }
            }
        }
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
