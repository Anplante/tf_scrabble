package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by 1467160 on 2016-11-03.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
        setState(new StatePending(this));
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }
}
