package ca.qc.bdeb.p56.scrabble.model.Log;

import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Classe pour conserver un coup joué par l'utilisateur.
 * <p>
 * Created by Louis Luu Lim on 10/3/2016.
 */
public abstract class MoveLog {

    private Player player;
    private int turnPlayed;


    public MoveLog(Player player, int turnPlayed){
        this.player = player;
        this.turnPlayed = turnPlayed;
    }


    public final Player getPlayer() {
        return player;
    }

    public final int getPointsAccumulated() {
        return player.getScore();
    }

    public final int getTurnPlayed(){ return turnPlayed; }

    public abstract int getMovePoints();
    public abstract String getMove();
}

