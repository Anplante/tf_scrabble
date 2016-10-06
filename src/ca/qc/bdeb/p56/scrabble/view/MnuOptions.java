package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Observer;

/**
 * Created by 1468636 on 2016-10-04.
 */
public class MnuOptions extends JDialog implements KeyListener {

    private JButton returnGame;
    private JButton abandon;
    private JLabel title;
    private JButton quitter;
    private ScrabbleGUI parent;

    public MnuOptions(ScrabbleGUI parent) {

        setBounds(parent.getWidth()/2-200, parent.getHeight()/2-150,400 , 300);

        this.parent = parent;
        returnGame = new JButton("Retourner au jeu");
        returnGame.setName("Return");
        abandon = new JButton("Abandonner");
        abandon.setName("Abandon");
        title = new JLabel("       Menu");
        quitter = new JButton("Quitter");
        title.setFont(new Font(title.getName(), Font.BOLD, 30));

        setLayout(null);

        title.setBounds((getWidth()-200)/2,(getHeight()/2)-140,200,50);
        returnGame.setBounds((getWidth()-200)/2,(getHeight()/2)-75,200,50);
        abandon.setBounds((getWidth()-200)/2,(getHeight()/2),200,50);
        quitter.setBounds((getWidth()-200)/2,(getHeight()/2)+75,200,50);
        abandon.setFocusable(false);
        quitter.setFocusable(false);
        setModal(true);
        add(title);
        add(returnGame);
        add(abandon);
        add(quitter);
        addKeyListener(this);
        setFocusable(true);


        returnGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        abandon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = JOptionPane.showConfirmDialog(new Frame(), "Voulez-vous vraiment continuer?",
                        "Abandon de partie",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE );
                if(i == 0){
                    JOptionPane.showMessageDialog(null,"Vous avez perdu.",
                            "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
                    parent.returnToMenu();
                    dispose();
                }
            }
        });

        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = JOptionPane.showConfirmDialog(new Frame(), "Voulez-vous vraiment continuer?",
                        "Fermeture de l'application",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE );
                if(i == 0) {
                    System.exit(0);
                }
            }
        });


    /* important Statement */
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
