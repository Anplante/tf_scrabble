package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;
import sun.misc.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 0993083 on 2016-10-06.
 */
public class ButtonExchange extends JButton implements Observateur {


    private static final URL PATH_TO_FILE = Launcher.class.getResource("/images/bag_scrabble.png");

    private Image bagImg;
    private JLabel lblNumberLetter;
    private Game theGame;


    public ButtonExchange(String name, Game game, Rectangle bounds) {

        super(name);
        setBounds(bounds);
        this.theGame = game;

        try {
            bagImg = ImageIO.read(PATH_TO_FILE);
        } catch (IOException ex) {
            Logger.getLogger(PATH_TO_FILE.toString()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }


    private void initComponents() {

        setIcon(new ImageIcon(bagImg.getScaledInstance(getWidth() / 2, getHeight() / 2, Image.SCALE_SMOOTH)));
        lblNumberLetter = new JLabel();
        lblNumberLetter.setSize(lblNumberLetter.getPreferredSize());
        lblNumberLetter.setForeground(Color.WHITE);
        lblNumberLetter.setText(stringCreator());
        add(lblNumberLetter);
    }


    private String stringCreator() {

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < getWidth() / 2 / 5 - 1; i++) {
            text.append(" ");
        }
        text.append(theGame.getlettersLeft());
        return text.toString();
    }

    @Override
    public void changementEtat() {
        lblNumberLetter.setText(stringCreator());
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
    }
}
