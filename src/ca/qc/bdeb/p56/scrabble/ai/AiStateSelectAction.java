package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.*;
import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.List;

/**
 * Created by 1467160 on 2016-11-03.
 */
public class AiStateSelectAction extends StateSelectAction {

    private final List<Tile> allLetters;

    protected AiStateSelectAction(Player currentPlayer) {
        super(currentPlayer);
        allLetters = currentPlayer.getTiles();
    }

    @Override
    protected void execute() {
        getPlayer().setActive(false);
    }


    @Override
    protected State getNextState() {
        return new AiStatePending(getPlayer());
    }
}
