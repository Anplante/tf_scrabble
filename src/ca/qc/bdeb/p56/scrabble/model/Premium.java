package ca.qc.bdeb.p56.scrabble.model;

/**
 * Classe qui permet d'indiquer si une tuile sur le plateau de jeu procure un bonus de points
 *
 * Created by Louis Luu Lim on 9/10/2016.
 */
public class Premium {

    public enum Type
    {
        LETTER_SCORE,
        WORD_SCORE
    }

    private Type type;
    private int multiplier;
    private String name;

    public Premium(String name, Type type, int multiplier)
    {
        this.name = name;
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

    public String getName()
    {
        return name;
    }
}
