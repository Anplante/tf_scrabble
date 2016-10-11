package ca.qc.bdeb.p56.scrabble.model;


import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.util.LinkedList;

/**
 * Created by TheFrenchOne on 9/7/2016.
 */
public class Square implements Observable{

    private Square adjacentUp;
    private Square adjacentDown;
    private Square adjacentLeft;
    private Square adjacentRight;


    private int posRow;
    private int posColumn;
    private Tile tileOn;
    private Premium premium;
    private transient LinkedList<Observateur> observateurs;


    public Square(int posRow, int posColumn)
    {
        this.posRow = posRow;
        this.posColumn = posColumn;
        this.tileOn = null;
        observateurs = new LinkedList<>();
    }


    public void setNeighbours(Square adjacentUp, Square adjacentDown, Square adjacentLeft, Square adjacentRight) {

        this.adjacentUp = adjacentUp;
        this.adjacentDown = adjacentDown;
        this.adjacentLeft = adjacentLeft;
        this.adjacentRight = adjacentRight;
    }

    public String getLetterOn()
    {
        if(tileOn != null)
        {
            return tileOn.getLetter();
        }
        else{
            return "";
        }
    }


    public Tile getTileOn()
    {
      return tileOn;
    }

    public int getPosRow() {
        return posRow;
    }

    public int getPosColumn() {
        return posColumn;
    }

    public Premium getPremium() {
        return premium;
    }

    public void setPremium(Premium premium) {
        this.premium = premium;
    }


    public void setLetter(Tile tile) {

        this.tileOn = tile;

        aviserObservateurs();
    }


    public boolean isNeighbours(Square square)
    {
        return square.equals(adjacentDown) || square.equals(adjacentLeft) || square.equals(adjacentRight) || square.equals(adjacentUp);
    }

    public boolean isCenter()
    {
        return posColumn == 7 && posRow == 7;
    }

    public boolean containLetter()
    {
        return tileOn != null;
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
