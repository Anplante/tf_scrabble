package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class Tile {

    private String letter;
    private int value;
    private boolean selected;

    public Tile(String letter, int value, Boolean isSelect)
    {
        this.letter = letter;
        this.value = value;
        selected = isSelect;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected(){
        return selected;
    }

    public String getLetter()
    {
        return letter;
    }

    public int getValue()
    {
        return value;
    }
}
