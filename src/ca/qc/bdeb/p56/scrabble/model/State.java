package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.Shared.IDState;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public abstract class State {

    private final Player player;
    private final Game game;
    private final IDState idState;

    protected State(Player currentPlayer, IDState id)
    {
        this.player = currentPlayer;
        this.idState = id;
        game = player.getGame();
    }

    protected void execute(){

    }

    protected void initialize()
    {

    }

    protected final Player getPlayer()
    {
        return player;
    }

    protected final Game getGame()
    {
        return game;
    }

    protected abstract void selectMode(Object itemSelected);
   // protected abstract void playTile(Square square);
    protected abstract State getNextState();
    protected abstract boolean readyForNextState();


}
