package ca.qc.bdeb.p56.scrabble.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

/**
 * Created by 1468636 on 2016-10-04.
 */
public class DialogOptionsMenu extends JDialog {

    private JButton btnReturnGame;
    private JButton btnAbandon;
    private JLabel lblTitle;
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
        initTitle();
        initOptReturnGame();
        initOptAbandon();
        initOptQuitGame();
        addKeybindings();
    }

    private void initTitle() {

        lblTitle = new JLabel("Menu", SwingConstants.CENTER);
        lblTitle.setFont(new Font(lblTitle.getName(), Font.BOLD, 30));
        add(lblTitle);
    }

    private void initOptQuitGame() {

        btnQuitGame = new JButton("Quitter");
        add(btnQuitGame);

        btnQuitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = JOptionPane.showConfirmDialog(new Frame(), "Voulez-vous vraiment continuer?",
                        "Fermeture de l'application", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (i == 0) {
                    System.exit(0);
                }
            }
        });
    }

    private void initOptAbandon() {

        btnAbandon = new JButton("Abandonner");
        btnAbandon.setName("Abandon");
        add(btnAbandon);

        btnAbandon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = JOptionPane.showConfirmDialog(new Frame(), "Voulez-vous vraiment continuer?",
                        "Abandon de partie", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, "Vous avez perdu.",
                            "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
                    parent.returnToMenu();
                    dispose();
                }
            }
        });
    }

    private void initOptReturnGame() {
        btnReturnGame = new JButton("Retourner au jeu");
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

        JRootPane contentPane = (JRootPane) getRootPane();
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        contentPane.getActionMap().put("Escape", actionEscape);
    }

    private final AbstractAction actionEscape = new AbstractAction("Escape") {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };
}
