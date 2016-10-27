package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.ConstanteComponentMessage;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class ButtonTile extends JButton implements Observateur, ConstanteComponentMessage {

    private static final String PATH_RES_LETTERS_VALUE = "./letters/englishDictionaryValue/";

    private static final Color BASIC_COLOR = Color.lightGray;
    private static final Color REMOVE_COLOR = Color.red;
    private static final Color SELECTED_COLOR = Color.green;
    private Game gameModel;
    private Tile tile;


    public ButtonTile(Game gameModel, Tile tile, Dimension dimension) {

        super();

        this.gameModel = gameModel;
        this.tile = tile;
        setSize(dimension);
        changementEtat();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        setImage();

        addActionListener(e -> gameModel.selectLetter(tile));
    }


    private void setImage(){

        String ressource = PATH_RES_LETTERS_VALUE + tile.getLetter().toUpperCase().trim() + EXT_PNG;
        ImageIcon fillingIcon = new ImageIcon(getClass().getClassLoader().getResource(ressource));
        Image img = fillingIcon.getImage();
        Image newimg = img.getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(newimg);
        setIcon(icon);
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public void changementEtat() {

        if (tile != null && tile.isSelected()) {

            if (gameModel.getActivePlayer().getState().getName() == IDState.EXCHANGE.getName()) {
                setBackground(REMOVE_COLOR);
            } else {
                setBackground(SELECTED_COLOR);
            }
        } else {
            setBackground(BASIC_COLOR);
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
