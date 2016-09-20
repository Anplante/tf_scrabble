package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

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

    protected void selectTile(Tile tile){

    }

    protected void selectSquare(Square square)
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

    protected abstract void selectNextState(IDState stateSelected);

    protected abstract State getNextState();
    protected abstract boolean readyForNextState();

    public final String getName()
    {
        return idState.getName();
    }


}
