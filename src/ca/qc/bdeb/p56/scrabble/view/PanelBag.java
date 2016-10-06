package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by 0993083 on 2016-10-06.
 */
public class PanelBag extends JPanel implements Observateur {

    private Image bagImg;
    private JLabel lblNumberLetter;
    private Game theGame;


    public PanelBag(Game game) {
        this.theGame = game;
        try {
            bagImg = ImageIO.read(this.getClass().getResource("/Image/bag_scrabble.png"));
        } catch (IOException ex) {
        }
        initComponents();
    }


    public void initComponents() {
        lblNumberLetter = new JLabel(new ImageIcon(bagImg.getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        lblNumberLetter.setSize(lblNumberLetter.getPreferredSize());
        lblNumberLetter.setText(Integer.toString(theGame.getlettersLeft()));
        lblNumberLetter.setForeground(Color.white);
        lblNumberLetter.setHorizontalTextPosition(JLabel.CENTER);
        //  lblNumberLetter.setVerticalTextPosition(JLabel.BOTTOM);
        lblNumberLetter.setVisible(true);
        // lblNumberLetter.setIcon(imageBag);
        this.add(lblNumberLetter);
    }

    @Override
    public void changementEtat() {
        lblNumberLetter.setText(Integer.toString(theGame.getlettersLeft()));
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
