package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Classe permettant de créer un dialogue affichant les options de la partie. Le joueur peut décider de revenir à la
 * partie, d'abandonner ou de quitter le jeu.
 *
 * Created by Julien brosseau on 2016-10-04.
 */
public class DialogOptionsMenu extends JDialog {

    private final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());
    private JButton btnReturnGame;
    private JButton btnQuitGame;

    public DialogOptionsMenu(ScrabbleGUI parent) {

        super();

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
        initOptQuitGame();
        addKeybindings();
    }

    private void initOptQuitGame() {

        btnQuitGame = new JButton( messages.getString("Quit"));
        btnQuitGame.setName(ConstanteTestName.EXIT_NAME);
        add(btnQuitGame);

        btnQuitGame.addActionListener(actionEvent -> {
            int i = JOptionPane.showConfirmDialog(new Frame(),  messages.getString("Confirmation_Quit"),
                    messages.getString("App_Closure"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (i == 0) {
                System.exit(0);
            }
        });
    }

    private void initOptReturnGame() {

        btnReturnGame = new JButton( messages.getString("Return"));
        btnReturnGame.setName(ConstanteTestName.RETURN_NAME);
        add(btnReturnGame);
        btnReturnGame.addActionListener(actionEvent -> dispose());
    }

    private void addKeybindings() {

        JRootPane contentPane = getRootPane();
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),  messages.getString("Escape_Key"));
        contentPane.getActionMap().put( messages.getString("Escape_Key") , actionEscape);
    }

    private final AbstractAction actionEscape = new AbstractAction( messages.getString("Escape_Key") ) {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };


}
