package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Observer;

/**
 * Created by 1468636 on 2016-10-04.
 */
public class MnuOptions extends JPanel implements Observateur {

    private JButton returnGame;
    private JButton abandon;
    private JLabel title;
    private JButton quitter;
    private Game gameModel;

    public MnuOptions(int x, int y, Game game) {
        setBounds(x-200, y-150,400 , 300);
        gameModel = game;

        returnGame = new JButton("Retourner au jeu");
        abandon = new JButton("Abandonner");
        title = new JLabel("       Menu");
        quitter = new JButton("Quitter");
        title.setFont(new Font(title.getName(), Font.BOLD, 30));

        setLayout(null);
        title.setBounds((getWidth()-200)/2,(getHeight()/2)-140,200,50);
        returnGame.setBounds((getWidth()-200)/2,(getHeight()/2)-75,200,50);
        abandon.setBounds((getWidth()-200)/2,(getHeight()/2),200,50);
        quitter.setBounds((getWidth()-200)/2,(getHeight()/2)+75,200,50);

        add(title);
        add(returnGame);
        add(abandon);
        add(quitter);

        returnGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameModel.setIsInMenu(false);
                gameModel.aviserObservateurs();
            }
        });

        abandon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });


    /* important Statement */
    }

    @Override
    public void changementEtat() {

    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
