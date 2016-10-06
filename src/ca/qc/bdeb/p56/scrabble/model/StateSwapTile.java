package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;
import ca.qc.bdeb.p56.scrabble.view.BtnTile;

import java.util.ArrayList;

/**
 * Created by Julien Brosseau on 10/5/2016.
 */
public class StateSwapTile extends State {

    private Tile tileSelected;
    private Tile swapTile;
    boolean readyToChange;
    private IDState stateSelected;
    private BtnTile first;
    private BtnTile second;
    private Tile inter;

    public StateSwapTile(Player currentPlayer, Tile tileSelected,Tile swapTile) {
        super(currentPlayer, IDState.SWAP_TILE);
        this.tileSelected = tileSelected;
        this.swapTile = swapTile;
    }

    public Tile getTileSelected(){
        return  tileSelected;
    }

    public Tile getSwapTile(){
        return  swapTile;
    }

    public void setFirst(BtnTile firstBtn){
        first = firstBtn;
    }

    public void setSecond(BtnTile secondBtn){
        second = secondBtn;
    }

    public void swap(){
        inter = second.getTile();
        second.setTile(first.getTile());
        first.setTile(inter);
    }

    @Override
    protected void selectNextState(IDState stateSelected) {
        this.stateSelected = stateSelected;
        readyToChange = true;
    }

    @Override
    protected State getNextState() {

        State newState = new StateSelectAction(getPlayer());
        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return readyToChange;
    }
}
