package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by TheFrenchOne on 11/23/2016.
 */
public class ExchangedLog extends MoveLog {

    private static final String EXCHANGED = "Exchanged";

    private int numberOfExchangedTile;

    public ExchangedLog(Player player, int turnPlayed, int numberOfExchangedTile) {
        super(player, turnPlayed);
        this.numberOfExchangedTile = numberOfExchangedTile;
    }

    @Override
    public int getMovePoints() {
        return 0;
    }

    @Override
    public String getMove() {
        return EXCHANGED + " " + numberOfExchangedTile;
    }
}
