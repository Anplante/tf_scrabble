package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Premium {

    public static enum Type
    {
        LETTER_SCORE,
        WORD_SCORE
    }


    private Type type;
    private int multiplier;


    public Premium(Type type, int multiplier)
    {
        this.type = type;
        this.multiplier = multiplier;
    }


    public Type getType()
    {
        return type;
    }

    public int getMultiplier(){
        return multiplier;
    }
}
