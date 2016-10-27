package ca.qc.bdeb.p56.scrabble.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

/**
 * Classe permettant de créer un dialogue affichant les options de la partie. Le joueur peut décider de revenir à la
 * partie, d'abandonner ou de quitter le jeu.
 *
 * Created by Julien brosseau on 2016-10-04.
 */
public class DialogOptionsMenu extends JDialog {

    private static final String OPT_QUITTER_TITLE = "Quitter";
    private static final String MESS_CONFIRM_EXIT = "Voulez-vous vraiment quitter?";
    private static final String TITLE_MESS_EXIT = "Fermeture de l'application";
    private static final String TITLE_SURRENDER = "Abandonner";
    private static final String MESS_CONFIRM_ACTION = "Voulez-vous continuer?";
    private static final String MESS_SURRENDER_GAME = "Abandon de partie";
    private static final String MESS_DEFEAT = "Vous avez perdu";
    private static final String MESS_END_GAME = "Fin de la partie";
    private static final String TITLE_RETURN_GAME = "Retourner au jeu";
    private static final String ESCAPE_KEY = "Escape";

    private JButton btnReturnGame;
    private JButton btnAbandon;
    private JButton btnQuitGame;
    private ScrabbleGUI parent;

    public DialogOptionsMenu(ScrabbleGUI parent) {

        super();

        this.parent = parent;
        setSize(parent.getWidth() / 4, parent.getHeight() / 2);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 10, 10));
        iniComponents();
        setModal(true);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void iniComponents() {
        initOptReturnGame();
        initOptAbandon();
        initOptQuitGame();
        addKeybindings();
    }

    private void initOptQuitGame() {

        btnQuitGame = new JButton(OPT_QUITTER_TITLE);
        add(btnQuitGame);

        btnQuitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = JOptionPane.showConfirmDialog(new Frame(), MESS_CONFIRM_EXIT,
                       TITLE_MESS_EXIT, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (i == 0) {
                    System.exit(0);
                }
            }
        });
    }

    private void initOptAbandon() {

        btnAbandon = new JButton(TITLE_SURRENDER);
        btnAbandon.setName("Abandon");
        add(btnAbandon);

        btnAbandon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = JOptionPane.showConfirmDialog(new Frame(), MESS_CONFIRM_ACTION,
                        MESS_SURRENDER_GAME, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, MESS_DEFEAT,
                           MESS_END_GAME, JOptionPane.INFORMATION_MESSAGE);
                    parent.returnToMenu();
                    dispose();
                }
            }
        });
    }

    private void initOptReturnGame() {
        btnReturnGame = new JButton(TITLE_RETURN_GAME);
        btnReturnGame.setName("Return");
        add(btnReturnGame);

        btnReturnGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    private void addKeybindings() {

        JRootPane contentPane = getRootPane();
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),ESCAPE_KEY );
        contentPane.getActionMap().put(ESCAPE_KEY , actionEscape);
    }

    private final AbstractAction actionEscape = new AbstractAction(ESCAPE_KEY ) {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };
}
