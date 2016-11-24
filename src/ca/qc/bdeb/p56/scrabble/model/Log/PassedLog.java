package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by TheFrenchOne on 11/23/2016.
 */
public class PassedLog extends MoveLog {

    private static final String PASSED = "Passed";

    public PassedLog(Player player, int turnPlayed){
        super(player, turnPlayed);
    }

    @Override
    public int getMovePoints() {
        return 0;
    }

    @Override
    public String getMove() {
        return PASSED;
    }
}
