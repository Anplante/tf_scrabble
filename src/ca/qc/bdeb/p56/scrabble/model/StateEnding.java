package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 11/20/2016.
 */
public class StateEnding extends State {


    public StateEnding(Player player){
        super(player, IDState.ENDING);
    }

    @Override
    protected void selectNextState(IDState stateSelected) {

    }

    @Override
    protected State getNextState() {
        return this;
    }

    @Override
    protected boolean readyForNextState() {
        return false;
    }

    protected void initialize() {
        getPlayer().setActive(false);
    }
}
