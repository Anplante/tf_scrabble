package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class Letter {

    private char letter;
    private int value;

    public Letter(char letter, int value)
    {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter()
    {
        return letter;
    }

}
