package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.model.State;
import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by Antoine on 10/26/2016.
 */
public abstract class  AiState extends State {

    protected AiState(Player currentPlayer, IDState id) {
        super(currentPlayer, id);
    }

    @Override
    protected State getNextState() {
        return new AiStatePending(getPlayer());
    }
}
