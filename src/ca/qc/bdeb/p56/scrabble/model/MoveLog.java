package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDMove;

/**
 * Classe pour conserver un coup joué par l'utilisateur.
 * <p>
 * Created by Louis Luu Lim on 10/3/2016.
 */
public class MoveLog {

    private Player player;
    private String wordPlayed;
    private IDMove IDMove;
    private int wordPoints;
    private int pointsAccumulated;
    private int turnPlayed;

    public MoveLog(Player player, int turnPlayed, String word, int point) {

        this.player = player;
        this.turnPlayed = turnPlayed;
        this.wordPlayed = word;
        this.wordPoints = point;
        this.pointsAccumulated = player.getScore();
        this.IDMove = IDMove.PLAYED_WORD;
    }

    public MoveLog(Player player, int turnPlayed, IDMove IDMove)
    {
        this.player = player;
        this.turnPlayed = turnPlayed;
        this.wordPlayed = "";
        this.wordPoints = 0;
        this.pointsAccumulated = player.getScore();
        this.IDMove = IDMove;
    }

    public Player getPlayer() {
        return player;
    }

    public String getWordPlayed() {
        return wordPlayed;
    }

    public IDMove getMove() {
        return IDMove;
    }

    public int getWordPoints() {
        return wordPoints;
    }

    public int getPointsAccumulated() {
        return pointsAccumulated;
    }

    public int getTurnPlayed()
    {
        return turnPlayed;
    }
}

