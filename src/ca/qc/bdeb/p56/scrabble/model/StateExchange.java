package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente la phase d'échange de lettres. Le joueur peut sélectionner les lettres qu'il veut échanger ou
 * annuler l'échange.
 * <p>
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    private IDState stateSelected;
    private boolean readyForNextPhase;
    private List<Tile> tilesSelected;

    public StateExchange(Player player) {
        super(player, IDState.EXCHANGE);
        stateSelected = IDState.EXCHANGE;
        tilesSelected = new ArrayList<>();
        readyForNextPhase = false;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {
        this.stateSelected = stateSelected;

        switch (stateSelected) {
            case EXCHANGE:
                readyForNextPhase = checkCanExchange();
                break;
            default:
                readyForNextPhase = true;
                break;
        }
    }


    @Override
    protected void selectTile(Tile tile) {

        if (tile.isSelected()) {
            tile.selectTile();
            tilesSelected.remove(tile);
            getGame().aviserObservateurs();
        } else {
            tilesSelected.add(tile);
            tile.selectTile();
        }
    }

    private boolean checkCanExchange() {
        return !tilesSelected.isEmpty();
    }

    private void cancelExchange() {

        for (Tile tile : tilesSelected) {
            tile.selectTile();
        }
    }

    @Override
    protected State getNextState() {

        State newState = null;

        switch (stateSelected) {
            case SELECT_ACTION:
                cancelExchange();
                newState = new StateSelectAction(getPlayer());
                break;
            case EXCHANGE:
                getGame().exchangeLetters(tilesSelected);
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
