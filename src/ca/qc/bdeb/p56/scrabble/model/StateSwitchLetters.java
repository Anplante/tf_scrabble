package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by Julien Brosseau on 9/18/2016.
 */
public class StateSwitchLetters extends State{
    @Override
    protected void selectMode(Object itemSelected) {

    }

    @Override
    protected State getNextState() {
        return null;
    }

    @Override
    protected boolean readyForNextState() {
        return false;
    }
}
