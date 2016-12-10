package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Fenêtre permettant de demander à l'utilisateur la valeur qu'il désire pour une tile vide.
 * <p>
 * Created by Louis Luu Lim on 11/13/2016.
 */
class DialogBlankTileChoice extends JDialog {

    private Tile blankTile;
    private JPanel pnlLettersChoice;
    private final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());
    private int prefSize;
    private Dimension prefDimension;

    protected DialogBlankTileChoice(ScrabbleGUI parent, Tile blankTile, String theme) {

        super();
        setSize(parent.getWidth() / 4, parent.getHeight() / 2);
        this.blankTile = blankTile;

        add(new JLabel(messages.getString("Select_Letter")));
        initLettersChoice(theme);

        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);

    }


    private JButton createBtnLetter(String resFolder, char letter) {


        String resource = resFolder + getBlanksPath(resFolder) + letter + ConstanteComponentMessage.EXT_PNG;
        URL res = getClass().getClassLoader().getResource(resource);
        ImageIcon icon = ImagesManager.getIcon(res, prefSize, prefSize);
        JButton btnLetter = new JButton();
        btnLetter.setPreferredSize(prefDimension);
        btnLetter.setMinimumSize(prefDimension);
        btnLetter.setIcon(icon);
        btnLetter.setName(Character.toString(letter));
        btnLetter.addActionListener(actionEvent -> {
            blankTile.setLetter(btnLetter.getName());
            dispose();
        });
        return btnLetter;
    }


    private void initLettersChoice(String resFolder) {

        pnlLettersChoice = new JPanel();
        pnlLettersChoice.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;

        prefSize = getWidth() / 10;
        prefDimension = new Dimension(prefSize, prefSize);

        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET - 2; start++) {

            JButton btnLetter = createBtnLetter(resFolder, start);
            pnlLettersChoice.add(btnLetter, gridConstraints);

            gridConstraints.gridx++;
            if (gridConstraints.gridx == 6) {
                gridConstraints.gridx = 0;
                gridConstraints.gridy++;
            }
        }

        gridConstraints.gridx = 2;

        for (char start = ConstanteComponentMessage.END_ALPHABET - 1; start <= ConstanteComponentMessage.END_ALPHABET; start++) {
            String resource = resFolder + getBlanksPath(resFolder) + start + ConstanteComponentMessage.EXT_PNG;
            JButton btnLetter = createBtnLetter(resFolder, start);
            pnlLettersChoice.add(btnLetter, gridConstraints);
            gridConstraints.gridx++;
        }

        initCancelOption();
        add(pnlLettersChoice);
    }

    private void initCancelOption() {

        prefDimension = new Dimension(prefSize * 2, prefSize);
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 6;
        gridConstraints.gridwidth = 2;

        JButton btnCancel = new JButton("Annuler");
        btnCancel.setName(ConstanteTestName.CANCEL_NAME);
        btnCancel.setPreferredSize(prefDimension);
        btnCancel.setMinimumSize(prefDimension);
        btnCancel.addActionListener(actionEvent -> {
            dispose();
        });

        pnlLettersChoice.add(btnCancel, gridConstraints);
    }

    private String getBlanksPath(String themePath) {
        if (themePath.contains("noble")) {
            return ConstanteComponentMessage.RES_BLANKS_NOBLE;
        } else {
            return ConstanteComponentMessage.RES_BLANKS_BASIC;
        }
    }
}
