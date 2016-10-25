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
public class ButtonExchange extends JButton implements Observateur {

    private Image bagImg;
    private JLabel lblNumberLetter;
    private Game theGame;


    public ButtonExchange(String name, Game game, Rectangle bounds) {

        super(name);
        setBounds(bounds);
        this.theGame = game;

        try {
            bagImg = ImageIO.read(this.getClass().getResource("/Image/bag_scrabble.png"));
        } catch (IOException ex) {
            // FIXME Antoine
            ex.printStackTrace();
        }
        initComponents();
    }


    public void initComponents() {

        setIcon(new ImageIcon(bagImg.getScaledInstance(getWidth()/2, getHeight()/2, Image.SCALE_SMOOTH)));

       lblNumberLetter = new JLabel();
        //setLayout(null);
        lblNumberLetter.setSize(lblNumberLetter.getPreferredSize());

        lblNumberLetter.setForeground(Color.WHITE);
        //lblNumberLetter.setHorizontalTextPosition(JLabel.CENTER);
        //  lblNumberLetter.setVerticalTextPosition(JLabel.BOTTOM);
        //lblNumberLetter.setVisible(true);
        // lblNumberLetter.setIcon(imageBag);
        lblNumberLetter.setLocation(getWidth()/2, getHeight()/2);
        lblNumberLetter.setText(stringCreator());
        add(lblNumberLetter);
    }


    private String stringCreator(){
        StringBuffer text = new StringBuffer();
        for (int i = 0; i <getWidth()/2/5-1 ; i++) {
            text.append(" ");
        }
        text.append(theGame.getlettersLeft());
        return text.toString();
    }

    @Override
    public void changementEtat() {

        lblNumberLetter.setText(Integer.toString(theGame.getlettersLeft()));
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
