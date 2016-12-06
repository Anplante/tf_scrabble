package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.Event;
import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente la phase de placer des lettres sur le plateau de jeu. Le joueur peut sélectionner des lettres
 * et les placer. Il peut également choisir d'échanger ses lettres, d'annuler son jeu et reprendre ses lettres ou de
 * passer au prochain tour.
 * <p>
 * Created by Louis Luu Lim on 9/17/2016.
 */
public class StatePlayTile extends State {

    private Tile tileSelected;
    private Square squareSelected;
    boolean readyToChange;
    private IDState stateSelected;
    private List<Square> tilesPlacedOnBoardPosition;
    private List<Tile> originalTilesOder;

    public StatePlayTile(Player currentPlayer, Tile tileSelected) {

        super(currentPlayer, IDState.PLAY_TILE);
        this.tileSelected = tileSelected;
        tilesPlacedOnBoardPosition = new ArrayList<>();
        readyToChange = false;
    }


    @Override
    protected void selectSquare(Square squareSelected) {


        // Si le joueur sélectionne une lettre sur le board pour le déplacer
        if (checkIfPlayerWantsSelectATileToMoveOnBoard(squareSelected)) {
            this.squareSelected = squareSelected;
        }
        // Si le joueur sélectionne une autre case pour déplacer la lettre sélectionné auparavant sur le board
        else if (checkIfPlayerwantsToMoveATileOnBoard(squareSelected)) {

            if (checkIfPlayerCanMoveTileOnBoard(squareSelected)) {
                swapTileOfPlace(this.squareSelected, squareSelected);
                this.squareSelected = null;
            }
        }
        // Sinon, il veut jouer une lettre sur le board
        else {
            this.squareSelected = squareSelected;
            if (isValidMove() && tileSelected != null) {

                if (originalTilesOder == null) {
                    originalTilesOder = getPlayer().getTiles();
                }

                placeTileOnSquare();
            }
            this.squareSelected = null;
        }
    }

    private void swapTileOfPlace(Square pointA, Square pointB) {

        Tile tempTile = pointA.getTileOn();
        pointA.setLetter(pointB.getTileOn());
        pointB.setLetter(tempTile);

        if (!tilesPlacedOnBoardPosition.contains(pointB)) {
            tilesPlacedOnBoardPosition.remove(pointA);
            tilesPlacedOnBoardPosition.add(pointB);
        }
    }

    private boolean checkIfPlayerWantsSelectATileToMoveOnBoard(Square squareSelected) {
        return this.tileSelected == null && this.squareSelected == null && checkIfContentOnSquaresCanBeSwap(squareSelected);
    }

    private boolean checkIfPlayerwantsToMoveATileOnBoard(Square squareSelected) {
        return tileSelected == null && this.squareSelected != null && this.squareSelected != squareSelected;
    }

    private boolean checkIfPlayerCanMoveTileOnBoard(Square squareSelected) {
        return letterOnSameAxe(squareSelected) && checkIfContentOnSquaresCanBeSwap(squareSelected);
    }

    private boolean checkIfContentOnSquaresCanBeSwap(Square squareSelected) {
        return tilesPlacedOnBoardPosition.contains(squareSelected) || !squareSelected.containLetter();
    }

    private void placeTileOnSquare() {

        tilesPlacedOnBoardPosition.add(squareSelected);

        if (tileSelected.isBlankTile()) {
            getGame().aviserObservateurs(Event.SELECT_BLANK_TILE_VALUE, tileSelected);
        }
        squareSelected.setLetter(tileSelected);
        tileSelected.selectTile();
        getGame().calculateCurrentPoints(tilesPlacedOnBoardPosition);
        getGame().aviserObservateurs();
        getPlayer().remove(tileSelected);
        getPlayer().aviserObservateurs();
        getGame().aviserObservateurs();
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

            if (tilesPlacedOnBoardPosition.isEmpty()) {
                selectNextState(IDState.SELECT_ACTION);
                readyToChange = true;
            }
        }
    }

    private boolean isValidMove() {

        boolean validMove = false;

        if (!squareSelected.containLetter() && letterOnSameAxe(squareSelected)) {
            validMove = true;
        }

        return validMove;
    }

    private boolean letterOnSameAxe(Square squareSelected) {

        boolean validMove = true;

        if (!tilesPlacedOnBoardPosition.isEmpty()) {

            for (Square tilePosition : tilesPlacedOnBoardPosition) {
                if (tilePosition.getPosRow() != squareSelected.getPosRow()) {
                    validMove = false;
                    break;
                }
            }
            if (!validMove) {
                validMove = true;
                for (Square tilePosition : tilesPlacedOnBoardPosition) {
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

        if (stateSelected == IDState.PENDING) {
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
                boolean isAWord = getGame().playWord(tilesPlacedOnBoardPosition);
                if (isAWord) {
                    newState = new StatePending(getPlayer());
                } else {
                    getGame().recallTiles();
                    newState = new StateSelectAction(getPlayer());
                }
                break;
            case EXCHANGE:
                getGame().recallTiles(tilesPlacedOnBoardPosition);
                getGame().replacePlayerTilesInOrder(originalTilesOder);
                newState = new StateExchange(getPlayer());
                break;
            case SELECT_ACTION:
                getGame().recallTiles(tilesPlacedOnBoardPosition);
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

        for (Square square : tilesPlacedOnBoardPosition) {
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
