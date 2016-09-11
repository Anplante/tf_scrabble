package ca.qc.bdeb.p56.scrabble.utility;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public interface Observable {

    public void ajouterObservateur(Observateur o);

    public void retirerObservateur(Observateur o);

    public void aviserObservateurs();

    public void aviserObservateurs(Enum<?> e, Object o);
}
