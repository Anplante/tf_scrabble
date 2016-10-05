package ca.qc.bdeb.p56.scrabble.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

/**
 * Created by 1468636 on 2016-10-04.
 */
public class MnuOptions extends JFrame {

    private JButton returnGame;
    private JButton abandon;
    private JLabel title;

    public MnuOptions(int x, int y) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(x-200, y-150,400 , 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setUndecorated(true);

        returnGame = new JButton("Retourner au jeu");
        abandon = new JButton("Abandonner");
        title = new JLabel("Menu");
        contentPane.setLayout(null);

        title.setBounds((getWidth()-200)/2,(getHeight()/2)-100,200,50);
        returnGame.setBounds((getWidth()-200)/2,(getHeight()/2)-50,200,50);
        abandon.setBounds((getWidth()-200)/2,(getHeight()/2)+50,200,50);
        returnGame.setVisible(true);
        contentPane.add(returnGame);
        contentPane.add(abandon);
        add(contentPane);

    /* important Statement */
    }
}
