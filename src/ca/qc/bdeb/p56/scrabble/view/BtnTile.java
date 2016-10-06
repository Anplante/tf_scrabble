package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.StatePlayTile;
import ca.qc.bdeb.p56.scrabble.model.StateSwapTile;
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
public class BtnTile extends JButton implements Observateur {

    private Color BASIC_COLOR = Color.lightGray;
    private Color REMOVE_COLOR = Color.red;
    private Color SELECTED_COLOR = Color.green;

    private Game gameModel;

    private Tile tile;


    public BtnTile(Game gameModel, Tile tile, Dimension dimension) {
        super(tile.getLetter() + " (" + tile.getValue() + ")");
        if (tile.getLetter().equals(" ")) {
            this.setText("     ");
        }
        this.gameModel = gameModel;
        this.tile = tile;
        setSize(dimension);
        changementEtat();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFocusable(false);


        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // gameModel.selectLetter(tile);
                if (gameModel.getActivePlayer().getState().getName() == IDState.EXCHANGE.getName()) {
                    if (getBackground() == BASIC_COLOR) {
                        setExchangeColor();
                    } else {
                        setBasicColor();
                    }
                } else if ((gameModel.getActivePlayer().getState().getName() == IDState.PLAY_TILE.getName())
                        && gameModel.getActivePlayer().getHasTile() == true) {
                    StatePlayTile state = (StatePlayTile)gameModel.getActivePlayer().getState();
                    state.setBackupTile(getTile());

                    gameModel.getActivePlayer().selectNextState(IDState.SWAP_TILE);
                    gameModel.getActivePlayer().nextState();
                } else {
                    if(tile.getSelected()) {
                        tile.setSelected(false);
                        setBasicColor();
                    }else {
                        gameModel.getActivePlayer().setHasTile(true);
                        tile.setSelected(true);
                        setSelectedColor();
                        gameModel.selectLetter(tile);
                    }
                }
                // TODO Louis: avertir l'observateur du lettre qu'elle a été sélectionné si on veut éventuellement que ca fasse quelque chose
            }
        });
    }


    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile aTile) {
        this.tile = aTile;
        this.setText("" + aTile.getLetter());
    }

    @Override
    public void changementEtat() {

        if(tile.isSelected())
        {
            if (gameModel.getActivePlayer().getState().getName() == IDState.EXCHANGE.getName()) {
                setBackground(REMOVE_COLOR);
            }
            else {
                setBackground(SELECTED_COLOR);
            }
        }
        else{
            setBackground(BASIC_COLOR);
        }
    }

    @Override
    public void changementEtat(Enum<?> e, Object o) {

    }
}
