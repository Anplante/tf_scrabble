package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Log.MoveLog;
import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class PanelPlayerInfo extends JPanel implements Observateur{

    private final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());
    private static final Font ROBOTO_FONT = loadFont("Roboto-Bold.ttf").deriveFont(Font.PLAIN, 20);
    private static final String TEXT_FONT = "Comic Sans MS Bold";
    private static final Font fontOfPanel = new Font(TEXT_FONT, Font.PLAIN, 18);

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
        lblName.setFont(ROBOTO_FONT);
        lblName.setText(": " + playerModel.getName());
        lblName.setLocation(125, 75);
        lblName.setSize(lblName.getPreferredSize());
        this.add(lblName);
    }

    private void initScoreLabel() {

        lblScoreTitle = new JLabel();
        lblScoreTitle.setFont(ROBOTO_FONT);
        lblScoreTitle.setText(messages.getString("Score") + " ");
        lblScoreTitle.setLocation(5, 125);
        lblScoreTitle.setSize(lblScoreTitle.getPreferredSize());

        lblScore = new JLabel();
        lblScore.setName(ConstanteTestName.SCORE_NAME);
        lblScore.setFont(ROBOTO_FONT);
        lblScore.setLocation(lblScoreTitle.getWidth(),125);
        lblScore.setSize(lblScore.getPreferredSize());

        this.add(lblScoreTitle);
        this.add(lblScore);
    }

    private void initPlayedLabel() {
        lblCoupTitle = new JLabel();
        lblCoupTitle.setFont(ROBOTO_FONT);
        lblCoupTitle.setText(messages.getString("Last_Move"));
        lblCoupTitle.setLocation(5, 150);
        lblCoupTitle.setSize(lblCoupTitle.getPreferredSize());

        lblCoupJoue = new JLabel();
        lblCoupJoue.setFont(ROBOTO_FONT);
        lblCoupJoue.setText("------");
        lblCoupJoue.setLocation(lblCoupTitle.getWidth() + 10, 150);
        lblCoupJoue.setSize(lblCoupJoue.getPreferredSize());


        this.add(lblCoupTitle);
        this.add(lblCoupJoue);
    }

    private void initPointsLabel() {
        lblPointsTitle = new JLabel();
        lblPointsTitle.setFont(ROBOTO_FONT);
        lblPointsTitle.setText("");
        lblPointsTitle.setLocation(lblCoupTitle.getWidth() + 10, 175);
        lblPointsTitle.setSize(lblPointsTitle.getPreferredSize());

        lblPoints = new JLabel();
        lblPoints.setFont(ROBOTO_FONT);
        lblPoints.setText("");
        lblPoints.setLocation(lblCoupTitle.getWidth() + 55, 175);
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
                    lblPointsTitle.setText(messages.getString("For"));
                    lblPointsTitle.setSize(lblPointsTitle.getPreferredSize());
                    lblPoints.setText(Integer.toString(move.getMovePoints()) + " " + messages.getString("Points_Space"));
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
        g.drawImage(playerIcon, 11, 11, 100,100,this);
        g.dispose();
    }

    private static Font loadFont(String resourceName) {
        try (InputStream inputStream = PanelSearchBar.class.getResourceAsStream("/fonts/" + resourceName)) {
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("Could not load " + resourceName, e);
        }
    }
}
