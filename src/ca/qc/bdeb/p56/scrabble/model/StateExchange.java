package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;

/**
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    private IDState stateSelected;
    boolean readyForNextPhase;

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
    }

    @Override
    protected State getNextState() {

        State newState = null;

        switch (stateSelected)
        {
            case SELECT_ACTION:
                newState = new StateSelectAction(getPlayer());
                break;
            case PENDING:
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
