package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class StateSelectAction extends State {

    private Tile tileSelected;
    private Object modeSelected;
    private IDState stateSelected;

    private boolean  readyToChange ;

    protected StateSelectAction(Player currentPlayer) {


        super(currentPlayer, IDState.SELECT_ACTION);
        readyToChange  = false;
        stateSelected = IDState.SELECT_ACTION;
    }

    @Override
    protected  void selectTile(Tile tileSelected)
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

        State newState;

        // TODO Louis : Faire les vérifications pour savoir si le joueur peut passer au prochain état et non simplement se laisser dire quel état aller
        switch (stateSelected)
        {
            case PLAY_TILE:
                newState = new StatePlayTile(getPlayer(), tileSelected);
                break;
            case PENDING:
                newState = new StatePending(getPlayer());
                break;
            default:
                newState = this;
        }
        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return  true;
    }
}
