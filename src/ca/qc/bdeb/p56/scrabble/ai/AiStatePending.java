package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.model.State;
import ca.qc.bdeb.p56.scrabble.model.StatePending;

/**
 * Created by Antoine on 11/8/2016.
 */
public class AiStatePending extends StatePending {

    public AiStatePending(Player player) {
        super(player);
    }


    @Override
    protected State getNextState() {
        return new AiStateSelectAction(getPlayer());
    }
}
