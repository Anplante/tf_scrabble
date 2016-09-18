package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 9/17/2016.
 */
public class StatePlayTile extends State {


    private Letter letterSelected;
    boolean readyToChange;

    public StatePlayTile(Player currentPlayer, Letter letterSelected)
    {
        super(currentPlayer, IDState.PLAY_TILE);
        this.letterSelected = letterSelected;
        readyToChange = false;
    }


    @Override
    protected void selectMode(Object itemSelected) {

        if(itemSelected.getClass() == Square.class)
        {
            Square square = (Square) itemSelected;

            square.setLetter(letterSelected);
            getPlayer().remove(letterSelected);
            getPlayer().aviserObservateurs();
            readyToChange = true;
        }
    }


    @Override
    protected State getNextState() {

        State newState = new StateSelect(getPlayer());

        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return  readyToChange;
    }
}
