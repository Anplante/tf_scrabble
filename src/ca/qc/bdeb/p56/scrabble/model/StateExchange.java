package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe qui représente la phase d'échange de lettres. Le joueur peut sélectionner les lettres qu'il veut échanger ou
 * annuler l'échange.
 *
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    private IDState stateSelected;
    private boolean readyForNextPhase;
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
        if(tile.isSelected()){
            tile.selectTile(false);
            tilesSelected.remove(tile);
            getGame().aviserObservateurs();
        }else {
            tilesSelected.add(tile);
            tile.selectTile(true);
        }
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
                // TODO Louis : solution temporaire pour montrer l'erreur. Il faudrait s'entendre sur comment on veut afficher des messages d'erreur
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
                if(tilesSelected != null) {
                    for (Tile tile : tilesSelected) {
                        tile.selectTile(false);
                    }
                    newState = new StateSelectAction(getPlayer());
                } else{
                    newState = new StateSelectAction(getPlayer());
                }
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
