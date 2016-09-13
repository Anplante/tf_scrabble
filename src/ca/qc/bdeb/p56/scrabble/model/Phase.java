package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.Shared.IDPhase;
import ca.qc.bdeb.p56.scrabble.model.Game;
import ca.qc.bdeb.p56.scrabble.model.Letter;
import ca.qc.bdeb.p56.scrabble.model.Player;

/**
 * Created by TheFrenchOne on 9/12/2016.
 */
public abstract class Phase {

    private final Player player;
    private final Game game;
    private final IDPhase idPhase;

    protected Phase(Player currentPlayer, IDPhase id)
    {
        this.player = currentPlayer;
        this.idPhase = id;
        game = player.getGame();
    }

    protected abstract void selectTile(Letter letter);
    protected abstract void playTile(Square square);


}
