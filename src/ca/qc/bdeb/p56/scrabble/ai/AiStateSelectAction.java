package ca.qc.bdeb.p56.scrabble.ai;

import ca.qc.bdeb.p56.scrabble.model.Player;
import ca.qc.bdeb.p56.scrabble.model.StateSelectAction;
import ca.qc.bdeb.p56.scrabble.model.Tile;

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
}