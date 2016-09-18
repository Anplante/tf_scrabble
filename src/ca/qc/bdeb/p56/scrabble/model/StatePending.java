package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class StatePending extends State {



    private Letter letter;

    public StatePending(Player player)
    {
        super(player, IDState.WAIT);
    }

    @Override
    protected void selectMode(Object modeSelected) {
    }

    /*@Override
    protected void playTile(Square square) {

    }*/

    @Override
    protected State getNextState() {

       State newState = new StateSelect(getPlayer());

        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return true;
    }

    @Override
    protected void execute() {
        getPlayer().setActive(true);
    }
}
