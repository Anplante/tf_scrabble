package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.Shared.IDState;

/**
 * Created by TheFrenchOne on 9/14/2016.
 */
public class StatePending extends State {


    public StatePending(Player player)
    {
        super(player, IDState.WAIT);
    }
    @Override
    protected void selectTile(Letter letter) {

    }

    @Override
    protected void playTile(Square square) {

    }

    @Override
    protected State getNextState() {

       State newState = new StateSelect(getPlayer());

        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return false;
    }

    @Override
    protected void execute() {
        getPlayer().setActive(true);
    }
}
