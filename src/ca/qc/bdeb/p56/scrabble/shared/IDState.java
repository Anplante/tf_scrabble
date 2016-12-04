package ca.qc.bdeb.p56.scrabble.shared;

/**
 * Enum représant les différents états du joueur pendant la partie.
 *
 * Created by Louis Luu Lim on 9/12/2016.
 */
public enum IDState {

    PENDING("Wait"),
    SELECT_ACTION("Select action"),
    EXCHANGE("Exchange"),
    PLAY_TILE("Play tile"),
    ENDING("Ending");

    private String name;

    IDState(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

}
