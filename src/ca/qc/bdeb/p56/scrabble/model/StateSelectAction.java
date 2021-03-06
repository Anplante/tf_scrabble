package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Classe qui représente la phase de sélection d'un coup à jouer par le joueur. Il peut choisir d'échanger, de mettre
 * une lettre, de passer son tour ou d'abandonner.
 *
 * Created by Louis Luu Lim on 9/12/2016.
 */
public class StateSelectAction extends State {

    private Tile tileSelected;
    protected IDState stateSelected;


    protected StateSelectAction(Player currentPlayer) {

        super(currentPlayer, IDState.SELECT_ACTION);
        stateSelected = IDState.SELECT_ACTION;
    }

    @Override
    protected  void selectTile(Tile tileSelected)
    {
        this.tileSelected = tileSelected;
        tileSelected.selectTile();
        stateSelected = IDState.PLAY_TILE;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {
       this.stateSelected = stateSelected;
    }

    @Override
    protected void execute() {
    }

    @Override
    protected State getNextState() {

        State newState = null;

        switch (stateSelected)
        {
            case PLAY_TILE:
                newState = new StatePlayTile(getPlayer(), tileSelected);
                break;
            case PENDING:
                newState = new StatePending(getPlayer());
                break;
            case EXCHANGE:
                newState = new StateExchange(getPlayer());
                break;
            case ENDING:
                newState = new StateEnding(getPlayer());
                break;
        }
        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return  true;
    }
}
