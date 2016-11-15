package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteTestName;
import ca.qc.bdeb.p56.scrabble.utility.ImagesManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by TheFrenchOne on 11/13/2016.
 */
public class DialogBlankTileChoice extends JDialog {

    private ScrabbleGUI parent;

    public DialogBlankTileChoice(ScrabbleGUI parent)
    {
        super();

        this.parent = parent;
        setSize(parent.getWidth() / 4, parent.getHeight() / 2);
        setLocationRelativeTo(parent);

        add(new JLabel(ConstanteComponentMessage.TITLE_SELECT_LETTER));


    }

    private void initLettersChoice()
    {
        JPanel pnlLettersChoice = new JPanel();
        pnlLettersChoice.setLayout(new GridBagLayout());

        int size = getWidth()/10;

        for (char start = ConstanteComponentMessage.START_ALPHABET; start <=  ConstanteComponentMessage.END_ALPHABET; start++) {
            String ressource = parent.getImgPath() + start + ConstanteComponentMessage.EXT_PNG;
            URL res = getClass().getClassLoader().getResource(ressource);
            ImageIcon icon = ImagesManager.getIcon(res, size, size);
           //ButtonTile btnTile = new ButtonTile()
        }
    }
}