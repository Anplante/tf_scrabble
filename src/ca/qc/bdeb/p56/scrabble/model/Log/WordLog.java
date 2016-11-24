package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by TheFrenchOne on 11/23/2016.
 */
public class WordLog extends MoveLog {

    private String wordPlayed;
    private int wordValue;

    public WordLog(Player player, String wordPlayed, int wordValue) {
        super(player);
        this.wordPlayed = wordPlayed;
        this.wordValue = wordValue;
    }

    @Override
    public int getMovePoints() {
        return wordValue;
    }

    @Override
    public String getMove() {
        return wordPlayed;
    }
}
