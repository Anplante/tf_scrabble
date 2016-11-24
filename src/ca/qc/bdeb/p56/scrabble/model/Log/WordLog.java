package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by Louis Luu Lim on 11/23/2016.
 */
public class WordLog extends MoveLog {

    private String wordPlayed;
    private int wordValue;

    public WordLog(Player player, int turnPlayed,  String wordPlayed, int wordValue) {
        super(player, turnPlayed);
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
