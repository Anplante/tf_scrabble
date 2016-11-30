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

        NumberOfPlayer number = null;

        switch (x) {
            case 2:
                number = TWO_PLAYER;
                break;
            case 3:
                number = THREE_PLAYER;
                break;
            case 4:
                number = FOUR_PLAYER;
                break;
        }
        return number;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
