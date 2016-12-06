package ca.qc.bdeb.p56.scrabble.view;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;
import ca.qc.bdeb.p56.scrabble.shared.Theme;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Fenêtre permettant de demander à l'utilisateur la valeur qu'il désire pour une tile vide.
 *
 * Created by Louis Luu Lim on 11/13/2016.
 */
class DialogBlankTileChoice extends JDialog {

    private Tile blankTile;


    protected DialogBlankTileChoice(ScrabbleGUI parent, Tile blankTile, Theme theme) {

        super();
        setSize(parent.getWidth() / 4, parent.getHeight() / 2);
        this.blankTile = blankTile;

        add(new JLabel(ConstanteComponentMessage.TITLE_SELECT_LETTER));
        initLettersChoice(theme.getThemeFolderPath());

        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);

    }

    private void initLettersChoice(String resFolder) {


        // TODO LOUIS Refactor
        JPanel pnlLettersChoice = new JPanel();
        pnlLettersChoice.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        int size = getWidth() / 10;
        Dimension prefSize = new Dimension(size, size);
        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET - 2; start++) {
            String resource = resFolder + start + ConstanteComponentMessage.EXT_PNG;
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
            pnlLettersChoice.add(btnLetter, gbc);

            gbc.gridx++;
            if(gbc.gridx == 6)
            {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }

        gbc.gridx = 2;
        for(char start = ConstanteComponentMessage.END_ALPHABET - 1; start <= ConstanteComponentMessage.END_ALPHABET ;start++){
            String resource = resFolder + start + ConstanteComponentMessage.EXT_PNG;
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

            pnlLettersChoice.add(btnLetter, gbc);
            gbc.gridx++;
        }

        prefSize = new Dimension(size*2, size);
        gbc.gridx = 2;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnCancel = new JButton("Annuler");
        btnCancel.setPreferredSize(prefSize);
        btnCancel.setMinimumSize(prefSize);
        btnCancel.addActionListener(actionEvent -> {
            dispose();
        });

        pnlLettersChoice.add(btnCancel, gbc);
        add(pnlLettersChoice);
    }
}
