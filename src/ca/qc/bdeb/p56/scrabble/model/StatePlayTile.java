package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheFrenchOne on 9/17/2016.
 */
public class StatePlayTile extends State {


    private Tile tileSelected;
    private Square squareSelected;
    boolean readyToChange;
    private IDState stateSelected;
    private List<Square> tilesPlaced;
    private List<Tile> originalTilesOder;

    public StatePlayTile(Player currentPlayer, Tile tileSelected) {
        super(currentPlayer, IDState.PLAY_TILE);
        this.tileSelected = tileSelected;

        readyToChange = false;
    }

    @Override
    protected void selectSquare(Square squareSelected) {

        this.squareSelected = squareSelected;
        if (tilesPlaced == null) {
            tilesPlaced = new ArrayList<>();
        }

        if(isValidMove())
        {
            if(originalTilesOder == null)
            {
                originalTilesOder = getPlayer().getTiles();
            }
            tilesPlaced.add(squareSelected);
            squareSelected.setLetter(tileSelected); // La partie devrait le faire??
            getPlayer().remove(tileSelected);  // idem
            getPlayer().aviserObservateurs();
        }
    }

    private boolean isValidMove()
    {
        boolean validMove = false;

        if(!squareSelected.containLetter() && letterOnSameAxe())
        {
                validMove = true;
        }

        // autres vérifications à venir

        return validMove;
    }

    private boolean letterOnSameAxe()
    {
        boolean validMove = true;

        if(!tilesPlaced.isEmpty()){

            for(Square tilePosition : tilesPlaced)
            {
                if(tilePosition.getPosRow() != squareSelected.getPosRow() ){
                    validMove = false;
                    break;
                }
            }

            if(!validMove)
            {
                validMove = true;
                for(Square tilePosition : tilesPlaced)
                {
                    if(tilePosition.getPosColumn() != squareSelected.getPosColumn() ){
                        validMove = false;
                        break;
                    }
                }
            }
        }
        return validMove;
    }

    @Override
    protected void selectTile(Tile tileSelected) {
        this.tileSelected = tileSelected;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {

        if(stateSelected == IDState.PENDING) // il y a un probleme lorsque le joueur decide de passer un tour alors qu'il est en train de placer des lettres, a voir la solution a utiliser
        {
            if(!verifyValidWord()){
               return;
            }
        }
        this.stateSelected = stateSelected;
        readyToChange = true;
    }


    @Override
    protected State getNextState() {

        State newState = null;
        switch (stateSelected) {
            case PENDING:
                getGame().playWord(tilesPlaced);
                newState = new StatePending(getPlayer());

                break;
            case EXCHANGE:
                getGame().recallTiles(tilesPlaced);
                getGame().replacePlayerTilesInOrder(originalTilesOder);
                newState = new StateExchange(getPlayer());
                break;
            case SELECT_ACTION:
                getGame().recallTiles(tilesPlaced);
                getGame().replacePlayerTilesInOrder(originalTilesOder);
                newState = new StateSelectAction(getPlayer());
                break;
        }
        return newState;
    }

    private boolean verifyValidWord()
    {
        boolean validMove = true;

        if(getGame().getMovesHistory().isEmpty())
        {
            validMove = verifyFirstWordAtCenter();
        }


        // TODO Louis: ajouter les autres vérifications

        return validMove;
    }


    private boolean verifyFirstWordAtCenter()
    {
        boolean validMove = false;

        for(Square square: tilesPlaced)
        {
            if(square.isCenter())
            {
                validMove = true;
                break;
            }
        }
       return validMove;
    }

    @Override
    protected boolean readyForNextState() {
        return readyToChange;
    }
}
