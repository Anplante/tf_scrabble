package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

/**
 * Created by TheFrenchOne on 9/17/2016.
 */
public class StatePlayTile extends State {


    private Tile tileSelected;
    boolean readyToChange;
    private IDState stateSelected;

    public StatePlayTile(Player currentPlayer, Tile tileSelected)
    {
        super(currentPlayer, IDState.PLAY_TILE);
        this.tileSelected = tileSelected;
        readyToChange = false;
    }


    @Override
    protected  void selectSquare(Square squareSelected)
    {

            // TODO Louis : Vérifier que la case est valide
            squareSelected.setLetter(tileSelected); // La partie devrait le faire
            getPlayer().remove(tileSelected);  // idem
            getPlayer().aviserObservateurs();  // surement inutile
            readyToChange = true;
              this.stateSelected = IDState.SELECT_TILE;
    }


    @Override
    protected void selectNextState(IDState stateSelected) {

       this.stateSelected = stateSelected;
    }


    @Override
    protected State getNextState() {

        State newState;
        switch(stateSelected)
        {
            case SELECT_TILE:
                newState = new StateSelectTile(getPlayer());

                break;
            case EXCHANGE:
                getGame().recallTiles();
                newState = new StateExchange(getPlayer());
                break;
            default:
               newState = new StateSelectAction(getPlayer());
        }

        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return  readyToChange;
    }
}
