package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;

/**
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    private ArrayList<Tile> selectedTiles;


    public StateExchange(Player player){
        super(player,IDState.EXCHANGE);
        selectedTiles = new ArrayList<>();
    }

    @Override
    protected void selectNextState(IDState stateSelected) {

    }

    public void chooseTile(Tile aTile){
        boolean found = false;
        for (int i = 0; i < selectedTiles.size() && !found; i++) {
            if(aTile == selectedTiles.get(i)){
                selectedTiles.remove(i);
                found = true;
            }
        }
        if(!found){
            selectedTiles.add(aTile);
        }
        for (int i = 0; i < selectedTiles.size(); i++) {
            System.out.println(selectedTiles.get(i));

        }
        System.out.println("---");
    }

    public ArrayList<Tile> getSelectedTiles(){
        return selectedTiles;
    }

    @Override
    protected State getNextState() {
        return null;
    }

    @Override
    protected boolean readyForNextState() {
        return false;
    }
}
