package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.shared.IDState;

import java.util.ArrayList;

/**
 * Created by Julien Brosseau on 9/21/2016.
 */
public class StateExchange extends State {

    private ArrayList<Tile> selectedTiles;
    private IDState stateSelected;

    public StateExchange(Player player) {
        super(player, IDState.EXCHANGE);
        selectedTiles = new ArrayList<>();
        stateSelected = IDState.EXCHANGE;
    }

    @Override
    protected void selectNextState(IDState stateSelected) {
        this.stateSelected = stateSelected;
    }

    public void chooseTile(Tile aTile) {
        boolean found = false;
        for (int i = 0; i < selectedTiles.size() && !found; i++) {
            if (aTile == selectedTiles.get(i)) {
                selectedTiles.remove(i);
                found = true;
            }
        }
        if (!found) {
            selectedTiles.add(aTile);
        }
    }

    public ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    @Override
    protected State getNextState() {
        State newState;

        newState = new StateSelectAction(getPlayer());

        return newState;
    }

    @Override
    protected boolean readyForNextState() {
        return false;
    }
}
