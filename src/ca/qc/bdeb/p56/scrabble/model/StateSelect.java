package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.Shared.IDState;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class StateSelect extends State {

    private Letter letterSelected;
    private boolean tileSelected;

    protected StateSelect(Player currentPlayer) {
        super(currentPlayer, IDState.SELECT);
        tileSelected = false;
    }

    @Override
    protected void selectTile(Letter letter) {
        letterSelected = letter;
    }

    @Override
    protected void playTile(Square square) {

        if(letterSelected != null)
        {
            square.setLetter(letterSelected);
        }

    }

    @Override
    protected State getNextState() {
        return null;
    }

    @Override
    protected boolean readyForNextState() {
        return tileSelected;
    }
}
