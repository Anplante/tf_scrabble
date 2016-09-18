package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class StateSelect extends State {

    private Letter letterSelected;
    private Object modeSelected;

    private boolean  readyToChange ;

    protected StateSelect(Player currentPlayer) {


        super(currentPlayer, IDState.SELECT);
        readyToChange  = false;
    }

    @Override
    protected void selectMode(Object modeSelected) {
       this.modeSelected = modeSelected;
    }

    @Override
    protected State getNextState() {

        State newState = null;

        if(modeSelected.getClass() == Letter.class)
        {
            newState = new StatePlayTile(getPlayer(), (Letter)modeSelected);
        }
        else
        {
            newState = this;
        }
        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return  true;
    }
}
