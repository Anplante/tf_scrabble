package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.Shared.IDPhase;
import ca.qc.bdeb.p56.scrabble.model.Letter;
import ca.qc.bdeb.p56.scrabble.model.Phase;
import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public class PhaseSelect extends Phase {

    Letter letterSelected;

    protected PhaseSelect(Player currentPlayer) {
        super(currentPlayer, IDPhase.SELECT);
    }

    @Override
    protected void selectTile(Letter letter) {
        letterSelected = letter;
    }

    @Override
    protected void playTile(Square square) {
        square.setLetter(letterSelected);
    }
}
