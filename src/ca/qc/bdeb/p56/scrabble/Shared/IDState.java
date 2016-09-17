package ca.qc.bdeb.p56.scrabble.Shared;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public enum IDState {

    DRAW("Draw"),
    WAIT("Wait"),
    SELECT("Select"),
    EXCHANGE("Exchange"),
    PLAY_TILE("Play tile");

    private String name;

    private IDState(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

}
