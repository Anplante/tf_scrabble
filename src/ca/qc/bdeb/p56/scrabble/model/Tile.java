package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/7/2016.
 */
public class Tile {

    public static final char TWO_LETTER_BONUS = 49;
    public static final char THREE_LETTER_BONUS = 50;
    public static final char TWO_WORD_BONUS = 51;
    public static final char THREE_WORD_BONUS = 52;
    public static final char CENTER_SQUARE = 53;

    private Tile adjacentUp;
    private Tile adjacentDown;
    private Tile adjacentLeft;
    private Tile adJacentRight;

    private int posRow;
    private int posColumn;
    private char content;

    public Tile(char content, int row, int column)
    {
        this.content = content;
        this.posRow = row;
        this.posColumn = column;
    }


    public char getContent()
    {
        return content;
    }
}
