package ca.qc.bdeb.p56.scrabble.model;


import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.util.LinkedList;

/**
 * Created by TheFrenchOne on 9/7/2016.
 */
public class Square implements Observable{

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
    private transient LinkedList<Observateur> observateurs;



    public Square()
    {
        this.tileOn = null;
        observateurs = new LinkedList<>();
    }




    public void setNeighbours(Square adjacentUp, Square adjacentDown, Square adjacentLeft, Square adjacentRight) {

        this.adjacentUp = adjacentUp;
        this.adjacentDown = adjacentDown;
        this.adjacentLeft = adjacentLeft;
        this.adjacentRight = adjacentRight;

    }


    public char getLetterOn()
    {
        if(tileOn != null)
        {
            return tileOn.getLetter();
        }
        else{
            return Character.MIN_VALUE;
        }
    }

    public Tile getTileOn()
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
        aviserObservateurs();
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void retirerObservateur(Observateur o) {
            observateurs.remove(o);
    }

    @Override
    public void aviserObservateurs() {
        for (Observateur ob : observateurs) {
            ob.changementEtat();
        }
    }

    @Override
    public void aviserObservateurs(Enum<?> e, Object o) {

    }
}
