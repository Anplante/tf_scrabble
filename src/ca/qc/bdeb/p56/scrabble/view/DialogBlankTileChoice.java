package ca.qc.bdeb.p56.scrabble.view;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Fenêtre permettant de demander à l'utilisateur la valeur qu'il désire pour une tile vide.
 *
 * Created by Louis Luu Lim on 11/13/2016.
 */
class DialogBlankTileChoice extends JDialog {

    private Tile blankTile;
    private final ResourceBundle messages = ResourceBundle.getBundle("strings", Locale.getDefault());

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

    private void initLettersChoice(String resFolder) {


        // TODO LOUIS Refactor
        JPanel pnlLettersChoice = new JPanel();
        pnlLettersChoice.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = gridConstraints.gridy = 0;
        int size = getWidth() / 10;
        Dimension prefSize = new Dimension(size, size);
        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET - 2; start++) {
            String resource = resFolder + getBlanksPath(resFolder)  +start + ConstanteComponentMessage.EXT_PNG;
            URL res = getClass().getClassLoader().getResource(resource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
            JButton btnLetter = new JButton();
            btnLetter.setPreferredSize(prefSize);
            btnLetter.setMinimumSize(prefSize);
            btnLetter.setIcon(icon);
            btnLetter.setName(Character.toString(start));
            btnLetter.addActionListener(actionEvent -> {
                blankTile.setLetter(btnLetter.getName());
                dispose();
            });
            pnlLettersChoice.add(btnLetter, gridConstraints);

            gridConstraints.gridx++;
            if(gridConstraints.gridx == 6)
            {
                gridConstraints.gridx = 0;
                gridConstraints.gridy++;
            }
        }

        gridConstraints.gridx = 2;
        for(char start = ConstanteComponentMessage.END_ALPHABET - 1; start <= ConstanteComponentMessage.END_ALPHABET ;start++){
            String resource = resFolder +  getBlanksPath(resFolder) + start + ConstanteComponentMessage.EXT_PNG;
            URL res = getClass().getClassLoader().getResource(resource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
            JButton btnLetter = new JButton();
            btnLetter.setPreferredSize(prefSize);
            btnLetter.setMinimumSize(prefSize);
            btnLetter.setIcon(icon);
            btnLetter.setName(Character.toString(start));
            btnLetter.addActionListener(actionEvent -> {
                blankTile.setLetter(btnLetter.getName());
                dispose();
            });

            pnlLettersChoice.add(btnLetter, gridConstraints);
            gridConstraints.gridx++;
        }

        prefSize = new Dimension(size*2, size);
        gridConstraints.gridx = 2;
        gridConstraints.gridy++;
        gridConstraints.gridwidth = 2;
        JButton btnCancel = new JButton("Annuler");
        btnCancel.setPreferredSize(prefSize);
        btnCancel.setMinimumSize(prefSize);
        btnCancel.addActionListener(actionEvent -> {
            dispose();
        });

        pnlLettersChoice.add(btnCancel, gridConstraints);
        add(pnlLettersChoice);
    }
    private String getBlanksPath(String themePath){
        if(themePath.contains("noble")){
            return ConstanteComponentMessage.RES_BLANKS_NOBLE;
        }else {
            return ConstanteComponentMessage.RES_BLANKS_BASIC;
        }
    }
}
