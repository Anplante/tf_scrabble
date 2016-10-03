package ca.qc.bdeb.p56.scrabble.view;

import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Tile;
import ca.qc.bdeb.p56.scrabble.shared.IDState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class BtnTile extends JButton {

    private  Color BASIC_COLOR = Color.lightGray;
    private  Color REMOVE_COLOR = Color.red;
    private  Color SELECTED_COLOR = Color.green;

    private Game gameModel;

    private Tile tile;


    public BtnTile(Game gameModel, Tile tile, Dimension dimension) {

        super("" + tile.getLetter());
        this.gameModel = gameModel;
        this.tile = tile;
        setSize(dimension);
        setBackground(BASIC_COLOR);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                gameModel.selectLetter(tile);
                if(gameModel.getActivePlayer().getState().getName() == IDState.EXCHANGE.getName()){
                    if(getBackground() == BASIC_COLOR) {
                        setExchangeColor();
                    }else{
                        setBasicColor();
                    }

                }else {

                    setBackground(SELECTED_COLOR);
                }

                // TODO Louis: avertir l'observateur du lettre qu'elle a été sélectionné si on veut éventuellement que ca fasse quelque chose
            }
        });
    }

    public void setSelectedColor(){
        setBackground(SELECTED_COLOR);
    }

    public void setExchangeColor(){
        setBackground(REMOVE_COLOR);
    }

    public void setBasicColor(){
        setBackground(BASIC_COLOR);
    }

    public Tile getTile(){
        return tile;
    }

    public void setTile(Tile aTile){
        this.tile = aTile;
        this.setText("" + aTile.getLetter());
    }
}
