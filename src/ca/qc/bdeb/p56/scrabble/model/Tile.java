package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.util.LinkedList;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public class Tile implements Observable {

    private String letter;
    private int value;
    private boolean isSelected;
    private transient LinkedList<Observateur> observateurs;

    public Tile(String letter, int value)
    {
        this.letter = letter;
        this.value = value;
        isSelected = false;
        observateurs = new LinkedList<>();
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getLetter()
    {
        return letter;
    }

    public int getValue()
    {
        return value;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void selectTile()
    {
        isSelected = !isSelected;
        aviserObservateurs();
    }

    public boolean isBlankTile()
    {
        return letter.isEmpty() || value == 0;
    }

    @Override
    public void ajouterObservateur(Observateur o) {
            observateurs.clear();
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
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
