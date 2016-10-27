package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class ButtonTile extends JButton implements Observateur {

    private final String PATH_RES_LETTERS_VALUE = "./letters/englishDictionaryValue/";

    private final Color BASIC_COLOR = Color.lightGray;
    private final Color REMOVE_COLOR = Color.red;
    private final Color SELECTED_COLOR = Color.green;
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

        if (tile != null) {
            String ressource = PATH_RES_LETTERS_VALUE + tile.getLetter().toUpperCase().trim() + BtnSquare.EXT_PNG;
            ImageIcon fillingIcon = new ImageIcon(getClass().getClassLoader().getResource(ressource));
            Image img = fillingIcon.getImage();
            Image newimg = img.getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(newimg);
            setIcon(icon);
        }

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameModel.selectLetter(tile);
            }
        });
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile aTile) {

        this.tile = aTile;
        if (tile != null) {
            ImageIcon fillingIcon = new ImageIcon(getClass().getClassLoader().getResource(PATH_RES_LETTERS_VALUE
                    + tile.getLetter().toUpperCase() + BtnSquare.EXT_PNG));
            Image img = fillingIcon.getImage();
            Image newimg = img.getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(newimg);
            setIcon(icon);
        }
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
