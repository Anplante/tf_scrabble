package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Classe pour conserver un coup joué par l'utilisateur.
 * <p>
 * Created by Louis Luu Lim on 10/3/2016.
 */
public abstract class MoveLog {

    private Player player;
    private int numberOfExchangedTile = 0;


    public MoveLog(Player player){
        this.player = player;
    }


    public final Player getPlayer() {
        return player;
    }

    public final int getPointsAccumulated() {
        return player.getScore();
    }

    public abstract int getMovePoints();
    public abstract String getMove();
}

