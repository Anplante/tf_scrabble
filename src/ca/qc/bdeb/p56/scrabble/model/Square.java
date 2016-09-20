package ca.qc.bdeb.p56.scrabble.model;



/**
 * Created by TheFrenchOne on 9/7/2016.
 */
public class Square {

    public static final char TWO_LETTER_BONUS = 49;
    public static final char THREE_LETTER_BONUS = 50;
    public static final char TWO_WORD_BONUS = 51;
    public static final char THREE_WORD_BONUS = 52;
    public static final char CENTER_SQUARE = 53;

    private Square adjacentUp;
    private Square adjacentDown;
    private Square adjacentLeft;
    private Square adjacentRight;

    private int posRow;
    private int posColumn;
    private Tile tileOn;
    private Premium premium;



    public Square()
    {
        this.tileOn = null;
    }




    public void setNeighbours(Square adjacentUp, Square adjacentDown, Square adjacentLeft, Square adjacentRight) {

        this.adjacentUp = adjacentUp;
        this.adjacentDown = adjacentDown;
        this.adjacentLeft = adjacentLeft;
        this.adjacentRight = adjacentRight;

    }


    public char getTileOn()
    {
        if(tileOn != null)
        {
            return tileOn.getLetter();
        }
        else{
            return Character.MIN_VALUE;
        }
    }

    public Tile getLetter()
    {
      return tileOn;
    }


    public void setPremium(Premium premium) {
        this.premium = premium;
    }

    public Premium getPremium() {
        return premium;
    }

    public void setLetter(Tile tile) {
        this.tileOn = tile;
    }
}
