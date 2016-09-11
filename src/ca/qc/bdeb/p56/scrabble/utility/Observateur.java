package ca.qc.bdeb.p56.scrabble.utility;

/**
 * Created by TheFrenchOne on 9/11/2016.
 */
public interface Observateur {

    public void changementEtat();

    public void changementEtat(Enum<?> e, Object o);
}
