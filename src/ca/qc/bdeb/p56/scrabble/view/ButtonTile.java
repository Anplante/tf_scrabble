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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class ButtonTile extends JButton implements Observateur{

    private static final Color BASIC_COLOR = Color.lightGray;
    private static final Color REMOVE_COLOR = Color.red;
    private static final Color SELECTED_COLOR = Color.green;
    private Game gameModel;
    private Tile tile;


    public ButtonTile(Game gameModel, Tile tile, ImageIcon icon) {

        super();

        this.gameModel = gameModel;
        this.tile = tile;
        changementEtat();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEtchedBorder());
        setIcon(icon);

        addActionListener(e -> gameModel.selectLetter(tile));


        setTransferHandler(new TransferHandler("Text"));

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e){

                JComponent lab = (JComponent)e.getSource();
                TransferHandler handle = lab.getTransferHandler();
                handle.exportAsDrag(lab, e, TransferHandler.MOVE);
            }
        });

    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public void changementEtat() {

        if (tile.isSelected()) {
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
