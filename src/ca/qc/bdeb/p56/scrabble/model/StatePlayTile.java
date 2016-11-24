package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente la phase de placer des lettres sur le plateau de jeu. Le joueur peut sélectionner des lettres
 * et les placer. Il peut également choisir d'échanger ses lettres, d'annuler son jeu et reprendre ses lettres ou de
 * passer au prochain tour.
 *
 * Created by Louis Luu Lim on 9/17/2016.
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
        tilesPlaced = new ArrayList<>();
        readyToChange = false;
    }


    @Override
    protected void selectSquare(Square squareSelected) {

        this.squareSelected = squareSelected;

        if (isValidMove() && tileSelected != null) {

            if (originalTilesOder == null) {
                originalTilesOder = getPlayer().getTiles();
            }
          placeTileOnSquare();
        }
    }

    private void placeTileOnSquare()
    {
        tilesPlaced.add(squareSelected);
        squareSelected.setLetter(tileSelected);
        tileSelected.selectTile();
        getPlayer().remove(tileSelected);
        getPlayer().aviserObservateurs();
        tileSelected.selectTile();
        tileSelected = null;
    }


    @Override
    protected void selectTile(Tile tileSelected) {

        if (this.tileSelected == null) {

            tileSelected.selectTile();
            this.tileSelected = tileSelected;
        } else {

            this.tileSelected.selectTile();
            getPlayer().swapTile(tileSelected, this.tileSelected);
            this.tileSelected = null;

            if (tilesPlaced.isEmpty()) {
                selectNextState(IDState.SELECT_ACTION);
                readyToChange = true;
            }

        }
    }

    private boolean isValidMove() {

        boolean validMove = false;

        if (!squareSelected.containLetter() && letterOnSameAxe()) {
            validMove = true;
        }

        return validMove;
    }

    private boolean letterOnSameAxe() {

        boolean validMove = true;

        if (!tilesPlaced.isEmpty()) {

            for (Square tilePosition : tilesPlaced) {
                if (tilePosition.getPosRow() != squareSelected.getPosRow()) {
                    validMove = false;
                    break;
                }
            }

            if (!validMove) {
                validMove = true;
                for (Square tilePosition : tilesPlaced) {
                    if (tilePosition.getPosColumn() != squareSelected.getPosColumn()) {
                        validMove = false;
                        break;
                    }
                }
            }
        }
        return validMove;
    }


    @Override
    protected void selectNextState(IDState stateSelected) {

        if (stateSelected == IDState.PENDING)
        {
            if (!verifyValidWord()) {
                return;
            }
        }
        this.stateSelected = stateSelected;

        if (tileSelected != null)
            tileSelected.selectTile();

        readyToChange = true;
    }

    @Override
    protected State getNextState() {

        State newState = null;

        switch (stateSelected) {
            case PENDING:
                boolean isAWord = getGame().playWord(tilesPlaced);
                if (isAWord) {
                    getGame().setWaitingNextTurn(true);
                    newState = new StatePending(getPlayer());
                } else {
                    getGame().recallTiles();
                    newState = new StateSelectAction(getPlayer());
                }
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

    private boolean verifyValidWord() {

        boolean validMove = true;

        if (getGame().getLogManager().getMovesHistory().isEmpty()) {
            validMove = verifyFirstWordAtCenter();
        }

        return validMove;
    }


    private boolean verifyFirstWordAtCenter() {

        boolean validMove = false;

        for (Square square : tilesPlaced) {
            if (square.isCenter()) {
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
