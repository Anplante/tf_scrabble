package ca.qc.bdeb.p56.scrabble.shared;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public enum IDState {

    DRAW("Draw"),
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
