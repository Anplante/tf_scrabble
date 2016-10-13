package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    private IDState stateSelected;
    boolean readyForNextPhase;

    private List<Tile> tilesSelected;

    public StateExchange(Player player) {
        super(player, IDState.EXCHANGE);
        stateSelected = IDState.EXCHANGE;
        readyForNextPhase = false;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {
        this.stateSelected = stateSelected;
        readyForNextPhase = true;
    }

    @Override
    protected void selectTile(Tile tile)
    {
        if(tilesSelected == null)
        {
            tilesSelected = new ArrayList<>();
        }
        tilesSelected.add(tile);
        tile.selectTile(true);
    }

    @Override
    protected void execute()
    {
        if(stateSelected != IDState.SELECT_ACTION)
        {
            if(tilesSelected!= null )
            {
                getGame().exchangeLetters(tilesSelected);
            }
            else {
                // solution temporaire
                JOptionPane.showMessageDialog(new Frame(),
                        "Aucune tuile n'a été sélectionné à supprimer",
                        "Action invalide",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected State getNextState() {

        State newState = null;

        switch (stateSelected)
        {
            case SELECT_ACTION:
                for(Tile tile : tilesSelected)
                {
                    tile.selectTile(false);
                }
                newState = new StateSelectAction(getPlayer());
                break;
            case EXCHANGE:
                newState = new StatePending(getPlayer());
                break;
        }
        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return readyForNextPhase;
    }



}
