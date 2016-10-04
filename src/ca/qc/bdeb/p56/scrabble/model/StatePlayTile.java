package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheFrenchOne on 9/17/2016.
 */
public class StatePlayTile extends State {


    private Tile tileSelected;
    boolean readyToChange;
    private IDState stateSelected;
    private List<Square> tilesPlaced;

    public StatePlayTile(Player currentPlayer, Tile tileSelected) {
        super(currentPlayer, IDState.PLAY_TILE);
        this.tileSelected = tileSelected;

        readyToChange = false;
    }

    @Override
    protected void selectSquare(Square squareSelected) {

        if (tilesPlaced == null) {
            tilesPlaced = new ArrayList<>();
        }

        if(getGame().getMovesHistory().isEmpty())
        {
            if(!squareSelected.isCenter() && tilesPlaced.isEmpty())
            {
                return;
            }
        }
        if(!squareSelected.containLetter())
        {
            tilesPlaced.add(squareSelected);
            // TODO Louis : Vérifier que la case est valide
            squareSelected.setLetter(tileSelected); // La partie devrait le faire??
            getPlayer().remove(tileSelected);  // idem
            getPlayer().aviserObservateurs();
        }
    }

    @Override
    protected void selectTile(Tile tileSelected) {
        this.tileSelected = tileSelected;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {

        this.stateSelected = stateSelected;
        readyToChange = true;
    }


    @Override
    protected State getNextState() {

        State newState;
        switch (stateSelected) {
            case PENDING:
                getGame().playWord(tilesPlaced);
                newState = new StatePending(getPlayer());
                break;
            case EXCHANGE:
                getGame().recallTiles(tilesPlaced);
                newState = new StateExchange(getPlayer());
                break;
            default:
                newState = new StateSelectAction(getPlayer());
        }
        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return readyToChange;
    }
}
