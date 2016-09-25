package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 9/19/2016.
 */
public class StateSelectTile extends State{

    private Tile tileSelected;
    private IDState stateSelected;

    protected StateSelectTile(Player currentPlayer) {
        super(currentPlayer, IDState.SELECT_TILE);
    }

    @Override
    protected void execute()
    {

    }
    @Override
    protected void selectTile(Tile tileSelected)
    {
        this.tileSelected = tileSelected;
        stateSelected = IDState.PLAY_TILE;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {
        this.stateSelected = stateSelected;
    }

    @Override
    protected State getNextState() {

        State newState = null;

        switch (stateSelected)
       {
           case PLAY_TILE:
               newState = new StatePlayTile(getPlayer(),tileSelected);
               break;
           case PENDING:
               newState = new StatePending(getPlayer());
               break;
           case SELECT_ACTION:
               newState = new StateSelectAction(getPlayer());
               break;
       }
       return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return true;
    }
}
