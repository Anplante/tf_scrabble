package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Square;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by TheFrenchOne on 11/13/2016.
 */
public class DialogBlankTileChoice extends JDialog {

    private Tile blankTile;

    public DialogBlankTileChoice(Tile blankTile) {
        super();

        this.blankTile = blankTile;

        add(new JLabel(ConstanteComponentMessage.TITLE_SELECT_LETTER));

    }

    private void initLettersChoice() {
        JPanel pnlLettersChoice = new JPanel();
        pnlLettersChoice.setLayout(new GridBagLayout());

        int size = getWidth() / 10;

        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET; start++) {
            String ressource = ConstanteComponentMessage.DEFAULT_DICT_PATH + start + ConstanteComponentMessage.EXT_PNG;
            URL res = getClass().getClassLoader().getResource(ressource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
            JButton btnLetter = new JButton();
            btnLetter.setIcon(icon);
            btnLetter.setName(Character.toString(start));
            btnLetter.addActionListener(actionEvent -> {
                blankTile.setLetter(btnLetter.getName());
            });
            pnlLettersChoice.add(btnLetter);
        }

        add(pnlLettersChoice);
    }
}
