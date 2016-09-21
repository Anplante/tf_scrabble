package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    public StateExchange(Player player){
        super(player,IDState.EXCHANGE);
    }

    @Override
    protected void selectNextState(IDState stateSelected) {

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
