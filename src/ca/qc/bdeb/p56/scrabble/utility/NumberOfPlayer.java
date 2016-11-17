package ca.qc.bdeb.p56.scrabble.utility;

/**
 * Created by 1467160 on 2016-11-15.
 */
public enum NumberOfPlayer {

    TWO_PLAYER(2),
    THREE_PLAYER(3),
    FOUR_PLAYER(4);

    private int numberOfPlayer;


    NumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public static NumberOfPlayer fromInteger(int x) {
        switch(x) {
            case 2:
                return TWO_PLAYER;
            case 3:
                return THREE_PLAYER;
            case 4:
                return FOUR_PLAYER;
        }
        return null;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
