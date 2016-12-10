package ca.qc.bdeb.p56.scrabble.utility;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public interface Observable {

    void ajouterObservateur(Observateur o);

    void retirerObservateur(Observateur o);

    void aviserObservateurs();

    void aviserObservateurs(Enum<?> e, Object o);
}
