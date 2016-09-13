package ca.qc.bdeb.p56.scrabble.Shared;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public enum IDPhase {

    DRAW("Draw"),
    WAIT("Wait"),
    SELECT("Select"),
    EXCHANGE("Exchange");

    private String name;

    private IDPhase(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

}
