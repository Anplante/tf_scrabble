package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by TheFrenchOne on 11/23/2016.
 */
public class ForfeitedLog extends MoveLog {

    private static final String FORFEITED = "Forfeited";

    public ForfeitedLog(Player player){
        super(player);
    }

    @Override
    public int getMovePoints() {
        return 0;
    }

    @Override
    public String getMove() {
        return FORFEITED;
    }
}
