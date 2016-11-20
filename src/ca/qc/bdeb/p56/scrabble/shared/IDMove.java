package ca.qc.bdeb.p56.scrabble.shared;

/**
 * Created by TheFrenchOne on 10/26/2016.
 */
public enum IDMove {

    PLAYED_WORD("Played"),
    EXCHANGED("Exchanged"),
    PASSED("Passed"),
    FORFEITED("Forfeited");


    private String name;

    IDMove(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
