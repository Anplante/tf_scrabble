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


    public DialogBlankTileChoice(ScrabbleGUI parent, Tile blankTile) {

        super();
        setLocationRelativeTo(parent);
        setSize(parent.getWidth()/4, parent.getHeight()/2);
        this.blankTile = blankTile;

        add(new JLabel(ConstanteComponentMessage.TITLE_SELECT_LETTER));
        initLettersChoice();

    }

    private void initLettersChoice() {
        JPanel pnlLettersChoice = new JPanel();
        pnlLettersChoice.setLayout(new GridBagLayout());

        int size = getWidth() / 10;

        for (char start = ConstanteComponentMessage.START_ALPHABET; start <= ConstanteComponentMessage.END_ALPHABET; start++) {
            String resource = ConstanteComponentMessage.RES_IMAGES_FR_BASIC + start + ConstanteComponentMessage.EXT_PNG;   // TODO Louis : avoir des images sans les valeurs de points
            URL res = getClass().getClassLoader().getResource(resource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
            JButton btnLetter = new JButton();
            btnLetter.setIcon(icon);
            btnLetter.setName(Character.toString(start));
            btnLetter.addActionListener(actionEvent -> {
                blankTile.setLetter(btnLetter.getName());
                dispose();
            });
            pnlLettersChoice.add(btnLetter);
        }

        add(pnlLettersChoice);
    }
}
