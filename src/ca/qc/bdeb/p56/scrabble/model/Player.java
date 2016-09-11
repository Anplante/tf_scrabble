package ca.qc.bdeb.p56.scrabble.model;

import ca.qc.bdeb.p56.scrabble.utility.Observable;
import ca.qc.bdeb.p56.scrabble.utility.Observateur;

import java.util.List;


/**
 * Created by TheFrenchOne on 9/10/2016.
 */
public class Player implements Observable {


    private Game game;
   // private final List<Tile> lettersOnHand;

    public Player(Game game)
    {
        this.game = game;
    }



    @Override
    public void ajouterObservateur(Observateur o) {

    }

    @Override
    public void retirerObservateur(Observateur o) {

    }

    @Override
    public void aviserObservateurs() {

    }

    @Override
    public void aviserObservateurs(Enum<?> e, Object o) {

    }
}
