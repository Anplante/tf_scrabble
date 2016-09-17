package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.Shared.IDState;

import java.util.Objects;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class StateSelect extends State {

    private Letter letterSelected;
    private Object modeSelected;

    private boolean tileSelected;

    protected StateSelect(Player currentPlayer) {


        super(currentPlayer, IDState.SELECT);
        tileSelected = false;
    }

    @Override
    protected void selectMode(Object modeSelected) {
       this.modeSelected = modeSelected;
    }

  /*  @Override
    protected void playTile(Square square) {

        if(letterSelected != null)
        {
            square.setLetter(letterSelected);
            getPlayer().remove(letterSelected);
            getPlayer().aviserObservateurs();
        }

        tileSelected = true;
    }*/

    @Override
    protected State getNextState() {

        State newState = null;

        if(modeSelected.getClass() == Letter.class)
        {
            newState = new StatePlayTile(getPlayer(), (Letter)modeSelected);
        }

        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return tileSelected;
    }
}
